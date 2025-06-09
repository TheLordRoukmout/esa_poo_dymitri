package UnitTest;

import main.ConnexionData;
import main.obj.Evenement;
import main.obj.Voitures;
import org.junit.jupiter.api.*;
import services.ClientService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestClientService {

    private ClientService clientService;
    private int idClient = 999;

    @BeforeEach
    public void setup() {
        clientService = new ClientService();
    }

    @BeforeEach
    void insertClientDeTest() {
        try (var conn = ConnexionData.getConnection();
             var stmt = conn.prepareStatement("""
            INSERT OR IGNORE INTO Clients (id_client, nom_client, prenom_client, age_client, numPermis_client, mail_client, password_client, id_role)
            VALUES (999, 'Test', 'Client', 30, 'ABC123456', 'test@unittest.com', 'password', 2)
        """)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion du client de test : " + e.getMessage());
        }
    }

    @BeforeEach
    void insertVoitureDeTest() {
        try (var conn = ConnexionData.getConnection();
             var stmt = conn.prepareStatement("""
            INSERT OR IGNORE INTO Voitures (id_voiture, model_voiture, puissance_voiture, priceLocation_voiture, disponibilite_voiture)
            VALUES (999, 'TestCar', 300, 150.0, 1)
        """)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion de la voiture de test : " + e.getMessage());
        }
    }

    @BeforeEach
    void insertEvenementDeTest() {
        try (var conn = ConnexionData.getConnection();
             var stmt1 = conn.prepareStatement("""
             INSERT OR IGNORE INTO Circuits (id_circuit, nom_circuit, adresse_circuit, distance_circuit, tarif_circuit)
             VALUES (999, 'Circuit Test', 'Testville', 100.0, 2)
         """);
             var stmt2 = conn.prepareStatement("""
             INSERT OR IGNORE INTO Evenements (nom_evenement, date_evenement, description_evenement, id_circuit)
             VALUES ('TestEvent', '2025-07-30', 'Événement fictif pour test', 999)
         """)) {
            stmt1.executeUpdate();
            stmt2.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion de l'événement de test : " + e.getMessage());
        }
    }

    @BeforeEach
    void insertClientVoitureCircuitPourReservation() {
        try (var conn = ConnexionData.getConnection()) {

            // Insertion du client de test (id 999)
            var clientStmt = conn.prepareStatement("""
            INSERT OR IGNORE INTO Clients (id_client, nom_client, prenom_client, age_client, numPermis_client, mail_client, password_client, id_role)
            VALUES (999, 'ClientTest', 'Unit', 25, 'PERMIS123', 'unit@test.com', 'pass', 2)
        """);
            clientStmt.executeUpdate();

            // Insertion du circuit de test (id 1, nécessaire pour créer l'événement)
            var circuitStmt = conn.prepareStatement("""
            INSERT OR IGNORE INTO Circuits (id_circuit, nom_circuit, adresse_circuit, distance_circuit, tarif_circuit)
            VALUES (1, 'CircuitTest', 'Testville', 80.0, 3)
        """);
            circuitStmt.executeUpdate();

            // Insertion de la voiture de test (id 999)
            var voitureStmt = conn.prepareStatement("""
            INSERT OR IGNORE INTO Voitures (id_voiture, model_voiture, puissance_voiture, priceLocation_voiture, disponibilite_voiture)
            VALUES (999, 'VoitureTest', 300, 200.0, 1)
        """);
            voitureStmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Erreur insertion données de réservation : " + e.getMessage());
        }
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
        Voitures voiture = clientService.getVoitureById(999);

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
        Voitures voiture = clientService.getVoitureById(999);

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
        Voitures voiture = clientService.getVoitureById(999);

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

    @AfterEach
    void cleanup() {
        try (var conn = ConnexionData.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Reservation WHERE id_client = 999");
            stmt.executeUpdate("DELETE FROM Clients WHERE id_client = 999");
            stmt.executeUpdate("DELETE FROM Voitures WHERE id_voiture = 999");
            stmt.executeUpdate("DELETE FROM Evenements WHERE nom_evenement = 'TestEvent'");
            stmt.executeUpdate("DELETE FROM Circuits WHERE id_circuit = 999");
        } catch (Exception e) {
            System.err.println("Erreur lors du nettoyage des données de test : " + e.getMessage());
        }
    }

    @AfterEach
    void cleanupEvenementDeTest() {
        try (var conn = ConnexionData.getConnection();
             var stmt1 = conn.prepareStatement("DELETE FROM Evenements WHERE nom_evenement = 'TestEvent'");
             var stmt2 = conn.prepareStatement("DELETE FROM Circuits WHERE id_circuit = 999")) {
            stmt1.executeUpdate();
            stmt2.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erreur lors du nettoyage : " + e.getMessage());
        }
    }

}
