package Database;

import Data.DatabaseConnection;
import java.sql.Connection;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DatabaseTest {

    @Test
    public void tryConnectDatabase() {
        Connection conn = DatabaseConnection.connect();
        assertNotNull(conn);
    }
}