package io.github.seclay2;

import io.github.seclay2.api.Movie;
import io.github.seclay2.api.PostgreSqlDao;
import org.junit.Test;

import java.util.Optional;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ApiTest {

    Movie movie;

    public ApiTest() {
        movie = new Movie("Test",2000,120,"tt123456");
    }

    @Test
    public void crdTest() {
        Handler ch = new ConsoleHandler();
        Logger.getLogger("").addHandler(ch);
        Logger.getLogger("io.github.seclay2").setLevel(Level.FINEST);

        PostgreSqlDao dao = new PostgreSqlDao();
        Optional<Integer> movieId = dao.save(movie);
        assertTrue(movieId.isPresent());
        Optional<Movie> movieRet = dao.get(movieId.get());
        assertTrue(movieRet.isPresent());
        assertEquals(movieRet.get().getTitle(), movie.getTitle());
        assertEquals(movieRet.get().getReleaseYear(), movie.getReleaseYear());
        assertEquals(movieRet.get().getRuntime(), movie.getRuntime());
        assertEquals(movieRet.get().getImdbId(), movie.getImdbId());

        dao.delete(movieRet.get());
        assertFalse(dao.get(movieId.get()).isPresent());

    }
}
