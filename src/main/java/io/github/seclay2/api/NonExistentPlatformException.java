package io.github.seclay2.api;

public class NonExistentPlatformException extends NonExistentEntityException {
    public NonExistentPlatformException() {
        super("Platform does not exist");
    }
}
