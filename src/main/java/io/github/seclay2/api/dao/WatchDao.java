package io.github.seclay2.api.dao;

import io.github.seclay2.api.JdbcConnection;
import io.github.seclay2.api.NonExistentMovieException;
import io.github.seclay2.api.NonExistentPlatformException;
import io.github.seclay2.api.entity.Movie;
import io.github.seclay2.api.entity.Platform;
import io.github.seclay2.api.entity.Watch;
import io.github.seclay2.api.service.MovieService;
import io.github.seclay2.api.service.MovieServiceImpl;
import io.github.seclay2.api.service.PlatformService;
import io.github.seclay2.api.service.PlatformServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WatchDao implements Dao<Watch, Integer> {

    private final Logger LOGGER =
            Logger.getLogger(WatchDao.class.getName());

    private final Optional<Connection> connection;

    private final MovieService movieService =
            new MovieServiceImpl();

    private final PlatformService platformService =
            new PlatformServiceImpl();

    public WatchDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Watch> get(int id) {
        return connection.flatMap( conn -> {
            Optional<Watch> watch = Optional.empty();
            String query = "SELECT * FROM movie_watches WHERE id=" + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    Date date = resultSet.getDate("date");
                    Movie movie = movieService.getMovie(resultSet.getInt("movie_id"));
                    Platform platform = platformService.getPlatform(resultSet.getInt("platform_id"));
                    float cost = resultSet.getFloat("cost");

                    watch = Optional.of(
                            new Watch(id, date, movie, platform, cost));
                }
            } catch (SQLException | NonExistentMovieException | NonExistentPlatformException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return watch;
        });
    }

    public Optional<Watch> getByDate(Date date) {
        return connection.flatMap(conn -> {
            Optional<Watch> watch = Optional.empty();
            String query = "SELECT * FROM movie_watches WHERE date = ?";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setDate(1, date);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Movie movie = movieService.getMovie(resultSet.getInt("movie_id"));
                    Platform platform = platformService.getPlatform(resultSet.getInt("platform_id"));
                    float cost = resultSet.getFloat("cost");

                    watch = Optional.of(
                            new Watch(id, date, movie, platform, cost));
                }

            } catch (SQLException | NonExistentMovieException | NonExistentPlatformException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return watch;
        });
    }

    /**
     * Default order by date
     */
    @Override
    public Collection<Watch> getAll() {
        Collection<Watch> watches = new ArrayList<>();
        String query = "SELECT * FROM movie_watches ORDER BY date";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date date = resultSet.getDate("date");
                    Movie movie = movieService.getMovie(resultSet.getInt("movie_id"));
                    Platform platform = platformService.getPlatform(resultSet.getInt("platform_id"));
                    Float cost = resultSet.getFloat("cost");

                    Watch watch = new Watch(id, date, movie, platform, cost);

                    watches.add(watch);
                }

            } catch (SQLException | NonExistentMovieException | NonExistentPlatformException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });

        return watches;
    }

    @Override
    public Optional<Integer> save(Watch watch) {
        String message = "Watch cannot be null";
        Watch nonNullWatch = Objects.requireNonNull(watch, message);
        String query = "INSERT INTO "
                + "movie_watches(date, movie_id, platform_id, cost) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
                )
            ) {
                statement.setDate(1, nonNullWatch.getDate());
                statement.setInt(2, nonNullWatch.getMovie().getId());
                statement.setInt(3, nonNullWatch.getPlatform().getId());
                statement.setFloat(4, nonNullWatch.getCost());

                int insertedNumberOfRows = statement.executeUpdate();

                // Retrieve auto-generated id
                if (insertedNumberOfRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Watch watch) {
        String message = "The watch to be update should not be null";
        Watch nonNullWatch = Objects.requireNonNull(watch, message);
        String query = "UPDATE movie_watches SET "
                + "date = ?, "
                + "movie_id = ?, "
                + "platform_id = ?, "
                + "cost = ? "
                + "WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setDate(1, nonNullWatch.getDate());
                statement.setInt(2, nonNullWatch.getMovie().getId());
                statement.setInt(3, nonNullWatch.getPlatform().getId());
                statement.setFloat(4, nonNullWatch.getCost());
                statement.setInt(5, nonNullWatch.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the movie_watch update successfully? ",
                        numberOfUpdatedRows > 0);

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });
    }

    @Override
    public void delete(Watch watch) {
         String message = "The watch to be deleted should not be null";
         Watch nonNullWatch = Objects.requireNonNull(watch, message);
         String query = "DELETE FROM movie_watches WHERE id = ?";

         connection.ifPresent(conn -> {
             try (PreparedStatement statement = conn.prepareStatement(query)) {
                 statement.setInt(1,nonNullWatch.getId());

                 int numberOfDeletedRows = statement.executeUpdate();

                 LOGGER.log(Level.INFO, "Was the movie deleted successfully? ",
                         numberOfDeletedRows > 0);

             } catch (SQLException e) {
                 LOGGER.log(Level.SEVERE, null, e);
             }
         });
    }
}
