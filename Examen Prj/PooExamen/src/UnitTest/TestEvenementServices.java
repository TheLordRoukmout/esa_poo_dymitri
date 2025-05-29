package UnitTest;

import main.obj.Circuit;
import main.obj.Evenement;
import org.junit.jupiter.api.Test;
import services.AdminService;
import services.EvenementService;

import static org.junit.jupiter.api.Assertions.*;

public class TestEvenementServices {

    @Test
    public void testAddEvenement() {
        Circuit circuitTest = new Circuit("Circuit Test", "Adresse test", 80.00, 9.45);
        AdminService adminService = new AdminService();
        adminService.addCircuit(circuitTest);
        EvenementService addEvent = new EvenementService();
        Evenement event = new Evenement("Evenement", "2025-08-01", "Journée circruit test", 1);
        boolean created = addEvent.creatEvent(event);
        assertTrue(created,  "L'évènement devrait être créer.");
    }

    @Test
    public void testRemoveEvenement() {
        AdminService adminService = new AdminService();
        EvenementService removeEvent = new EvenementService();
        String nameEvent = "Evenement";
        boolean deleteEvent = removeEvent.deleEvent(nameEvent);
        assertTrue(deleteEvent, "L'évenement devrait être supprimé.");
        adminService.supprimerCircuitParNom("Circuit Test");
    }

    @Test
    void testCreateEvenementAlreadyExists() {
        Circuit circuitTest = new Circuit("Circuit Test", "Adresse test", 80.00, 9.45);
        AdminService adminService = new AdminService();
        adminService.addCircuit(circuitTest);
        EvenementService serviceEvent = new EvenementService();
        Evenement evenement1 = new Evenement("TestDouble", "2025-05-12", "Test pour le double envent", 1);
        Evenement evenement2 = new Evenement("TestDouble", "2025-05-12", "Test pour le double envent", 1);

        assertTrue(serviceEvent.creatEvent(evenement1), "Le premier event devrait être bon");
        assertFalse(serviceEvent.creatEvent(evenement2), "Le deuxième ne pas être ajouté car il existe déja");

        serviceEvent.deleEvent(evenement1.getNomEvenement());

    }

    @Test
    void testSuppressionEvenementInexistant() {
        EvenementService service = new EvenementService();
        boolean result = service.deleEvent("EvenementInexistant");
        assertFalse(result, "❌ Rien ne doit être supprimé");
    }



}
