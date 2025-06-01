package main;

import main.obj.*;
import services.AdminService;
import services.ClientService;
import services.EvenementService;
import services.LoginService;
import sessionRacing.SessionAdmin;
import Simulation.SimulationRoulage;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Connexion en tant qu'admin....");
        LoginService login = new LoginService();
        Voitures voiture = null;  // ✅ On déclare ici en haut pour l'utiliser partout

        // Connexion avec l'admin
        LoginResult adminResult = login.loginAdmin("admin@tracktoys.com", "admin123");
        System.out.println(adminResult.message);
        System.out.println("\n________________________________________ ");

        if(adminResult.success){
            System.out.println("L'admin créer un circuit et un véhicule pour l'évenement à venir.");
            AdminService adminAction = new AdminService();

            Circuit circuit = new Circuit("Spa Francorchamps", "Liège spa", 80.00, 22);
            if(adminAction.addCircuit(circuit)){
                System.out.println("✅ Circuit " + circuit.getNom() + " ajouté !");
            } else {
                System.out.println("❌ Circuit déjà existant.");
            }

            // --- Création et ajout voiture ---
            voiture = new Voitures("Porsche 911 GT3", 510, 250.0, true);  // Affectation à la variable globale

            if(adminAction.voitureAlreadyExist(voiture.getModele())){
                System.out.println("⚠️ Voiture " + voiture.getModele() + " existe déjà");
            }

            if(adminAction.addVoiture(voiture)){

                int idVoiture = adminAction.getLastVoitureIdByModele(voiture.getModele());

                ClientService clientServiceTemp = new ClientService();
                voiture = clientServiceTemp.getVoitureById(idVoiture);

                if (voiture == null || voiture.getIdVoiture() == null) {
                    System.out.println("❌ Impossible de récupérer la voiture avec son ID après ajout.");
                    return;
                }

                boolean ficheOK = adminAction.addInfoMechaData(idVoiture, 80.0, 8.0, 0.00, "Roulable");

                if (!ficheOK) {
                    System.out.println("❌ Erreur ajout fiche méca.");
                }

            } else {
                System.out.println("❌ Échec de l'ajout de la voiture");
            }

            System.out.println("\n________________________________________ ");

            Evenement evenementDemo = new Evenement("Spa en Juin", "2025-06-28", "Evenement pour les débutants et confirmé sur le circuit de spa Francorchamps", 1);
            EvenementService serviceEvenement = new EvenementService();
            if(serviceEvenement.creatEvent(evenementDemo)){
                System.out.println("Evènement " + evenementDemo.getNomEvenement() + " a été créér.");
            }
            System.out.println("\n________________________________________ ");
            LoginResult logoutResult = login.logout();
            System.out.println(logoutResult.message);
        }

        // Connexion avec le client
        LoginResult result = login.loginClient("client@tracktoys.com", "1234");
        Client clientConnecte = result.client;
        int idClient = clientConnecte.getIdClient();
        System.out.println(result.message);

        ClientService clientService = new ClientService();
        List<Evenement> evenementsDisponible = clientService.getEvenementsDisponibles();

        if(evenementsDisponible.isEmpty()){
            clientService.avertissementSiAucunEvenement();
        } else {
            System.out.println("Evenements disponible: ");
            for (Evenement e : evenementsDisponible){
                System.out.println("° " + e.getNomEvenement() + " le " + e.getDateEvenement());
            }

            try {
                Evenement evenementASuivre = null;
                for (Evenement ev : evenementsDisponible){
                    if(ev.getNomEvenement().equalsIgnoreCase("Spa en Juin")){  // ✅ ignoreCase pour éviter les erreurs de majuscules
                        evenementASuivre = ev;
                        break;
                    }
                }
                if (!clientService.clientPermisValid(idClient)) {
                    System.out.println("❌ Le client ne possède pas de permis.");
                    return;
                }

                if(evenementASuivre == null){
                    System.out.println("❌ Événement non trouvé");
                    return;
                }

                if (voiture == null) {
                    voiture = clientService.getVoitureById(1); // 🔄 En secours si elle n'existait pas côté admin
                }

                if (voiture == null) {
                    System.out.println("❌ Voiture introuvable.");
                    return;
                }

                boolean reservé = clientService.reserverEvenement(1, evenementASuivre, voiture, evenementASuivre.getDateEvenement());

                if (reservé) {
                    System.out.println("✅ Réservation effectuée pour l'événement " + evenementASuivre.getNomEvenement() + " avec la voiture " + voiture.getModele());
                } else {
                    System.out.println("❌ La réservation a échoué.");
                }
            } catch (ClientService.VoitureIndisponibleException e) {
                System.out.println("❌ Échec de réservation : " + e.getMessage());
            }
        }

        LoginResult logoutClient = login.logout();
        System.out.println(logoutClient.message);
        System.out.println("\n________________________________________ ");

        //Nouvelle connexion de l'admin
        System.out.println("🔐 Connexion de l'admin pour débuter une session...");
        LoginResult adminLogin = login.loginAdmin("admin@tracktoys.com", "admin123");
        System.out.println(adminLogin.message);

        if (adminLogin.success) {
            SessionAdmin session = new SessionAdmin();
            session.debutSession("Spa en Juin", 1); // Tu appelles ici ta méthode spéciale pour démarrer une session admin
            session.demarrerRoulage();
            Client client = clientService.getClientById(1);
            Simulation simulation = new Simulation(
                    client.getIdClient(),
                    session.getVoiturePriseEnCharge().getIdVoiture(),
                    session.getEvenementActif().getIdCircuit(),
                    session.getAdminConnecte().getIdAdmin(),
                    session.getChronoStart(),
                    session.getChronoEnd(),
                    LocalDate.now(),   // ou session.getDate() si tu l’as
                    session.getNombreTours()  // à ajouter dans SessionAdmin si nécessaire
            );
            SimulationRoulage simulationRoulage = new SimulationRoulage();
            simulationRoulage.enregistrerSimulation(simulation);

        }
    }
}



