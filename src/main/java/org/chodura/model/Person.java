package org.chodura.model;

import java.util.Objects;
import java.util.UUID;

public class Person extends AbstractBaseEntity {

    private final String name;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", guid=" + guid +
                '}';
    }

    private final int age;

    public Person(int id, UUID guid, String name, int age) {
        super(id, guid);
        this.name = name;
        this.age = age;
    }

    private Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public static Person create(String name, int age) {
        return new Person(name, age);
    }

    public static Person empty() {
        return new Person(0, UUID.randomUUID(), "emptyName", 0);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}