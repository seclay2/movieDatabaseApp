package io.github.seclay2.api.service;

import io.github.seclay2.api.NonExistentPlatformException;
import io.github.seclay2.api.dao.Dao;
import io.github.seclay2.api.dao.PlatformDao;
import io.github.seclay2.api.entity.Platform;

import java.util.Collection;
import java.util.Optional;

public class PlatformServiceImpl implements PlatformService {

    private final Dao<Platform, Integer> PLATFORM_DAO = new PlatformDao();

    @Override
    public Platform getPlatform(int id) throws NonExistentPlatformException {
        Optional<Platform> platform = PLATFORM_DAO.get(id);
        return platform.orElseThrow(NonExistentPlatformException::new);
    }

    @Override
    public Collection<Platform> getAllPlatforms() {
        return PLATFORM_DAO.getAll();
    }

    @Override
    public void updatePlatform(Platform platform) {
        PLATFORM_DAO.update(platform);
    }

    @Override
    public Optional<Integer> addPlatform(Platform platform) {
        return PLATFORM_DAO.save(platform);
    }

    @Override
    public void deletePlatform(Platform platform) {
        PLATFORM_DAO.delete(platform);
    }
}
