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

/**
 * Classe responsable de la gestion d'une session d'un administrateur pour superviser un événement et un roulage.
 */
public class SessionAdmin {

    private final Admin adminConnecte;
    private Evenement evenementActif;
    private Voitures voiturePriseEnCharge;
    private Simulation simulation;

    private LocalTime chronoStart;
    private LocalTime chronoEnd;

    private final LoginService loginService;
    private final AdminService adminService;

    private int nombreTours = 5;

    /**
     * Constructeur : initialise les services et connecte automatiquement l'administrateur.
     * Lance une exception si la connexion échoue.
     */
    public SessionAdmin() {
        this.loginService = new LoginService();
        this.adminService = new AdminService(loginService);

        LoginResult result = loginService.loginAdmin("admin@tracktoys.com", "admin123");
        if (!result.success || result.admin == null) {
            throw new IllegalStateException("Connexion échouée ou admin introuvable !");
        }
        this.adminConnecte = result.admin;
    }

    /**
     * Démarre une session d’événement pour l’admin connecté avec la voiture et l’événement spécifiés.
     *
     * @param nomEvenement le nom de l'événement à charger
     * @param idVoiture    l'ID de la voiture qui participera à la session
     */
    public void debutSession(String nomEvenement, int idVoiture) {
        System.out.println("Connexion en tant qu'admin...");
        LoginResult result = loginService.loginAdmin("admin@tracktoys.com", "admin123");

        if (!result.success) {
            System.out.println("❌ Échec de la connexion : " + result.message);
            return;
        }

        System.out.println("✅ Session admin démarrée !");
        System.out.println(result.message);

        Evenement evenement = adminService.getEvenementByNom(nomEvenement);
        if (evenement == null) {
            System.out.println("❌ Événement non trouvé.");
            return;
        }

        Voitures voiture = adminService.getVoitureById(idVoiture);
        if (voiture == null) {
            System.out.println("❌ Voiture introuvable.");
            return;
        }

        this.evenementActif = evenement;
        this.voiturePriseEnCharge = voiture;

        String clientNomParticipant = adminService.getNomPrenomClientPourReservation(
                evenement.getNomEvenement(),
                voiture.getIdVoiture()
        );

        System.out.println("✅ Début de la session pour l'événement : " + evenement.getNomEvenement());
        System.out.println("🔧 Voiture prise en charge : " + voiture.getModele());
        System.out.println("Client: " + clientNomParticipant);
    }

    /**
     * Simule un roulage sur circuit avec chrono en temps réel et avancement progressif.
     * La durée est de 20 secondes avec affichage chaque seconde.
     */
    public void demarrerRoulage() {
        this.chronoStart = LocalTime.now();

        if (evenementActif == null || voiturePriseEnCharge == null) {
            System.out.println("❌ Impossible de démarrer : événement ou voiture non défini.");
            return;
        }

        String nomParticipant = adminService.getNomPrenomClientPourReservation(
                evenementActif.getNomEvenement(),
                voiturePriseEnCharge.getIdVoiture()
        );
        System.out.println("🚗 Participant : " + nomParticipant + " démarre le roulage !");
        System.out.println("🏁 Circuit : " + evenementActif.getNomEvenement());

        final int dureeTotalSecondes = 20;
        for (int i = 0; i <= dureeTotalSecondes; i++) {
            int avancement = (i * 100) / dureeTotalSecondes;
            String chrono = String.format("%02d:%02d", i / 60, i % 60);
            System.out.println("⏱️ Chrono : " + chrono + " | Avancement : " + avancement + "%");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("⛔ Simulation interrompue.");
                return;
            }
        }

        this.chronoEnd = LocalTime.now();
        System.out.println("🏁 Fin du roulage !");
    }

    /**
     * Retourne l'événement actif de la session.
     *
     * @return l'objet {@link Evenement} actif
     */
    public Evenement getEvenementActif() {
        return this.evenementActif;
    }

    /**
     * Retourne la voiture utilisée dans cette session.
     *
     * @return l'objet {@link Voitures} utilisé
     */
    public Voitures getVoiturePriseEnCharge() {
        return this.voiturePriseEnCharge;
    }

    /**
     * Retourne l'administrateur connecté à cette session.
     *
     * @return l'objet {@link Admin} connecté
     */
    public Admin getAdminConnecte() {
        return this.adminConnecte;
    }

    /**
     * Retourne l'heure de début du roulage.
     *
     * @return un objet {@link LocalTime}
     */
    public LocalTime getChronoStart() {
        return this.chronoStart;
    }

    /**
     * Retourne l'heure de fin du roulage.
     *
     * @return un objet {@link LocalTime}
     */
    public LocalTime getChronoEnd() {
        return this.chronoEnd;
    }

    /**
     * Retourne le nombre de tours effectués dans la session.
     *
     * @return nombre de tours
     */
    public int getNombreTours() {
        return this.nombreTours;
    }

    /**
     * Définit le nombre de tours à simuler pour cette session.
     *
     * @param nombreTours nombre de tours souhaité
     */
    public void setNombreTours(int nombreTours) {
        this.nombreTours = nombreTours;
    }
}
