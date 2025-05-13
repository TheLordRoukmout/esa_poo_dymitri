import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionData {
    private static final String URL = "jdbc:mysql://localhost:3306/tracktoys";
    private static final String USER = "postgres";
    private static final String PASS = "UHT808val201$";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
