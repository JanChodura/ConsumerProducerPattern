package org.chodura.core;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private final Connection connection;

    private DatabaseConnector() {
        Properties properties = loadProperties();
        String dbUrl = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failure to database: " + e.getMessage(), e);
        }
    }

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Closing of connection failed", e);
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failure in executing query: " + e.getMessage(), e);
        }
        return resultSet;

    }

    public int executeUpdate(String sqlQuery) {
        int rowCount = 0;
        try {
            Statement statement = connection.createStatement();
            rowCount = statement.executeUpdate(sqlQuery);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }


    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failure in loading configure properties: " + e.getMessage(), e);
        }
        return properties;
    }
}