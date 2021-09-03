package io.github.seclay2.api;

public class NonExistentWatchException extends NonExistentEntityException {
    public NonExistentWatchException() {
        super("Watch does not exist");
    }
}
