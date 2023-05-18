package org.chodura.dao;

import org.chodura.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonMapper {
    private final static Logger LOGGER = LoggerFactory.getLogger(PersonMapper.class);

    public Person mapObject(ResultSet resultSet) {

        Person person = Person.empty();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String guid = resultSet.getString("guid");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                person = new Person(id, UUID.fromString(guid), name, age);

                LOGGER.debug("Mapped Person: {}", person);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failure in executing query: " + e.getMessage(), e);
        }


        return person;
    }

    public List<Person> mapList(ResultSet resultSet) {

        List<Person> persons = new ArrayList<>();
        if (resultSet == null) {
            return persons;
        }

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String guid = resultSet.getString("guid");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                Person person = new Person(id, UUID.fromString(guid), name, age);
                persons.add(person);
                LOGGER.debug("Mapped Person: {} to persons list with size: {}", person, persons.size());
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failure in executing query: " + e.getMessage(), e);
        }
        return persons;
    }
}
