package io.github.seclay2.api.service;

import io.github.seclay2.api.NonExistentPlatformException;
import io.github.seclay2.api.entity.Platform;

import java.util.Collection;
import java.util.Optional;

public interface PlatformService {

    public Platform getPlatform(int id) throws NonExistentPlatformException;

    public Collection<Platform> getAllPlatforms();

    public void updatePlatform(Platform platform);

    public Optional<Integer> addPlatform(Platform platform);

    public void deletePlatform(Platform platform);
}
