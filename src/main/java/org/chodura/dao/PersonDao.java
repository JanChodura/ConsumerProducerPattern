package org.chodura.dao;

import org.chodura.model.Person;

import java.util.List;

public interface PersonDao {
    void add(Person person);

    List<Person> getAll();

    int deleteAll();
}
