package io.github.seclay2.api.service;

import io.github.seclay2.api.NonExistentMovieException;
import io.github.seclay2.api.entity.Movie;

import java.util.Collection;
import java.util.Optional;

public interface MovieService {

    public Movie getMovie(int id) throws NonExistentMovieException;

    public Collection<Movie> getAllMovies();

    public void updateMovie(Movie movie);

    public Optional<Integer> addMovie(Movie movie);

    public void deleteMovie(Movie movie);
}
