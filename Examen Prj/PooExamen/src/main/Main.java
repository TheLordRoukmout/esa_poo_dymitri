package main;

import main.obj.Circuit;
import main.obj.Voitures;

public class Main {
    public static void main(String[] args) {
        LoginService login = new LoginService();

        // Connexion avec l'admin
        LoginResult adminResult = login.loginAdmin("admin@tracktoys.com", "admin123");
        System.out.println(adminResult.message);

        if(adminResult.success){
            AdminService adminAction = new AdminService();

            // --- Ajout du circuit ---
            Circuit circuit = new Circuit("Spa Francorchamps", "Liège spa", 80.00, 6);
            if(adminAction.addCircuit(circuit)){
                System.out.println("✅ Circuit " + circuit.getNom() + " ajouté !");
            } else {
                System.out.println("❌ Circuit déjà existant.");
            }

            // --- Ajout de la voiture ---
            Voitures voiture = new Voitures("Porsche 911 GT3", 510, 250.0, true);

            if(adminAction.voitureAlreadyExist(voiture.getModele())){
                System.out.println("⚠️ Voiture " + voiture.getModele() + " existe déjà");
            }

            if(adminAction.addVoiture(voiture)){
                System.out.println("✅ Voiture: "+ voiture.getModele() + " ajoutée !");

                // Récupérer l’ID de la voiture qu’on vient d’ajouter
                int idVoiture = adminAction.getLastVoitureIdByModele(voiture.getModele());

                // Ajouter les infos mécaniques manuellement
                boolean ficheOK = adminAction.addInfoMechaData(idVoiture, 80.0, 8.0, 0.00, "Roulable");

                if (ficheOK) {
                    System.out.println("✅ Fiche mécanique ajoutée !");
                } else {
                    System.out.println("❌ Erreur ajout fiche méca.");
                }

            } else {
                System.out.println("❌ Échec de l'ajout de la voiture");
            }

            // Suppression test
            adminAction.supprimerCircuitParNom(circuit.getNom());
            adminAction.supprimerVoiture(1);
        }

        // Connexion avec le client
        LoginResult result = login.loginClient("client@tracktoys.com", "1234");
        System.out.println(result.message);
    }
}

