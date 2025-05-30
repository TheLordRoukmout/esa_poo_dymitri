package services;

import main.ConnexionData;
import main.obj.Evenement;
import main.obj.Voitures;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public List<Evenement> getEvenementsDisponibles() {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT nom_evenement, date_evenement, description_evenement, id_circuit FROM Evenements";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Evenement event = new Evenement(
                        rs.getString("nom_evenement"),
                        rs.getString("date_evenement"),
                        rs.getString("description_evenement"),
                        rs.getInt("id_circuit")
                );
                evenements.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des évènements: " + e.getMessage());
        }
        return evenements;
    }

    public void avertissementSiAucunEvenement() {
        List<Evenement> evenements = getEvenementsDisponibles();

        if (evenements.isEmpty()) {
            System.out.println("Aucun événement disponible pour le moment.");
            System.out.println("Veuillez consulter notre site plus tard ou contacter le service client.");
        } else {
            System.out.printf("%d événement(s) disponible(s)%n", evenements.size());
        }
    }

    public boolean reserverEvenement(int idClient, Evenement evenement, Voitures voiture, String dateReservation) throws VoitureIndisponibleException {
        if (voiture.getIdVoiture() == null) {
            System.err.println("Erreur : la voiture n'a pas d'ID valide, réservation impossible.");
            return false;
        }

        try {
            if (!estVoitureDisponible(voiture.getIdVoiture(), dateReservation)) {
                throw new VoitureIndisponibleException("La voiture " + voiture.getModele() + " n'est pas disponible à la date " + dateReservation);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de la disponibilité : " + e.getMessage());
            return false;
        }

        String sql = "INSERT INTO Reservation(nom_event, id_client, id_voiture, id_circuit, startDate_reservation, endDate_reservation, prixSeance_reservation, status_reservation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        double prixTotal = voiture.getPrixLocation(); // Prix d'une journée

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evenement.getNomEvenement());
            stmt.setInt(2, idClient);
            stmt.setInt(3, voiture.getIdVoiture());
            stmt.setInt(4, evenement.getIdCircuit());
            stmt.setString(5, dateReservation);
            stmt.setString(6, dateReservation);
            stmt.setDouble(7, prixTotal);
            stmt.setInt(8, 1); // statut réservation active ou en attente

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Réservation enregistrée avec succès !");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la réservation : " + e.getMessage());
        }
        return false;
    }


    public Voitures getVoitureById(int idVoiture) {
        String sql = "SELECT id_voiture, model_voiture, puissance_voiture, priceLocation_voiture, disponibilite_voiture FROM Voitures WHERE id_voiture = ?";
        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVoiture);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Voitures(
                        rs.getInt("id_voiture"),
                        rs.getString("model_voiture"),
                        rs.getInt("puissance_voiture"),
                        rs.getDouble("priceLocation_voiture"),
                        rs.getInt("disponibilite_voiture") == 1 // convertir int -> boolean
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // voiture pas trouvée
    }

    // rendre static pour pouvoir l'utiliser depuis l'extérieur sans instance ClientService
    public static class VoitureIndisponibleException extends Exception {
        public VoitureIndisponibleException(String message) {
            super(message);
        }
    }

    public boolean estVoitureDisponible(int idVoiture, String dateReservation) throws SQLException {
        String sqlDisponibilite = "SELECT disponibilite_voiture FROM Voitures WHERE id_voiture = ?";
        String sqlReservation = "SELECT COUNT(*) AS count FROM Reservation WHERE id_voiture = ? AND startDate_reservation = ? AND status_reservation = 1";

        try (Connection conn = ConnexionData.getConnection()) {
            // Vérifie si la voiture est marquée disponible
            try (PreparedStatement stmt = conn.prepareStatement(sqlDisponibilite)) {
                stmt.setInt(1, idVoiture);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        boolean dispo = rs.getInt("disponibilite_voiture") == 1;
                        if (!dispo) {
                            return false; // voiture marquée indisponible
                        }
                    } else {
                        return false; // voiture inexistante
                    }
                }
            }

            // Vérifie si la voiture n’est pas déjà réservée à cette date (status_reservation = 1 = active)
            try (PreparedStatement stmt = conn.prepareStatement(sqlReservation)) {
                stmt.setInt(1, idVoiture);
                stmt.setString(2, dateReservation);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        return count == 0; // dispo si aucune réservation active ce jour-là
                    }
                }
            }
        }
        return false; // par défaut non dispo
    }


    public boolean annulerReservation(int idClient, String nomEvenement) {
        String sql = "DELETE FROM Reservation WHERE id_client = ? AND nom_event = ? AND status_reservation = 1";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            stmt.setString(2, nomEvenement);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Réservation annulée avec succès !");
                return true;
            } else {
                System.out.println("Aucune réservation active trouvée pour annulation.");
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'annulation de la réservation : " + e.getMessage());
        }
        return false;
    }


}
