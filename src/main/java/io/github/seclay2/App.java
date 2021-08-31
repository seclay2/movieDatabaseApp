package io.github.seclay2;

/**
 * Hello world!
 *
 */
public class App 
{

    static final String URL = "jdbc:postgresql://172.28.85.52:5432/moviedb";
    static final String USER = "remote";
    static final String PASS = "tigers07";

    public static void main( String[] args )
    {
        ConnectionManager cm = new ConnectionManager(URL, USER, PASS);
        cm.close();
    }
}
