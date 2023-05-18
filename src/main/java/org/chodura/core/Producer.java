package org.chodura.core;

import org.chodura.model.Person;
import org.chodura.util.PersonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer extends Stoppable implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private final BlockingQueue<Person> queue;
    private final List<Person> persons;

    private int producedPersonsCount;

    public Producer(BlockingQueue<Person> queue, int personsCount) {
        this.persons = PersonGenerator.generatePersonList(personsCount);
        this.queue = queue;
        this.producedPersonsCount = 0;
        this.setLastActivityTime(System.currentTimeMillis());
    }

    @Override
    public void run() {
        LOGGER.info("start of thread: {}", Thread.currentThread().getName());

        while (!this.stopRequested) {
            if (this.persons.size() > 0) {
                persons.forEach(p -> {
                    try {
                        queue.put(p);
                        producedPersonsCount++;
                        this.setLastActivityTime(System.currentTimeMillis());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                persons.clear();
            }
        }

        LOGGER.info("finish of thread: {} with produced persons: {}", Thread.currentThread().getName(), producedPersonsCount);
    }
}