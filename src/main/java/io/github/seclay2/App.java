package io.github.seclay2;

import io.github.seclay2.api.*;

import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final Logger LOGGER =
            Logger.getLogger(App.class.getName());
    private static final Dao<Movie, Integer> MOVIE_DAO = new PostgreSqlDao();

    public static void main( String[] args )
    {
        // Test whether an exception id thrown when
        // the database is queried for a non-existent movie.
        // But, if the movie does exist, the details will be printed
        // on the console
        try {
            Movie movie = getMovie(1);
        } catch (NonExistentEntityException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }

        // Test whether a movie can be added to the database
        Movie firstMovie = new Movie("firstMovie", 2020, 100, "tt12345");
        addMovie(firstMovie).ifPresent(firstMovie::setId);

        Movie secondMovie = new Movie("secondMovie", 2020, 100, "tt23456");
        addMovie(secondMovie).ifPresent(secondMovie::setId);

        // Test whether new movie's details can be edited
        secondMovie.setReleaseYear(2000);
        secondMovie.setRuntime(90);
        updateMovie(secondMovie);

        // Test whether all movies can be read from database
        getAllMovies().forEach(System.out::println);

        // Test whether a movie can be deleted
        deleteMovie(firstMovie);
    }

    // static helper methods referenced above
    public static Movie getMovie(int id) throws NonExistentMovieException {
        Optional<Movie> movie = MOVIE_DAO.get(id);
        return movie.orElseThrow(NonExistentMovieException::new);
    }

    public static Collection<Movie> getAllMovies() {
        return MOVIE_DAO.getAll();
    }

    public static void updateMovie(Movie movie) {
        MOVIE_DAO.update(movie);
    }

    public static Optional<Integer> addMovie(Movie movie) {
        return MOVIE_DAO.save(movie);
    }

    public static void deleteMovie(Movie movie) {
        MOVIE_DAO.delete(movie);
    }
}
