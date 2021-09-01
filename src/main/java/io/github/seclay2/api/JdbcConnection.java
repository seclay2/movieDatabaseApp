package io.github.seclay2.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnection {

    private static final Logger LOGGER =
            Logger.getLogger(JdbcConnection.class.getName());

    private static Optional<Connection> connection = Optional.empty();

    public static Optional<Connection> getConnection() {
        if (!connection.isPresent()) {
            String url = "jdbc:postgresql://172.28.85.52:5432/moviedb";
            String user = "remote";
            String pwd = "tigers07";

            try {
                connection = Optional.ofNullable(
                        DriverManager.getConnection(url, user, pwd));
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        }

        return connection;
    }

}
