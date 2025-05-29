package UnitTest;

import services.AdminService;
import main.obj.Circuit;
import main.obj.Voitures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAdminService {

    @Test
    void testAddCircuitSuccessfully() {
        AdminService adminAction = new AdminService();
        String nomUnique = "Test Temporaire" + System.currentTimeMillis(); // nom toujours unique
        Circuit testCircuit = new Circuit(nomUnique, "Rue Bidon 42", 12.34, 5);

        boolean result = adminAction.addCircuit(testCircuit);
        assertTrue(result, "Le circuit devrait être ajouté");

        adminAction.supprimerCircuitParNom(nomUnique); // nettoyage
    }

    @Test
    void CircuitDouble() {
        AdminService adminAction = new AdminService();

        adminAction.supprimerCircuitParNom("Test Circuit");

        Circuit testCircuit = new Circuit("Test Circuit", "123 Rue de test, Namur", 42.50, 5);

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
        Circuit circuit = new Circuit("Circuit Sans Adresse", "", 50, 5);
        assertFalse(admin.addCircuit(circuit), "Le circuit ne doit pas être ajouté sans adresse.");
    }

    @Test
    void testAjoutAvecTarifInvalide() {
        AdminService admin = new AdminService();
        Circuit circuit = new Circuit("Circuit Gratuit", "Rue gratuite", 0, 5);
        assertFalse(admin.addCircuit(circuit), "Le circuit ne doit pas être ajouté avec un tarif nul.");
    }

    @Test
    void testSuppressionCircuit(){
        AdminService admin = new AdminService();
        var NomCircuit = "Test Circuit";
        boolean result = admin.supprimerCircuitParNom(NomCircuit);
        assertTrue(result, "Le circuit devrait être supprimé");
    }

    @Test
    void addNewVoiture(){
        AdminService admin = new AdminService();
        Voitures voitureTest = new Voitures("Test x001", 90, 10.00, true);
        boolean result = admin.addVoiture(voitureTest);
        assertTrue(result, "La voiture devrait être ajoutée.");
    }

    @Test
    void voitureAlreadyExiste(){
        AdminService admin = new AdminService();
        Voitures voituresExist = new Voitures("Test x001", 90, 10.00, true);
        boolean result = admin.addVoiture(voituresExist);
        assertTrue(result, "Attention vous avez une voiture qui existe déja.");
    }

    @Test
    void SuppressionVoiture(){
        AdminService admin = new AdminService();
        Voitures voitureToDelete = new Voitures("Test x001", 90, 10.00, true);
        boolean result = admin.supprimerVoiture("Test x001");
        assertTrue(result, "Voiture " + voitureToDelete.getModele() + " supprimé !");
    }

    @Test
    void testAjoutEtSuppressionFicheMecha() {
        AdminService admin = new AdminService();

        // 1. Ajouter une voiture temporaire
        Voitures testVoiture = new Voitures("TempCar", 350, 120.0, true);
        boolean voitureAjoutee = admin.addVoiture(testVoiture);
        assertTrue(voitureAjoutee, "La voiture devrait être ajoutée.");

        // 2. Récupérer son ID
        int idVoiture = admin.getLastVoitureIdByModele(testVoiture.getModele());
        assertTrue(idVoiture > 0, "L'ID de la voiture devrait être supérieur à 0.");

        // 3. Ajouter une fiche InfoMecha
        boolean ficheAjoutee = admin.addInfoMechaData(idVoiture, 60.0, 8.0, 0.0, "Neuve");
        assertTrue(ficheAjoutee, "La fiche InfoMecha devrait être ajoutée.");

        // 5. Supprimer la voiture
        boolean voitureSupprimee = admin.supprimerVoiture("TempCar");
        assertTrue(voitureSupprimee, "La voiture devrait être supprimée.");
    }

}
