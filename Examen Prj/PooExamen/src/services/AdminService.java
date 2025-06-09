package services;

import main.ConnexionData;
import main.obj.Circuit;
import main.obj.Evenement;
import main.obj.Voitures;

import java.sql.*;

/**
 * Service dédié aux actions d'administration :
 * ajout, suppression et vérification de circuits, de voitures et de fiches mécaniques.
 * Certaines méthodes nécessitent une authentification en tant qu'administrateur.
 */


public class AdminService {

    private final LoginService loginService;

    /**
     * Constructeur sans vérification d'identité.
     * À utiliser uniquement pour des tests ou des cas sans session active.
     */


    public AdminService() {
        this.loginService = null;
    }

    /**
     * Constructeur avec contrôle d'accès basé sur une session d'admin.
     *
     * @param loginService instance de LoginService pour vérifier les droits admin
     */

    public AdminService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Ajoute un nouveau circuit à la base de données après vérifications.
     *
     * @param circuit le circuit à ajouter
     * @return true si l'ajout a réussi, false sinon
     */

    public boolean addCircuit(Circuit circuit){
        if(loginService != null && !loginService.isAdminConnected()){
            System.out.println("Action réfusé vous devez être admin");
            return false;
        }
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

    /**
     * Vérifie si un circuit existe déjà dans la base.
     *
     * @param nom le nom du circuit à vérifier
     * @return true si le circuit existe déjà, false sinon
     */

    public boolean circuitAlreadyExist(String nom){
        if(loginService != null && !loginService.isAdminConnected()){
            System.out.println("Action réfusé vous devez être admin");
            return false;
        }

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

    /**
     * Supprime un circuit de la base selon son nom.
     *
     * @param nom le nom du circuit à supprimer
     * @return true si la suppression a eu lieu, false sinon
     */

    public boolean supprimerCircuitParNom(String nom) {
        if(loginService != null && !loginService.isAdminConnected()){
            System.out.println("Action réfusé vous devez être admin");
            return false;
        }

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

    /**
     * Ajoute une voiture à la base de données.
     * Si l'ajout est un succès, une fiche mécanique par défaut est aussi créée.
     *
     * @param voiture la voiture à ajouter
     * @return true si la voiture et la fiche ont été ajoutées, false sinon
     */

    public boolean addVoiture(Voitures voiture){
        if(loginService != null && !loginService.isAdminConnected()){
            System.out.println("Action réfusé vous devez être admin");
            return false;
        }

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

    /**
     * Vérifie si une voiture existe déjà dans la base de données.
     *
     * @param nomVotiure le nom du modèle de la voiture
     * @return true si elle existe, false sinon
     */

    public boolean supprimerVoiture(String nomVotiure){
        if(loginService != null && !loginService.isAdminConnected()){
            System.out.println("Action réfusé vous devez être admin");
            return false;
        }

        String sqlRequest = "DELETE FROM Voitures WHERE model_Voiture = ?";

        try (Connection conn = ConnexionData.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlRequest)){

            stmt.setString(1, nomVotiure);
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

    /**
     * Vérifie si une voiture existe déjà dans la base de données.
     *
     * @param nom le nom du modèle de la voiture
     * @return true si elle existe, false sinon
     */

    public boolean voitureAlreadyExist(String nom){
        if(loginService != null && !loginService.isAdminConnected()){
            System.out.println("Action réfusé vous devez être admin");
            return false;
        }

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


    /**
     * Ajoute une fiche mécanique à une voiture donnée.
     *
     * @param idVoiture   l'ID de la voiture
     * @param fuelMax     capacité maximale du réservoir
     * @param fuelLive    carburant restant
     * @param kilometrage kilométrage actuel
     * @param etat        état mécanique (ex. "Roulable")
     * @return true si la fiche a été ajoutée, false sinon
     */

    public boolean addInfoMechaData(int idVoiture, double fuelMax, double fuelLive, double kilometrage, String etat) {
        if(loginService != null && !loginService.isAdminConnected()){
            System.out.println("Action réfusé vous devez être admin");
            return false;
        }

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


    /**
     * Récupère l'ID de la dernière voiture ajoutée correspondant à un modèle donné.
     *
     * @param modele le modèle recherché
     * @return l'ID de la voiture ou -1 si non trouvé
     */

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

    /**
     * Récupère un événement depuis la base en fonction de son nom.
     *
     * @param nomEvenement nom de l'événement
     * @return l'objet Evenement correspondant ou null si non trouvé
     */

    public Evenement getEvenementByNom(String nomEvenement) {
        String sql = "SELECT * FROM Evenements WHERE nom_evenement = ?";

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomEvenement);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Evenement(
                        rs.getString("nom_evenement"),
                        rs.getString("date_evenement"),
                        rs.getString("description_evenement"),
                        rs.getInt("id_circuit")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération de l'événement : " + e.getMessage());
        }

        return null; // aucun événement trouvé
    }

    /**
     * Récupère une voiture à partir de son ID.
     *
     * @param idVoiture ID de la voiture
     * @return une instance de Voitures ou null si introuvable
     */

    public Voitures getVoitureById(int idVoiture) {
        String sql = "SELECT * FROM Voitures WHERE id_voiture = ?";

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
                        rs.getInt("disponibilite_voiture") == 1
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération de la voiture : " + e.getMessage());
        }

        return null; // aucune voiture trouvée
    }

    /**
     * Récupère le nom et le prénom d'un client ayant réservé un événement avec une voiture donnée.
     *
     * @param nomEvenement le nom de l'événement
     * @param idVoiture    l'identifiant de la voiture
     * @return une chaîne formatée "Prénom Nom" ou "Client inconnu" si non trouvé
     */

    public String getNomPrenomClientPourReservation(String nomEvenement, int idVoiture) {
        String sql = """
        SELECT nom_client, prenom_client
        FROM Reservation
        JOIN Clients ON Reservation.id_client = Clients.id_client
        WHERE Reservation.nom_event = ? AND Reservation.id_voiture = ?
        LIMIT 1
    """;

        try (Connection conn = ConnexionData.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomEvenement);
            stmt.setInt(2, idVoiture);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("prenom_client") + " " + rs.getString("nom_client");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération du client : " + e.getMessage());
        }

        return "Client inconnu";
    }




}
