package io.github.seclay2.api;

public class NonExistentMovieException extends NonExistentEntityException {

    private static final long serialVersionUID = 8633588908169766368L;

    public NonExistentMovieException() {
        super("Movie does not exist");
    }
}
