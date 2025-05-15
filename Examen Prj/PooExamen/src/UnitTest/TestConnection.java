package UnitTest;

import main.ConnexionData;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class TestConnection {

    @Test
    void testGetConnection() throws SQLException {
        Connection conn = ConnexionData.getConnection();

        assertNotNull(conn, "La connexion ne devrait pas être null");
        assertFalse(conn.isClosed(), "La connexion devrait être ouverte");

        conn.close();

    }
}
