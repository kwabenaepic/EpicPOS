package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection conn = null;


    public void close() {
        System.out.println("Closing connection");
        try {
            conn.close();
            conn = null;
        } catch (Exception e) {
        }
    }

    private static Connection con;

    /**
     * Singleton pattern
     * @return an instance of the connection
     */
    public static Connection getInstance() {
        if (con == null) {
            con = getConnection();
        }

        return con;
    }

    /**
     * creates a connection
     * @return the connection
     */
    public static Connection getConnection() {
        String dbUrl = "jdbc:sqlite:C:\\Users\\KwabenaEpic\\IdeaProjects\\EpicPOS\\src\\database\\posdata.db";
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(dbUrl);

            System.out.println("Opened database sucessfully");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getClass().getName() + " " + e.getMessage());
            System.exit(0);
        }

        return c;
    }

}
