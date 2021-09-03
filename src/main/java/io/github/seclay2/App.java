package io.github.seclay2;

import io.github.seclay2.api.NonExistentWatchException;
import io.github.seclay2.api.service.*;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final Logger LOGGER =
            Logger.getLogger(App.class.getName());

    public static void main( String[] args )
    {
        MovieServiceImpl movieService = new MovieServiceImpl();
        PlatformServiceImpl platformService = new PlatformServiceImpl();
        WatchServiceImpl watchService = new WatchServiceImpl();

        // test add new movie
//        Movie movie = new Movie("Without Remorse",2021,109,"tt0499097");
//        movieService.addMovie(movie).ifPresent(movie::setId);
//        System.out.println(movie);

        // test getting all platforms
        platformService.getAllPlatforms().forEach(System.out::println);

        // test getting watch by date and id
        Date date = Date.valueOf("2021-08-3");
        try {
            System.out.println(watchService.getWatchByDate(date));
            System.out.println(watchService.getWatch(3));
        } catch (NonExistentWatchException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }
}
