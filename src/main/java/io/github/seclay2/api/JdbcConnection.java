package io.github.seclay2.api;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnection {

    private static final Logger LOGGER =
            Logger.getLogger(JdbcConnection.class.getName());

    private static Optional<Connection> connection = Optional.empty();

    public static Optional<Connection> getConnection() {
        if (!connection.isPresent()) {
            Properties appProps = loadProperties();

            String url = appProps.getProperty("jdbcUrl");
            String user = appProps.getProperty("jdbcUser");
            String pass = appProps.getProperty("jdbcPass");

            try {
                connection = Optional.ofNullable(
                        DriverManager.getConnection(url, user, pass));
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        }

        return connection;
    }

    private static Properties loadProperties() {
        Properties appProps = new Properties();
        try {
            Path propertyFile = Paths.get("src/main/resources/application2.properties");
            Reader propsReader = Files.newBufferedReader(propertyFile);
            appProps.load(propsReader);

            LOGGER.log(Level.INFO, "Properties file loaded: " + propertyFile);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return appProps;
    }

}
