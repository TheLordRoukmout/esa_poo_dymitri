package main;

import main.obj.Circuit;
import main.obj.Voitures;

import java.sql.*;

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

        String sql = "INSERT INTO Circuits(nom_circuit, adresse_circuit, tarif_circuit, distance_circuit) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnexionData.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, circuit.getNom());
            stmt.setString(2, circuit.getAdresse());
            stmt.setDouble(3, circuit.getTarif());
            stmt.setDouble(4, circuit.getDistance());

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

    public boolean supprimerCircuitParNom(String nom) {
        String sql = "DELETE FROM Circuits WHERE nom_circuit = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            int rows = stmt.executeUpdate();
            System.out.println("Suppression du circuit: " + nom + " supprimé.");

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du circuit : " + e.getMessage());
            return false;
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
        PreparedStatement stmt = conn.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, voiture.getModele());
            stmt.setInt(2, voiture.getPuissance());
            stmt.setDouble(3, voiture.getPrixLocation());
            stmt.setInt(4, voiture.getDisponible() ? 1 : 0);

            int lignes = stmt.executeUpdate();


            if(lignes > 0){
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    int idVoiture = generatedKeys.getInt(1);

                    boolean infoMechaOk = addInfoMechaData(idVoiture, 50.0, 8.0, 0.0, "Roulable");
                    if(infoMechaOk){
                        System.out.println("✅ Voiture: " + voiture.getModele() + " avec fiche méchanique ajoutée.");
                    }else{
                        System.out.println("Voiture ajoutée mais pas d'information méchanique");
                    }

                    return true;
                }
            }

            return false;

        }catch (SQLException e){
            System.out.println("Erreur lors de l'ajout de la voiture: " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerVoiture(int idVotiure){
        String sqlRequest = "DELETE FROM Voitures WHERE id_Voiture = ?";

        try (Connection conn = ConnexionData.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlRequest)){

            stmt.setInt(1, idVotiure);
            int lignes = stmt.executeUpdate();

            if(lignes > 0){
                System.out.println("Suppression : " + lignes + " voiture" + " + sa fiche méchanique.");
                return true;
            }else {
                System.out.println("Auncune voiture trouvé avec l'id donné.");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Erreur lors de la suppression du voiture: " + e.getMessage());
            return false;
        }
    }

    public boolean voitureAlreadyExist(String nom){
        String sqlRequest = "SELECT 1 FROM Voitures WHERE model_voiture = ? LIMIT 1";
        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlRequest)){
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }catch (SQLException e){
            System.err.println("Error for create the car: " + e.getMessage());
            return false;
        }
    }


    public boolean addInfoMechaData(int idVoiture, double fuelMax, double fuelLive, double kilometrage, String etat) {
        String sql = "INSERT INTO InfoMecha(id_voiture, fuelMax_infomecha, fuelLive_infomecha, kilometrage_infomecha, etat_infomecha) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVoiture);
            stmt.setDouble(2, fuelMax);
            stmt.setDouble(3, fuelLive);
            stmt.setDouble(4, kilometrage);
            stmt.setString(5, etat);

            int lignes = stmt.executeUpdate();
            if (lignes > 0) {
                //System.out.println("✅ Fiche mécanique ajoutée avec succès !");
                return true;
            } else {
                System.out.println("❌ Aucune ligne ajoutée.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur ajout InfoMecha : " + e.getMessage());
            return false;
        }
    }


    public int getLastVoitureIdByModele(String modele){
        String sqlRequest = "SELECT id_voiture FROM Voitures WHERE model_voiture = ? ORDER BY id_voiture DESC LIMIT 1";
        try(Connection conn = ConnexionData.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlRequest)){
            stmt.setString(1, modele);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("id_voiture");
            }
        }catch (SQLException e){
            System.err.println("Erreur récupération ID voiture: " + e.getMessage());
        }
        return -1;
    }


}
