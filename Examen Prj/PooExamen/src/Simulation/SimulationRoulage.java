package Simulation;

import main.ConnexionData;
import main.obj.Simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe responsable de l'enregistrement des simulations de roulage (chronos) dans la base de données.
 */
public class SimulationRoulage {

    /**
     * Enregistre une simulation de roulage dans la table `Chronos` de la base de données.
     *
     * @param simulation L'objet {@link Simulation} contenant les données à enregistrer.
     * @return {@code true} si l'enregistrement a réussi, {@code false} sinon.
     */
    public boolean enregistrerSimulation(Simulation simulation) {
        String sql = "INSERT INTO Chronos(id_client, id_voiture, id_circuit, chronoStart, chronoEnd, date_chrono, id_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, simulation.getIdClient());
            stmt.setInt(2, simulation.getIdVoiture());
            stmt.setInt(3, simulation.getIdCircuit());
            stmt.setString(4, simulation.getChronoStart().toString());
            stmt.setString(5, simulation.getChronoEnd().toString());
            stmt.setString(6, simulation.getDate().toString());
            stmt.setInt(7, simulation.getIdAdmin());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'enregistrement du chrono : " + e.getMessage());
            return false;
        }
    }
}
