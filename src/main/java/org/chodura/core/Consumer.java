package org.chodura.core;

import org.chodura.model.Person;
import org.chodura.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Stoppable implements Runnable {
    private final static Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private final BlockingQueue<Person> queue;
    private final PersonService personService;

    public Consumer(BlockingQueue<Person> queue, PersonService personService) {
        this.queue = queue;
        this.personService = personService;
        this.setLastActivityTime(System.currentTimeMillis());
    }

    @Override
    public void run() {

        int consumedPersonsCount = 0;
        LOGGER.info("start of thread: {}", Thread.currentThread().getName());
        while (!this.stopRequested) {
            try {
                if (queue.size() > 0) {
                    this.personService.addPerson(queue.take());
                    this.setLastActivityTime(System.currentTimeMillis());
                    consumedPersonsCount++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("finish of thread: {}, consumed persons count: {}", Thread.currentThread().getName(), consumedPersonsCount);
    }

}