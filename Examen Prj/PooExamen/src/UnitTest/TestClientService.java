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


}
