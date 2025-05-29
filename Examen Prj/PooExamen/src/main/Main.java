package main;

import main.obj.Circuit;
import main.obj.Evenement;
import main.obj.Voitures;
import services.AdminService;
import services.ClientService;
import services.EvenementService;
import services.LoginService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Connexion en tant qu'admin....");

        LoginService login = new LoginService();

        // Connexion avec l'admin
        LoginResult adminResult = login.loginAdmin("admin@tracktoys.com", "admin123");
        System.out.println(adminResult.message);
        System.out.println("\n________________________________________ ");

        if(adminResult.success){
            System.out.println("L'admin créer un circuit et un véhicule pour l'évenement à venir.");
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

                // Récupérer l’ID de la voiture qu’on vient d’ajouter
                int idVoiture = adminAction.getLastVoitureIdByModele(voiture.getModele());

                // Ajouter les infos mécaniques manuellement
                boolean ficheOK = adminAction.addInfoMechaData(idVoiture, 80.0, 8.0, 0.00, "Roulable");

                if (ficheOK) {

                } else {
                    System.out.println("❌ Erreur ajout fiche méca.");
                }

            } else {
                System.out.println("❌ Échec de l'ajout de la voiture");
            }
            System.out.println("\n________________________________________ ");

            System.out.println("L'admin va créer un évènement.");
            Evenement evenementDemo = new Evenement("Spa en Juin", "2025-06-28", "Evenement pour les débutants et confirmé sur le circuit de spa Francorchamps", 1);
            EvenementService serviceEvenement = new EvenementService();
            if(serviceEvenement.creatEvent(evenementDemo)){
                System.out.println("Evènement " + evenementDemo.getNomEvenement() + " a été créér.");
            }
            System.out.println("\n________________________________________ ");
            LoginResult logoutResult = login.logout();
            System.out.println(logoutResult.message);

            // Suppression test
            //adminAction.supprimerCircuitParNom(circuit.getNom());
            //adminAction.supprimerVoiture("Porsche 911 GT3");
            //serviceEvenement.deleEvent("Spa en Juin");
        }

        // Connexion avec le client
        LoginResult result = login.loginClient("client@tracktoys.com", "1234");
        System.out.println(result.message);
        ClientService clientService = new ClientService();
        List<Evenement> evenementsDisponible = new ClientService().getEvenementsDisponibles();
        if(evenementsDisponible.isEmpty()){
            clientService.avertissementSiAucunEvenement();
        }else{
            System.out.println("Evenements disponible: ");
            for (Evenement e : evenementsDisponible){
                System.out.println("° " + e.getNomEvenement() + " le " + e.getDateEvenement());
            }
        }
    }
}

