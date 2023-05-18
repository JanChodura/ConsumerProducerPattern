package org.chodura.util;

import org.chodura.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonGenerator {
    private static final String[] FIRST_NAMES = {"Alice", "Bob", "Charlie", "David", "Emily", "Frank", "Grace", "Henry", "Isabella", "Jack", "Kate", "Liam", "Mia", "Noah", "Olivia", "Penelope", "Quinn", "Ryan", "Sophia", "Thomas", "Ursula", "Victor", "Willow", "Xander", "Yara", "Zoe"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson"};

    public static List<Person> generatePersonList(int count) {
        List<Person> personList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String firstName = getRandomFirstName(random);
            String lastName = getRandomLastName(random);
            String fullName = firstName + " " + lastName;
            int age = random.nextInt(90) + 1;
            personList.add(Person.create(fullName, age));
        }

        return personList;
    }

    private static String getRandomFirstName(Random random) {
        int index = random.nextInt(FIRST_NAMES.length);
        return FIRST_NAMES[index];
    }

    private static String getRandomLastName(Random random) {
        int index = random.nextInt(LAST_NAMES.length);
        return LAST_NAMES[index];
    }
}