package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; // Importar Statement

public class DatabaseConnection {

    private static String URL = "jdbc:sqlite:database.db";

    public static void setUrl(String url) {
        URL = url;
    }

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            
            // Habilitar la integridad referencial para esta conexión específica
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Error conectando a DB", e);
        }
    }
}