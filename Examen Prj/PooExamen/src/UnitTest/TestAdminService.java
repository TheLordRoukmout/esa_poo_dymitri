package UnitTest;

import main.AdminService;
import main.Circuit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAdminService {

    @Test
    void testAddCircuitSuccessfully() {
        AdminService adminAction = new AdminService();
        String nomUnique = "Test Temporaire" + System.currentTimeMillis(); // nom toujours unique
        Circuit testCircuit = new Circuit(nomUnique, "Rue Bidon 42", 12.34);

        boolean result = adminAction.addCircuit(testCircuit);
        assertTrue(result, "Le circuit devrait être ajouté");

        adminAction.supprimerCircuitParNom(nomUnique); // nettoyage
    }

    @Test
    void CircuitDouble() {
        AdminService adminAction = new AdminService();

        adminAction.supprimerCircuitParNom("Test Circuit");

        Circuit testCircuit = new Circuit("Test Circuit", "123 Rue de test, Namur", 42.50);

        // Premier ajout → devrait réussir
        boolean premierAjout = adminAction.addCircuit(testCircuit);
        assertTrue(premierAjout, "Le premier ajout du circuit doit réussir.");

        // Deuxième ajout → même nom, devrait échouer
        boolean secondAjout = adminAction.addCircuit(testCircuit);
        assertFalse(secondAjout, "L'ajout devrait échouer car le circuit existe déjà.");
    }

    @Test
    void testAjoutSansAdresse() {
        AdminService admin = new AdminService();
        Circuit circuit = new Circuit("Circuit Sans Adresse", "", 50);
        assertFalse(admin.addCircuit(circuit), "Le circuit ne doit pas être ajouté sans adresse.");
    }

    @Test
    void testAjoutAvecTarifInvalide() {
        AdminService admin = new AdminService();
        Circuit circuit = new Circuit("Circuit Gratuit", "Rue gratuite", 0);
        assertFalse(admin.addCircuit(circuit), "Le circuit ne doit pas être ajouté avec un tarif nul.");
    }

}
