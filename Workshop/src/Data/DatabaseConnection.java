package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static String URL = "jdbc:sqlite:database.db";

    public static void setUrl(String url) {
        URL = url;
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Error conectando a DB", e);
        }
    }
}