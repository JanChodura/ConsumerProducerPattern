package org.chodura.core;

import org.chodura.model.Person;
import org.chodura.service.PersonService;
import org.chodura.service.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SimpleProducerConsumer implements ProducerConsumer{

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleProducerConsumer.class);
    public static final int CHECK_INTERVAL_MS = 1000;
    private final long inactivityTimeoutMax;
    private final int queueMaxSize;

    private final int personsCount;
    private Producer producer;
    private Consumer consumer;
    private final PersonService personService;

    public SimpleProducerConsumer(int personsCount, int queueMaxSize, long inactivityTimeout){
        this.personsCount = personsCount;
        this.queueMaxSize = queueMaxSize;
        this.inactivityTimeoutMax = inactivityTimeout;
        personService = new PersonServiceImpl();
    }

    public void run() {

        BlockingQueue<Person> personQueue = new ArrayBlockingQueue<>(queueMaxSize);

        producer = new Producer(personQueue, personsCount);
        consumer = new Consumer(personQueue, personService);

        Thread producerThread = new Thread(producer, "Producer Thread");
        Thread consumerThread = new Thread(consumer, "Consumer Thread");

        producerThread.start();
        consumerThread.start();

        checkActivity();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<Person> getConsumed() {
        return this.personService.printAll();
    }

    public int clean() {
        return this.personService.deleteAll();
    }

    private void checkActivity() {
        boolean shouldStop = false;
        long lastActivityTime = producer.getLastActivityTime() > producer.getLastActivityTime() ? producer.getLastActivityTime() : consumer.getLastActivityTime();

        while (!shouldStop) {

            if (isAbleToStop(lastActivityTime)) {
                shouldStop = true;
                producer.requestStop();
                consumer.requestStop();
            } else {
                try {
                    Thread.sleep(CHECK_INTERVAL_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        LOGGER.info("Finished of check activity");
    }

    private boolean isAbleToStop(long lastActivityTime) {
        return System.currentTimeMillis() - lastActivityTime > this.inactivityTimeoutMax;
    }
}
