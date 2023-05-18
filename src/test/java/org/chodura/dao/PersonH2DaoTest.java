package org.chodura.dao;

import org.chodura.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonH2DaoTest {

    PersonH2Dao dao;

    @BeforeEach
    void setUp() {
        dao = new PersonH2Dao();
    }

    @AfterEach
    void tearDown() {
        dao.deleteAll();
        dao.connector.closeConnection();
    }

    @Test
    void add() {
        Person newPerson = Person.create("John Doe", 56);
        assertEquals(0, dao.getAll().size());

        dao.add(newPerson);

        List<Person> allPersons = dao.getAll();
        assertEquals(1, allPersons.size());
        assertEquals("John Doe", allPersons.get(0).getName());
        assertEquals(56, allPersons.get(0).getAge());
    }

}