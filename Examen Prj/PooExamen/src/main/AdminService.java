package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {

    public boolean addCircuit(Circuit circuit){
        // Vérifications avant insertion
        if (circuit.getNom() == null || circuit.getNom().trim().isEmpty()) {
            System.out.println("Le nom du circuit est vide.");
            return false;
        }

        if (circuit.getAdresse() == null || circuit.getAdresse().trim().isEmpty()) {
            System.out.println("L'adresse du circuit est vide.");
            return false;
        }

        if (circuit.getTarif() <= 0) {
            System.out.println("Le tarif du circuit doit être supérieur à 0.");
            return false;
        }

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
            System.out.println("Suppression : " + rows + " ligne(s) supprimée(s).");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du circuit : " + e.getMessage());
        }
    }

    public boolean addVoiture(Voitures voiture){
        if(voiture.getModele() == null || voiture.getModele().trim().isEmpty()) {
            System.out.println("Le modele du voiture est vide.");
            return false;
        }

        if(voiture.getPuissance() <=0){
            System.out.println("Le puissance du voiture est vide.");
            return false;
        }

        String sqlRequest = "INSERT INTO Voitures(model_voiture, puissance_voiture, priceLocation_voiture, disponibilite_voiture) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnexionData.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlRequest)){

            stmt.setString(1, voiture.getModele());
            stmt.setInt(2, voiture.getPuissance());
            stmt.setDouble(3, voiture.getPrixLocation());
            stmt.setInt(4, voiture.getDisponible() ? 1 : 0);

            int lignes = stmt.executeUpdate();
            return lignes > 0;

        }catch (SQLException e){
            System.out.println("Erreur lors de l'ajour de la voiture: " + e.getMessage());
            return false;
        }
    }

    public void supprimerVoiture(int idVotiure){
        String sqlRequest = "DELETE FROM Voitures WHERE id_Voiture = ?";

        try (Connection conn = ConnexionData.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlRequest)){

            stmt.setInt(1, idVotiure);
            int lignes = stmt.executeUpdate();

            if(lignes > 0){
                System.out.println("Suppression : " + lignes + " voiture(s).");
            }else {
                System.out.println("Auncune voiture trouvé avec l'id donné.");
            }
        }catch (SQLException e){
            System.out.println("Erreur lors de la suppression du voiture: " + e.getMessage());
        }
    }

}
