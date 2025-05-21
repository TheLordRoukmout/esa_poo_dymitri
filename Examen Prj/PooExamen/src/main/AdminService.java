package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {

    public boolean addCircuit(Circuit circuit){
        if(circuitAlreadyExist(circuit.getNom())){
            System.out.println("Le circuit: " + circuit.getNom() + "existe déja.");
            return false;
        }

        String sql = "INSERT INTO Circuits(nom_circuit, adresse_circuit, tarif_circuit) VALUES (?, ?, ?)";
        try (Connection conn = ConnexionData.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, circuit.getNom());
            stmt.setString(2, circuit.getAdresse());
            stmt.setDouble(3, circuit.getTarif());

            int lignes = stmt.executeUpdate();
            return lignes > 0;
        }catch (SQLException e){
            System.err.println("Erreur lors de l'ajout du circuit " + e.getMessage());
            return false;
        }
    }

    public boolean circuitAlreadyExist(String nom){
        String sqlRequest = "SELECT 1 FROM Circuits WHERE nom_circuit = ? LIMIT 1";
        try (Connection conn = ConnexionData.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlRequest)){
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }catch (SQLException e){
            System.err.println("Error for create the circuit: " + e.getMessage());
            return false;
        }
    }

    public void supprimerCircuitParNom(String nom) {
        String sql = "DELETE FROM Circuits WHERE nom_circuit = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            int rows = stmt.executeUpdate();
            System.out.println("🧹 Suppression : " + rows + " ligne(s) supprimée(s).");

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du circuit : " + e.getMessage());
        }
    }
}
