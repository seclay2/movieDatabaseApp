package io.github.seclay2.api.service;

import io.github.seclay2.api.dao.Dao;
import io.github.seclay2.api.NonExistentMovieException;
import io.github.seclay2.api.entity.Movie;
import io.github.seclay2.api.dao.MovieDao;

import java.util.Collection;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {

    private final Dao<Movie, Integer> MOVIE_DAO = new MovieDao();

    @Override
    public Movie getMovie(int id) throws NonExistentMovieException {
        Optional<Movie> movie = MOVIE_DAO.get(id);
        return movie.orElseThrow(NonExistentMovieException::new);
    }

    @Override
    public Collection<Movie> getAllMovies() {
        return MOVIE_DAO.getAll();
    }

    @Override
    public void updateMovie(Movie movie) {
        MOVIE_DAO.update(movie);
    }

    @Override
    public Optional<Integer> addMovie(Movie movie) {
        return MOVIE_DAO.save(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        MOVIE_DAO.delete(movie);
    }
}
