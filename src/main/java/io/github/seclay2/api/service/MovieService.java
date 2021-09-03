package io.github.seclay2.api.service;

import io.github.seclay2.api.NonExistentMovieException;
import io.github.seclay2.api.entity.Movie;

import java.util.Collection;
import java.util.Optional;

public interface MovieService {

    Movie getMovie(int id) throws NonExistentMovieException;

    Collection<Movie> getAllMovies();

    void updateMovie(Movie movie);

    Optional<Integer> addMovie(Movie movie);

    void deleteMovie(Movie movie);
}
