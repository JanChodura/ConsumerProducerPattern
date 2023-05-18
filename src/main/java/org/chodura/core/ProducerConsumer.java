package org.chodura.core;

import org.chodura.model.Person;

import java.util.List;

public interface ProducerConsumer {
    void run();
    List<Person> getConsumed();
    int clean();
}
