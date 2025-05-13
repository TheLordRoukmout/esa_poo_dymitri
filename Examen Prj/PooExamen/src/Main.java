import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = ConnexionData.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clients")) {

            while (rs.next()) {
                System.out.println("Nom: " + rs.getString("nom"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
