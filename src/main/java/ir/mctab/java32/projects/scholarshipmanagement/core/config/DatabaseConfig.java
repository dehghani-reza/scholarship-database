package ir.mctab.java32.projects.scholarshipmanagement.core.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/scholarship";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    private static Connection connection;

    public static Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } else {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
        }
        return connection;
    }
}
