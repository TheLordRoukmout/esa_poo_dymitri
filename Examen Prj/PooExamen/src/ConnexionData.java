import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionData{
    //Méthode pour se connecter a la db
    public static Connection getConnexion() throws SQLException{
        String url = "jdbc:sqlite:tracktoys.db";
        return DriverManager.getConnection(url);
    }

    //Méthode pour tester la connexion
    public static void testConnection(){
        try (Connection conn = getConnexion()){
            System.out.println("Connection success");
        }catch (SQLException e){
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}