package services;

import main.ConnexionData;
import main.LoginResult;
import main.obj.Admin;
import main.obj.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service responsable de la gestion des connexions et déconnexions pour les utilisateurs (admins et clients).
 */
public class LoginService {

    private Admin currentAdmin = null;
    private Client currentClient = null;

    /**
     * Vérifie si un administrateur est actuellement connecté.
     *
     * @return true si un admin est connecté, false sinon
     */
    public boolean isAdminConnected() {
        return currentAdmin != null;
    }

    /**
     * Tentative de connexion en tant que client si la connexion admin a échoué.
     *
     * @param mail     email du client
     * @param password mot de passe du client
     * @return un objet {@link LoginResult} indiquant le succès ou l'échec de la connexion
     */
    private LoginResult loginClientAsFallback(String mail, String password) {
        String sqlClient = "SELECT * FROM Clients WHERE mail_client = ?";

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

            this.currentClient = new Client(
                    rs.getInt("id_client"),
                    rs.getString("nom_client"),
                    rs.getString("prenom_client"),
                    rs.getInt("age_client"),
                    rs.getString("numPermis_client"),
                    mail,
                    passwordEnBase,
                    rs.getInt("id_role")
            );

            return new LoginResult(true, "✅ Connexion client réussie.", this.currentClient);

        } catch (SQLException e) {
            return new LoginResult(false, "❌ Erreur SQL (client) : " + e.getMessage());
        }
    }

    /**
     * Tente de connecter un administrateur à partir de son email et mot de passe.
     * Si l'utilisateur n'est pas admin, essaie une connexion client.
     *
     * @param mail     email de l'utilisateur
     * @param password mot de passe
     * @return un objet {@link LoginResult} indiquant le succès ou l'échec de la connexion
     */
    public LoginResult loginAdmin(String mail, String password) {
        String sqlAdmin = "SELECT a.id_admin, a.nom_admin, a.prenom_admin, a.mail_admin, " +
                "a.password_admin, a.id_role, r.nom_role " +
                "FROM Admin a " +
                "JOIN Roles r ON a.id_role = r.id_role " +
                "WHERE mail_admin = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmtAdmin = conn.prepareStatement(sqlAdmin)) {

            stmtAdmin.setString(1, mail);
            ResultSet rsAdmin = stmtAdmin.executeQuery();

            if (rsAdmin.next()) {
                String passwordEnBase = rsAdmin.getString("password_admin");
                String role = rsAdmin.getString("nom_role");

                if (!passwordEnBase.equals(password)) {
                    return new LoginResult(false, "❌ Mot de passe incorrect pour le compte admin.");
                }

                if ("admin".equalsIgnoreCase(role)) {
                    this.currentAdmin = new Admin(
                            rsAdmin.getInt("id_admin"),
                            rsAdmin.getString("nom_admin"),
                            rsAdmin.getString("prenom_admin"),
                            mail,
                            passwordEnBase,
                            rsAdmin.getInt("id_role")
                    );

                    return new LoginResult(true, "✅ Connexion admin réussie.", this.currentAdmin);
                } else {
                    return loginClientAsFallback(mail, password);
                }

            } else {
                return loginClientAsFallback(mail, password);
            }

        } catch (SQLException e) {
            return new LoginResult(false, "❌ Erreur SQL (admin) : " + e.getMessage());
        }
    }

    /**
     * Tente de connecter un client à partir de son email et mot de passe.
     *
     * @param mail     email du client
     * @param password mot de passe du client
     * @return un objet {@link LoginResult} contenant les informations de connexion
     */
    public LoginResult loginClient(String mail, String password) {
        String sql = "SELECT * FROM Clients WHERE mail_client = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return new LoginResult(false, "❌ Aucun compte trouvé avec cet email.");
            }

            String passWordDataBase = rs.getString("password_client");
            if (!passWordDataBase.equals(password)) {
                return new LoginResult(false, "❌ Mot de passe incorrect");
            }

            Client client = new Client(
                    rs.getInt("id_client"),
                    rs.getString("nom_client"),
                    rs.getString("prenom_client"),
                    rs.getInt("age_client"),
                    rs.getString("numPermis_client"),
                    rs.getString("mail_client"),
                    passWordDataBase,
                    rs.getInt("id_role")
            );

            this.currentClient = client;
            return new LoginResult(true, "✅ Connexion client réussie.", client);

        } catch (SQLException e) {
            return new LoginResult(false, "❌ Erreur SQL : " + e.getMessage());
        }
    }

    /**
     * Déconnecte l'utilisateur actuellement connecté (admin ou client).
     *
     * @return un objet {@link LoginResult} indiquant le succès ou l'échec de la déconnexion
     */
    public LoginResult logout() {
        if (currentAdmin != null) {
            System.out.println("Déconnexion de " + currentAdmin.getNom() + " en cours...");
            currentAdmin = null;
            return new LoginResult(true, "Déconnexion réussie.");
        }

        if (currentClient != null) {
            System.out.println("Déconnexion de " + currentClient.getNom() + " en cours...");
            currentClient = null;
            return new LoginResult(true, "Déconnexion réussie");
        }

        return new LoginResult(false, "❌ Erreur : aucun utilisateur n'était connecté.");
    }
}
