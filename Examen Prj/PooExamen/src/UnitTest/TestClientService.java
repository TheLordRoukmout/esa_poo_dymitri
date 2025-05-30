package UnitTest;

import main.obj.Evenement;
import main.obj.Voitures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestClientService {

    @Test
    void testAllEvent() {
        ClientService service = new ClientService();
        List<Evenement> resultats = service.getEvenementsDisponibles();
        assertFalse(resultats.isEmpty(), "La liste des événements ne devrait pas être vide");
    }

    @Test
    void testAucunEvenement() {
        ClientService service = new ClientService();

        // Assurez-vous que la base de données est vide pour ce test
        // Cela pourrait nécessiter un nettoyage préalable ou l'utilisation d'une base de données de test vide

        assertDoesNotThrow(() -> service.avertissementSiAucunEvenement());
    }


    private ClientService clientService;

    @BeforeEach
    public void setup() {
        clientService = new ClientService();
        // Ici tu pourrais initialiser une base test ou mocker la connexion
    }

    @Test
    public void testAnnulerReservation() {
        int idClient = 1;
        String nomEvent = "Test Event";

        boolean annulé = clientService.annulerReservation(idClient, nomEvent);
        assertTrue(annulé, "L'annulation devrait réussir");
    }


    @Test
    public void testReserverEvenement_success() throws ClientService.VoitureIndisponibleException {
        int idClient = 1;
        Evenement evenement = new Evenement("Test Event", "2025-06-15", "Description", 1);

        // Récupérer une voiture existante avec un id valide (ex: 1)
        Voitures voitureExistante = clientService.getVoitureById(1);
        assertNotNull(voitureExistante, "La voiture doit exister en base pour le test");

        // Appeler la méthode de réservation avec la vraie voiture
        boolean result = clientService.reserverEvenement(idClient, evenement, voitureExistante, evenement.getDateEvenement());

        assertTrue(result, "La réservation devrait réussir");
    }

    @Test
    public void testReserverEvenement_voitureIndisponible() {
        int idClient = 1;
        Evenement evenement = new Evenement("Test Event", "2025-06-15", "Description", 1);

        Voitures voiture = clientService.getVoitureById(1);
        assertNotNull(voiture, "La voiture doit exister");

        // IMPORTANT : avant de lancer la réservation, il faut simuler que la voiture est déjà réservée ce jour-là
        // Cette simulation doit être faite en base de données avant ce test (insert réservation active pour idVoiture=1 et date=2025-06-15)
        // Sinon ce test ne fonctionnera pas correctement.

        // On teste que l'exception est bien levée si la voiture est indisponible
        assertThrows(ClientService.VoitureIndisponibleException.class, () -> {
            clientService.reserverEvenement(idClient, evenement, voiture, evenement.getDateEvenement());
        });
    }

    @Test
    void testReservationUniqueParClient() throws Exception {
        ClientService clientService = new ClientService();

        int idClient = 1;

        // On crée un événement fictif pour le test
        Evenement event = new Evenement("TestUniqueEvent", "2025-08-01", "Test unitaire réservation unique", 1);
        Voitures voiture = clientService.getVoitureById(1); // suppose que la voiture existe

        assertNotNull(voiture, "La voiture doit exister pour faire le test");

        // --- Étape 1 : s’assurer qu’il n’y a pas encore de réservation
        boolean dejaReserveAvant = invokeDejaReserve(clientService, idClient, event.getNomEvenement());
        assertFalse(dejaReserveAvant, "Le client ne doit pas avoir encore réservé cet événement");

        // --- Étape 2 : faire la réservation
        boolean reserveOK = clientService.reserverEvenement(idClient, event, voiture, event.getDateEvenement());
        assertTrue(reserveOK, "La réservation doit réussir");

        // --- Étape 3 : vérifier que la méthode détecte la réservation
        boolean dejaReserveApres = invokeDejaReserve(clientService, idClient, event.getNomEvenement());
        assertTrue(dejaReserveApres, "Le client doit maintenant être détecté comme ayant réservé");

        // --- Cleanup : annuler la réservation
        boolean annule = clientService.annulerReservation(idClient, event.getNomEvenement());
        assertTrue(annule, "La réservation doit être annulée pour nettoyage");
    }

    // Utilitaire pour appeler une méthode privée (par réflexion)
    private boolean invokeDejaReserve(ClientService service, int idClient, String nomEvent) throws Exception {
        var method = ClientService.class.getDeclaredMethod("dejaReserve", int.class, String.class);
        method.setAccessible(true);
        return (boolean) method.invoke(service, idClient, nomEvent);
    }

}
