package services;

import main.ConnexionData;
import main.LoginResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    private LoginResult loginClientAsFallback(String mail, String password) {
        String sqlClient = "SELECT password_client FROM Clients WHERE mail_client = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlClient)) {

            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return new LoginResult(false, "❌ Aucun compte trouvé avec cet email.");
            }

            String passwordEnBase = rs.getString("password_client");

            if (!passwordEnBase.equals(password)) {
                return new LoginResult(false, "❌ Mot de passe incorrect pour le compte client.");
            }

            return new LoginResult(true, "✅ Connexion client réussie.");

        } catch (SQLException e) {
            return new LoginResult(false, "❌ Erreur SQL (client) : " + e.getMessage());
        }
    }


    public LoginResult loginAdmin(String mail, String password) {
        // 1. Vérifier si un admin existe avec cet email
        String sqlAdmin = "SELECT password_admin, r.nom_role FROM Admin a " +
                "JOIN Roles r ON a.id_role = r.id_role " +
                "WHERE mail_admin = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmtAdmin = conn.prepareStatement(sqlAdmin)) {

            stmtAdmin.setString(1, mail);
            ResultSet rsAdmin = stmtAdmin.executeQuery();

            if (rsAdmin.next()) {
                String passwordEnBase = rsAdmin.getString("password_admin");
                String role = rsAdmin.getString("nom_role");

                // Email trouvé, on vérifie le mot de passe
                if (!passwordEnBase.equals(password)) {
                    return new LoginResult(false, "❌ Mot de passe incorrect pour le compte admin.");
                }

                // Mot de passe correct → vérifier le rôle
                if ("admin".equalsIgnoreCase(role)) {
                    return new LoginResult(true, "✅ Connexion admin réussie.");
                } else {
                    // Pas admin → tenter client
                    return loginClientAsFallback(mail, password);
                }

            } else {
                // Aucun admin avec cet email → tenter client
                return loginClientAsFallback(mail, password);
            }

        } catch (SQLException e) {
            return new LoginResult(false, "❌ Erreur SQL (admin) : " + e.getMessage());
        }
    }



    public LoginResult loginClient(String mail, String password) {
        String sql = "SELECT * FROM Clients WHERE mail_client = ? AND password_client = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mail);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return new LoginResult(false, "Fail: Wrong or Email not found.");
            }
            String passWordDataBase = rs.getString("password_client");
            if(!passWordDataBase.equals(password)){
                return new LoginResult(false, "Fail: Wrong password");
            }

            return new LoginResult(true, "Connection sucessfull !");

        } catch (SQLException e) {
            return new LoginResult(false, "Error SQL: " +e.getMessage());
        }
    }


}
