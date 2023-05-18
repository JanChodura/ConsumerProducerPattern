package org.chodura.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfiguration {

    public static Properties read(String file) {
        Properties properties = new Properties();

        try (InputStream inputStream = AppConfiguration.class.getClassLoader().getResourceAsStream(file)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failure in reading configuration from file:" + file, e);
        }
        return properties;
    }
}
