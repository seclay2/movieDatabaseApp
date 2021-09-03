package io.github.seclay2.api.service;

import io.github.seclay2.api.NonExistentWatchException;
import io.github.seclay2.api.entity.Watch;

import java.util.Collection;
import java.util.Optional;

public interface WatchService {

    public Watch getWatch(int id) throws NonExistentWatchException;

    public Collection<Watch> getAllWatches();

    public void updateWatch(Watch watch);

    public Optional<Integer> addWatch(Watch watch);

    public void deleteWatch(Watch watch);
}
