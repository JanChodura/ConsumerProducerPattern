package org.chodura.service;

import org.chodura.dao.PersonDao;
import org.chodura.dao.PersonH2Dao;
import org.chodura.model.Person;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao = new PersonH2Dao();

    @Override
    public void addPerson(Person person) {
        personDao.add(person);
    }

    @Override
    public List<Person> printAll() {
        List<Person> allPersons = personDao.getAll();
        allPersons.forEach(System.out::println);
        return allPersons;
    }

    @Override
    public int deleteAll() {
        return personDao.deleteAll();
    }
}
