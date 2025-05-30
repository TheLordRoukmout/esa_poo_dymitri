package sessionRacing;

import main.ConnexionData;
import main.LoginResult;
import main.obj.Admin;
import main.obj.Circuit;
import main.obj.Evenement;
import main.obj.Voitures;
import services.AdminService;
import services.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionAdmin {

    private final Admin adminConnecte;
    private Evenement evenementActif;
    private Voitures voiturePriseEnCharge;

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

}
