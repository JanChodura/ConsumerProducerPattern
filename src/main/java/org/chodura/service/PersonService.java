package org.chodura.service;

import org.chodura.model.Person;

import java.util.List;

public interface PersonService {

    void addPerson(Person person);

    List<Person> printAll();

    int deleteAll();
}
