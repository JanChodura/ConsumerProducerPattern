package org.chodura;

import org.chodura.core.ProducerConsumer;
import org.chodura.core.SimpleProducerConsumer;
import org.chodura.util.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

public class ProducerConsumerApp {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProducerConsumerApp.class);
    private final static int DEFAULT_NUMBER_OF_PERSONS = 100;
    private final static String CONFIG_FILE = "config.properties";
    private final static String QUEUE_MAX_SIZE_KEY = "queue.maxSize";
    private final static String MAX_INACTIVITY_TIMEOUT_KEY = "inactivity.maxTime";
    private final static String SIMPLE_PRODUCER_CONSUMER = "Simple";
    private final static String THREAD_POOL_PRODUCER_CONSUMER = "ThreadPool";


    public static void main(String[] args) {
        LOGGER.info("Hello and welcome!");

        Properties appConfiguration = AppConfiguration.read(CONFIG_FILE);
        long maxInactivityTimeout = Long.parseLong(appConfiguration.getProperty(MAX_INACTIVITY_TIMEOUT_KEY)) * 1000;
        int maxQueueItems = Integer.parseInt(appConfiguration.getProperty(QUEUE_MAX_SIZE_KEY));
        ProducerConsumer producerConsumer;
        if (getProducerConsumerImplementation(args).equals(THREAD_POOL_PRODUCER_CONSUMER)) {
            throw new RuntimeException("not yet implemented");
        } else {
            producerConsumer = new SimpleProducerConsumer(getPersonsCount(args), maxQueueItems, maxInactivityTimeout);
            producerConsumer.run();
        }
        LOGGER.info("Farewell!");
    }

    private static int getPersonsCount(String[] args) {
        return Arrays.stream(args).filter(arg -> arg.startsWith("count")).findFirst().map(Integer::parseInt).orElse(DEFAULT_NUMBER_OF_PERSONS);
    }

    private static String getProducerConsumerImplementation(String[] args) {
        return Arrays.stream(args).filter(arg -> arg.startsWith("i")).findFirst().orElse(SIMPLE_PRODUCER_CONSUMER);
    }
}
