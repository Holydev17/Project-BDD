package ehotels.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/ehotels";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234"; // ton mot de passe

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL introuvable", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}