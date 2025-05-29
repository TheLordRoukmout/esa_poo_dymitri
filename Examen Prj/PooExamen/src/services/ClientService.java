package services;

import main.ConnexionData;
import main.obj.Evenement;

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
}