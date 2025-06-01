package sessionRacing;

import main.ConnexionData;
import main.LoginResult;
import main.obj.*;
import services.AdminService;
import services.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class SessionAdmin {

    private final Admin adminConnecte;
    private Evenement evenementActif;
    private Voitures voiturePriseEnCharge;
    private Simulation simulation;

    private LocalTime chronoStart;
    private LocalTime chronoEnd;

    private final LoginService loginService;
    private final AdminService adminService;

    public SessionAdmin() {
        this.loginService = new LoginService();
        this.adminService = new AdminService(loginService);

        LoginResult result = loginService.loginAdmin("admin@tracktoys.com", "admin123");
        if (!result.success || result.admin == null) {
            throw new IllegalStateException("Connexion échouée ou admin introuvable !");
        }
        this.adminConnecte = result.admin;
    }

    public void debutSession(String nomEvenement, int idVoiture) {
        System.out.println("Connexion en tant qu'admin...");
        LoginResult result = loginService.loginAdmin("admin@tracktoys.com", "admin123");

        if (!result.success) {
            System.out.println("❌ Échec de la connexion : " + result.message);
            return;
        }

        System.out.println("✅ Session admin démarrée !");
        System.out.println(result.message);

        // Récupération de l'événement existant
        Evenement evenement = adminService.getEvenementByNom(nomEvenement);
        if (evenement == null) {
            System.out.println("❌ Événement non trouvé.");
            return;
        }

        // Récupération de la voiture réservée par le client
        Voitures voiture = adminService.getVoitureById(idVoiture);
        if (voiture == null) {
            System.out.println("❌ Voiture introuvable.");
            return;
        }

        // Sauvegarde pour la suite (début roulage)
        this.evenementActif = evenement;
        this.voiturePriseEnCharge = voiture;

        String clientNomParticipant = adminService.getNomPrenomClientPourReservation(evenement.getNomEvenement(), voiture.getIdVoiture());

        System.out.println("✅ Début de la session pour l'événement : " + evenement.getNomEvenement());
        System.out.println("🔧 Voiture prise en charge : " + voiture.getModele());
        System.out.println("Client: " + clientNomParticipant);
    }

    public void demarrerRoulage() {
        this.chronoStart = LocalTime.now();
        if (evenementActif == null || voiturePriseEnCharge == null) {
            System.out.println("❌ Impossible de démarrer : événement ou voiture non défini.");
            return;
        }

        // Exemple d'affichage du nom du participant
        String nomParticipant = adminService.getNomPrenomClientPourReservation(
                evenementActif.getNomEvenement(),
                voiturePriseEnCharge.getIdVoiture()
        );
        System.out.println("🚗 Participant : " + nomParticipant + " démarre le roulage !");
        System.out.println("🏁 Circuit : " + evenementActif.getNomEvenement());

        // Simulation d’un roulage : 100% en 20 étapes (toutes les secondes)
        final int dureeTotalSecondes = 20;
        for (int i = 0; i <= dureeTotalSecondes; i++) {
            int avancement = (i * 100) / dureeTotalSecondes;
            String chrono = String.format("%02d:%02d", i / 60, i % 60);
            System.out.println("⏱️ Chrono : " + chrono + " | Avancement : " + avancement + "%");

            try {
                Thread.sleep(1000); // pause 1 seconde pour simuler le temps réel
            } catch (InterruptedException e) {
                System.out.println("⛔ Simulation interrompue.");
                return;
            }
        }
        this.chronoEnd = LocalTime.now();

        System.out.println("🏁 Fin du roulage !");
    }

    public Evenement getEvenementActif() {
        return this.evenementActif;
    }

    public Voitures getVoiturePriseEnCharge() {
        return this.voiturePriseEnCharge;
    }

    public Admin getAdminConnecte() {
        return this.adminConnecte;
    }

    public LocalTime getChronoStart() {
        return this.chronoStart;
    }

    public LocalTime getChronoEnd() {
        return this.chronoEnd;
    }

    private int nombreTours = 5; // ou autre valeur par défaut

    public int getNombreTours() {
        return this.nombreTours;
    }

    public void setNombreTours(int nombreTours) {
        this.nombreTours = nombreTours;
    }


}
