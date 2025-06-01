package UnitTest;

import main.obj.Evenement;
import main.obj.Voitures;
import org.junit.jupiter.api.*;
import services.ClientService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestClientService {

    private ClientService clientService;
    private int idClient = 1; // 🔧 Doit exister dans la base

    @BeforeEach
    public void setup() {
        clientService = new ClientService();
    }

    @Test
    void testEvenementsDisponibles() {
        List<Evenement> evenements = clientService.getEvenementsDisponibles();
        assertNotNull(evenements, "La liste ne doit pas être nulle");
        assertFalse(evenements.isEmpty(), "Il doit y avoir au moins un événement");
    }

    @Test
    void testReserverEtAnnulerEvenement() throws Exception {
        String nomEvent = "TestEvent_" + System.currentTimeMillis();
        Evenement event = new Evenement(nomEvent, "2025-06-28", "Test réservation", 1);
        Voitures voiture = clientService.getVoitureById(2);

        assertNotNull(voiture, "La voiture doit exister");
        boolean reserve = clientService.reserverEvenement(idClient, event, voiture, event.getDateEvenement());
        assertTrue(reserve, "La réservation doit réussir");

        boolean annule = clientService.annulerReservation(idClient, nomEvent);
        assertTrue(annule, "L'annulation doit réussir");
    }

    @Test
    void testReservationUniqueParClient() throws Exception {
        String nomEvent = "UniqueEvent_" + System.currentTimeMillis();
        Evenement event = new Evenement(nomEvent, "2025-08-01", "Test unique", 1);
        Voitures voiture = clientService.getVoitureById(1);

        assertNotNull(voiture, "La voiture doit être valide");

        boolean avant = invokeDejaReserve(clientService, idClient, nomEvent);
        assertFalse(avant, "Le client ne doit pas avoir réservé");

        boolean reserve = clientService.reserverEvenement(idClient, event, voiture, event.getDateEvenement());
        assertTrue(reserve, "La réservation doit réussir");

        boolean apres = invokeDejaReserve(clientService, idClient, nomEvent);
        assertTrue(apres, "Le client doit maintenant être détecté comme ayant réservé");

        boolean annule = clientService.annulerReservation(idClient, nomEvent);
        assertTrue(annule, "L'annulation doit réussir");
    }

    @Test
    void testVoitureIndisponible() throws Exception {
        String nomEvent = "ResaConflit_" + System.currentTimeMillis();
        Evenement event = new Evenement(nomEvent, "2025-07-01", "Conflit test", 1);
        Voitures voiture = clientService.getVoitureById(1);

        assertNotNull(voiture, "La voiture doit exister");

        // 1ère réservation OK
        boolean reserve = clientService.reserverEvenement(idClient, event, voiture, event.getDateEvenement());
        assertTrue(reserve, "La réservation initiale doit réussir");

        // 2e réservation → doit échouer
        assertThrows(ClientService.VoitureIndisponibleException.class, () -> {
            clientService.reserverEvenement(idClient, event, voiture, event.getDateEvenement());
        });

        // Cleanup
        clientService.annulerReservation(idClient, nomEvent);
    }

    // Utilitaire : appel méthode privée `dejaReserve`
    private boolean invokeDejaReserve(ClientService service, int idClient, String nomEvent) throws Exception {
        var method = ClientService.class.getDeclaredMethod("dejaReserve", int.class, String.class);
        method.setAccessible(true);
        return (boolean) method.invoke(service, idClient, nomEvent);
    }
}
