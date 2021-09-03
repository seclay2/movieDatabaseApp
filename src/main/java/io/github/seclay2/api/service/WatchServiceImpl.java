package io.github.seclay2.api.service;

import io.github.seclay2.api.NonExistentWatchException;
import io.github.seclay2.api.dao.Dao;
import io.github.seclay2.api.dao.WatchDao;
import io.github.seclay2.api.entity.Watch;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

public class WatchServiceImpl implements WatchService {

    private final WatchDao WATCH_DAO = new WatchDao();

    @Override
    public Watch getWatch(int id) throws NonExistentWatchException {
        Optional<Watch> watch = WATCH_DAO.get(id);
        return watch.orElseThrow(NonExistentWatchException::new);
    }

    public Watch getWatchByDate(Date date) throws NonExistentWatchException {
        Optional<Watch> watch = WATCH_DAO.getByDate(date);
        return watch.orElseThrow(NonExistentWatchException::new);
    }

    @Override
    public Collection<Watch> getAllWatches() {
        return WATCH_DAO.getAll();
    }

    @Override
    public void updateWatch(Watch watch) {
        WATCH_DAO.update(watch);
    }

    @Override
    public Optional<Integer> addWatch(Watch watch) {
        return WATCH_DAO.save(watch);
    }

    @Override
    public void deleteWatch(Watch watch) {
        WATCH_DAO.delete(watch);
    }
}
