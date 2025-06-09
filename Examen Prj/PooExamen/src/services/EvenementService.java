package services;

import main.ConnexionData;
import main.obj.Evenement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service permettant la gestion des événements.
 * Permet la création, la suppression et la vérification d'existence d'événements en base de données.
 */
public class EvenementService {

    /**
     * Crée un nouvel événement dans la base de données s'il n'existe pas déjà.
     *
     * @param evenement l'événement à créer
     * @return true si l'événement a été ajouté avec succès, false sinon
     */
    public boolean creatEvent(Evenement evenement) {
        if (eventAlreadyExists(evenement.getNomEvenement())) {
            System.out.println("Event avec ce nom existe déjà.");
            return false;
        }

        String sqlRequest = "INSERT INTO Evenements(nom_evenement, date_evenement, description_evenement, id_circuit)"
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlRequest)) {

            stmt.setString(1, evenement.getNomEvenement());
            stmt.setString(2, evenement.getDateEvenement());
            stmt.setString(3, evenement.getDescriptionEvenement());
            stmt.setInt(4, evenement.getIdCircuit());

            int lignes = stmt.executeUpdate();

            if (lignes > 0) {
                System.out.println("Evenement créé avec succès !");
                return true;
            } else {
                System.out.println("Aucune infos pour créer un événement.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de l'événement : " + e.getMessage());
            return false;
        }
    }

    /**
     * Vérifie si un événement existe déjà dans la base de données.
     *
     * @param nomEvenement le nom de l'événement à vérifier
     * @return true si l'événement existe, false sinon
     */
    public boolean eventAlreadyExists(String nomEvenement) {
        String sqlRequest = "SELECT 1 FROM Evenements WHERE nom_evenement= ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlRequest)) {

            stmt.setString(1, nomEvenement);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'événement : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un événement de la base de données à partir de son nom.
     *
     * @param nomEvenement le nom de l'événement à supprimer
     * @return true si l'événement a été supprimé, false sinon
     */
    public boolean deleEvent(String nomEvenement) {
        String sqlRequest = "DELETE FROM Evenements WHERE nom_evenement= ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlRequest)) {

            stmt.setString(1, nomEvenement);
            int lignes = stmt.executeUpdate();

            if (lignes > 0) {
                System.out.println("Évènement : " + nomEvenement + " supprimé.");
                return true;
            } else {
                System.out.println("Aucun évènement trouvé avec ce nom.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la suppression : " + e.getMessage());
            return false;
        }
    }
}
