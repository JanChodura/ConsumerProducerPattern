package org.chodura.dao;

import org.chodura.model.Person;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class PersonH2Dao extends AbstractDao implements PersonDao {

    private static final String SQL_TEMPLATE_FILE = "sqlQueries.template";

    private final PersonMapper mapper;

    public PersonH2Dao() {
        super(ModelClassEnum.PERSON, SQL_TEMPLATE_FILE);
        mapper = new PersonMapper();
    }

    @Override
    public void add(Person person) {

        String queryTemplate = this.sqlQueryTemplates.get(this.modelClassPrefix + "add");
        String query = queryTemplate.replace(this.modelClassPrefix + "name", toStringForDb(person.getName()));
        query = query.replace(this.modelClassPrefix + "age", Integer.toString(person.getAge()));
        query = query.replace(this.modelClassPrefix + "guid", toStringForDb(UUID.randomUUID().toString()));

        this.connector.executeUpdate(query);
    }

    @Override
    public List<Person> getAll() {
        String query = this.sqlQueryTemplates.get(this.modelClassPrefix + "printAll");
        ResultSet resultSet = this.connector.executeQuery(query);
        return mapper.mapList(resultSet);
    }

    @Override
    public int deleteAll() {
        String deleteQuery = this.sqlQueryTemplates.get(this.modelClassPrefix + "deleteAll");
        return this.connector.executeUpdate(deleteQuery);
    }

    private String toStringForDb(String stringValue) {
        return "'" + stringValue + "'";
    }
}
