package io.github.seclay2.api.dao;

import io.github.seclay2.api.JdbcConnection;
import io.github.seclay2.api.entity.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieDao implements Dao<Movie, Integer> {

    private static final Logger LOGGER =
            Logger.getLogger(MovieDao.class.getName());
    private final Optional<Connection> connection;

    public MovieDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Movie> get(int id) {
        return connection.flatMap( conn -> {
            Optional<Movie> movie = Optional.empty();
            String query = "SELECT * FROM movies WHERE id=" + id;

            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    Integer releaseYear = resultSet.getInt("release_year");
                    Integer runtime = resultSet.getInt("runtime");
                    String imdbId = resultSet.getString("imdb_id");

                    movie = Optional.of(
                            new Movie(id, title, releaseYear, runtime, imdbId));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return movie;

        });
    }

    @Override
    public Collection<Movie> getAll() {
        Collection<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    int releaseYear = resultSet.getInt("release_year");
                    int runtime = resultSet.getInt("runtime");
                    String imdbId = resultSet.getString("imdb_id");

                    Movie movie = new Movie(id, title, releaseYear, runtime, imdbId);

                    movies.add(movie);
                }

                LOGGER.log(Level.INFO, "{0} movies found in database", movies.size());

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });

        return movies;
    }

    @Override
    public Optional<Integer> save(Movie movie) {
        String message = "Movie cannot be null";
        Movie nonNullMovie = Objects.requireNonNull(movie, message);
        String query = "INSERT INTO "
                + "movies(title,release_year,runtime,imdb_id) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                    conn.prepareStatement(
                            query,
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                statement.setString(1, nonNullMovie.getTitle());
                statement.setInt(2, nonNullMovie.getReleaseYear());
                statement.setInt(3, nonNullMovie.getRuntime());
                statement.setString(4, nonNullMovie.getImdbId());

                int numberOfInsertedRows = statement.executeUpdate();

                // Retrieve auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{ nonNullMovie, (numberOfInsertedRows > 0)});
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return generatedId;

        });
    }

    @Override
    public void update(Movie movie) {
        String message = "The movie to be updated should not be null";
        Movie nonNullMovie = Objects.requireNonNull(movie, message);
        String query = "UPDATE movies SET "
                + "title = ?, "
                + "release_year = ?, "
                + "runtime = ?, "
                + "imdb_id = ? "
                + "WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, nonNullMovie.getTitle());
                statement.setInt(2, nonNullMovie.getReleaseYear());
                statement.setInt(3, nonNullMovie.getRuntime());
                statement.setString(4, nonNullMovie.getImdbId());
                statement.setInt(5, nonNullMovie.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the movie updated successfully? {0}",
                        numberOfUpdatedRows > 0);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });
    }

    @Override
    public void delete(Movie movie) {
        String message = "The movie to be deleted should not be null";
        Movie nonNullMovie = Objects.requireNonNull(movie, message);
        String query = "DELETE FROM movies WHERE id = ?";

        connection.ifPresent( conn -> {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, nonNullMovie.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                 LOGGER.log(Level.INFO, "Was the movie deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        });
    }
}
