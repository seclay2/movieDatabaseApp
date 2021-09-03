package io.github.seclay2.api.dao;

import io.github.seclay2.api.JdbcConnection;
import io.github.seclay2.api.NonExistentMovieException;
import io.github.seclay2.api.NonExistentPlatformException;
import io.github.seclay2.api.entity.Movie;
import io.github.seclay2.api.entity.Platform;
import io.github.seclay2.api.entity.Watch;
import io.github.seclay2.api.service.MovieServiceImpl;
import io.github.seclay2.api.service.PlatformService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlatformDao implements Dao<Platform, Integer> {

    private final Logger LOGGER =
            Logger.getLogger(PlatformDao.class.getName());

    private final Optional<Connection> connection;

    public PlatformDao() {
        this.connection = JdbcConnection.getConnection();
    }


    @Override
    public Optional<Platform> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Platform> platform = Optional.empty();
            String query = "SELECT * FROM movie_platforms WHERE id = " + id;

            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    String name = resultSet.getString("platform_name");
                    float monthlyCost = resultSet.getFloat("monthly_cost");

                    platform = Optional.of(new Platform(id, name, monthlyCost));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return platform;

        });
    }

    @Override
    public Collection<Platform> getAll() {
        Collection<Platform> platforms = new ArrayList<>();
        String query = "SELECT * FROM movie_platforms";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("platform_name");
                    float monthlyCost = resultSet.getFloat("monthly_cost");

                    platforms.add(new Platform(id, name, monthlyCost));
                }

                LOGGER.log(Level.INFO, "{0} platforms found in database", platforms.size());

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });

        return platforms;
    }

    @Override
    public Optional<Integer> save(Platform platform) {
        String message = "Platform cannot be null";
        Platform nonNullPlatform = Objects.requireNonNull(platform, message);
        String query = "INSERT INTO "
                + "movie_platforms(platform_name, monthly_cost) "
                + "VALUES(?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullPlatform.getName());
                statement.setFloat(2, nonNullPlatform.getMonthlyCost());

                int numberOfInsertedRows = statement.executeUpdate();

                // Retrieve auro-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullPlatform, (numberOfInsertedRows > 0)});
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Platform platform) {
        String message = "The platform to be update should not be null";
        Platform nonNullPlatform = Objects.requireNonNull(platform, message);
        String query = "UPDATE movie_platforms SET "
                + "platform_name = ?, "
                + "monthly_cost = ? "
                + "WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, nonNullPlatform.getName());
                statement.setFloat(2, nonNullPlatform.getMonthlyCost());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the platform updated successfully? ",
                        numberOfUpdatedRows > 0);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });
    }

    @Override
    public void delete(Platform platform) {
        String message = "The platform to be deleted should not be null";
        Platform nonNullPlatform = Objects.requireNonNull(platform, message);
        String query = "DELETE FROM movie_platforms WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, nonNullPlatform.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was platform deleted successfully? ",
                        numberOfDeletedRows > 0);

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });
    }
}
