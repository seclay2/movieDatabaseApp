package io.github.seclay2;

import java.sql.*;

public class ConnectionManager {

    private Connection connection;

    ConnectionManager(String url, String user, String pass) {
        this.connection = openConnection(url, user, pass);
        if (connection != null) queryTest();
    }

    public Connection openConnection(String url, String user, String pass) {
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void queryTest() {
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from movies_view");
            while (rs.next()) {
                // Retrieve by column name
                System.out.print(rs.getDate("date"));
                System.out.print(", " + rs.getString("title"));
                System.out.print(", " + rs.getInt("release_year"));
                System.out.print(", " + rs.getInt("runtime"));
                System.out.print(", " + rs.getString("platform_name"));
                System.out.print(", " + rs.getFloat("cost"));
                System.out.print("\n");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
