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

    @Test
    void testCreatingAdmin() throws SQLException {
        Connection conn = ConnexionData.getConnection();

        var stmt = conn.createStatement();
        var rs = stmt.executeQuery("SELECT COUNT (*) AS total FROM Admin WHERE mail_admin = 'admin@tracktoys.com'");

        rs.next();
        int total = rs.getInt("total");

        assertTrue(total >= 1, "Un compte admin devrait exister");

        rs.close();
        stmt.close();
        conn.close();

    }
}
