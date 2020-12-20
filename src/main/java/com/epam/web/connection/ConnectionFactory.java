package com.epam.web.connection;

import com.epam.web.exceptions.ConnectionPoolException;
import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String DB_PROPERTIES_LOCATION = "database.properties";
    private static final String URL_PROPERTY_KEY = "url";

    public Connection create() {
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES_LOCATION)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String url = properties.getProperty(URL_PROPERTY_KEY);
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(url, properties);
        } catch (SQLException | IOException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }
}
