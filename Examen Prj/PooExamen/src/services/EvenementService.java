package services;

import main.ConnexionData;
import main.obj.Evenement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EvenementService {

    public boolean creatEvent(Evenement evenement){
        if(eventAlreadyExists(evenement.getNomEvenement())){
            System.out.println("Event avec ce nom existe déja.");
            return false;
        }

        String sqlRequest = "INSERT INTO Evenements(nom_evenement, date_evenement, description_evenement, id_circuit)"
                + "VALUES (?, ?, ?, ?)";

        try(Connection conn = ConnexionData.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlRequest)){

            stmt.setString(1, evenement.getNomEvenement());
            stmt.setString(2, evenement.getDateEvenement());
            stmt.setString(3, evenement.getDescriptionEvenement());
            stmt.setInt(4, evenement.getIdCircuit());

            int lignes = stmt.executeUpdate();

            if(lignes > 0){
                System.out.println("Evenement créer avec succès !");
                return true;
            }else {
                System.out.println("Aucune infos pour créer un évenement.");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Erreur lors de la création de l'évenement " + e.getMessage());
            return false;
        }
    }

    public boolean eventAlreadyExists(String nomEvenement){
        String sqlRequest = "SELECT 1 FROM Evenements WHERE nom_evenement= ?";

        try (Connection conn = ConnexionData.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlRequest)){

            stmt.setString(1, nomEvenement);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }catch (SQLException e){
            System.err.println("Erreur lors de la vérification de l'évent: " + e.getMessage());
            return false;
        }
    }

    public boolean deleEvent(String nomEvenement){
        String sqlRequest = "DELETE FROM Evenements WHERE nom_evenement= ?";

        try (Connection conn = ConnexionData.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlRequest)){

            stmt.setString(1, nomEvenement);
            int lignes = stmt.executeUpdate();

            if (lignes > 0){
                System.out.println("Evènement :" + nomEvenement + " supprimé.");
                return true;
            }else{
                System.out.println("Aucun évènement trouvé avec ce nom" );
                return false;
            }
        }catch (SQLException e){
            System.err.println("Erreur sql lors de la suppression: " + e.getMessage());
            return false;
        }
    }


}
