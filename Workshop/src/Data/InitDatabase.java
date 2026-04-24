package Data;

import java.sql.Connection;
import java.sql.Statement;

public class InitDatabase {

    public static void init() {

        String customerTable = """
            CREATE TABLE IF NOT EXISTS Customer (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_card TEXT UNIQUE NOT NULL,
                first_name TEXT NOT NULL,
                last_name1 TEXT NOT NULL,
                last_name2 TEXT,
                phone TEXT NOT NULL,
                address TEXT,

                CHECK (length(id_card) = 9 AND id_card GLOB '[0-9]*'),
                CHECK (length(phone) = 8 AND phone GLOB '[0-9]*'),
                CHECK (length(first_name) <= 50),
                CHECK (length(last_name1) <= 50),
                CHECK (length(last_name2) <= 50),
                CHECK (length(address) <= 150)
            );
        """;

        String technicianTable = """
            CREATE TABLE IF NOT EXISTS Technician (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_card TEXT UNIQUE NOT NULL,
                first_name TEXT NOT NULL,
                last_name1 TEXT NOT NULL,
                last_name2 TEXT,
                phone TEXT NOT NULL,

                CHECK (length(id_card) = 9 AND id_card GLOB '[0-9]*'),
                CHECK (length(phone) = 8 AND phone GLOB '[0-9]*'),
                CHECK (length(first_name) <= 50),
                CHECK (length(last_name1) <= 50),
                CHECK (length(last_name2) <= 50)
            );
        """;

        String workTable = """
            CREATE TABLE IF NOT EXISTS Work (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                customer_id INTEGER NOT NULL,
                technician_id INTEGER NOT NULL,
                description TEXT NOT NULL,
                status TEXT NOT NULL,
                date_time TEXT NOT NULL,

                CHECK (length(description) <= 255),
                CHECK (status IN ('PENDING', 'IN_PROGRESS', 'DONE')),

                FOREIGN KEY (customer_id) REFERENCES Customer(id),
                FOREIGN KEY (technician_id) REFERENCES Technician(id)
            );
        """;

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(customerTable);
            stmt.execute(technicianTable);
            stmt.execute(workTable);

            System.out.println("Base de datos inicializada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}