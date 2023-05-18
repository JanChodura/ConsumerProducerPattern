package org.chodura.core;

import org.chodura.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleProducerConsumerTest {

    private ProducerConsumer simpleProducerConsumer;

    @BeforeEach
    void setUp() {
        simpleProducerConsumer = new SimpleProducerConsumer(1, 4, 2);
        simpleProducerConsumer.clean();
    }

    @AfterEach
    void tearDown() {
        simpleProducerConsumer.clean();
    }

    @Test
    void run20UsersQueue4() {
        simpleProducerConsumer = new SimpleProducerConsumer(20, 4, 3);
        simpleProducerConsumer.run();
        List<Person> addPersons = simpleProducerConsumer.getConsumed();

        assertEquals(20, addPersons.size());
        assertTrue(addPersons.get(10).getName().length()>0);
        assertTrue(addPersons.get(10).getAge()>0);
    }

    @Test
    void run200UsersQueue4() {
        simpleProducerConsumer = new SimpleProducerConsumer(200, 4, 3);
        simpleProducerConsumer.run();
        List<Person> addPersons = simpleProducerConsumer.getConsumed();

        assertEquals(200, addPersons.size());
    }

    @Test
    void run200UsersQueue50() {
        simpleProducerConsumer = new SimpleProducerConsumer(200, 50, 3);
        simpleProducerConsumer.run();
        List<Person> addPersons = simpleProducerConsumer.getConsumed();

        assertEquals(200, addPersons.size());
    }

    @Test
    void clean() {
        simpleProducerConsumer = new SimpleProducerConsumer(5, 4, 3);
        simpleProducerConsumer.run();

        int deletedPersons = simpleProducerConsumer.clean();

        assertTrue(simpleProducerConsumer.getConsumed().isEmpty());
        assertEquals(5, deletedPersons);
    }

}