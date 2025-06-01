package Simulation;


import main.ConnexionData;
import main.obj.Simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class SimulationRoulage {

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
