package UnitTest;

import org.junit.jupiter.api.BeforeEach;
import services.AdminService;
import main.obj.Circuit;
import main.obj.Voitures;
import org.junit.jupiter.api.Test;
import sessionRacing.SessionAdmin;

import static org.junit.jupiter.api.Assertions.*;


public class TestSession {

    private SessionAdmin sessionAdmin;

    @BeforeEach
    void setUp() {
        sessionAdmin = new SessionAdmin();
    }

    @Test
    void testDebutSessionAvecEvenementEtVoitureValides() {
        // Ces valeurs doivent exister dans ta base de données de test
        String nomEvenement = "Spa en Juin";
        int idVoiture = 1;

        // Appelle la méthode à tester (les asserts sont limités car elle utilise des println)
        assertDoesNotThrow(() -> sessionAdmin.debutSession(nomEvenement, idVoiture));

        // Tu pourrais vérifier que l'événement et la voiture sont bien enregistrés si tu exposes les getters
        // Exemple (à implémenter) :
        // assertNotNull(sessionAdmin.getEvenementActif());
        // assertEquals(nomEvenement, sessionAdmin.getEvenementActif().getNomEvenement());
    }

    @Test
    void testDebutSessionAvecEvenementInvalide() {
        String nomEvenement = "EvenementInexistant";
        int idVoiture = 1; // Supposée valide

        assertDoesNotThrow(() -> sessionAdmin.debutSession(nomEvenement, idVoiture));
        // Ici tu pourrais aussi rediriger System.out pour tester les messages si nécessaire
    }

    @Test
    void testDebutSessionAvecVoitureInvalide() {
        String nomEvenement = "Spa en Juin";
        int idVoiture = 9999; // Supposée inexistante

        assertDoesNotThrow(() -> sessionAdmin.debutSession(nomEvenement, idVoiture));
    }

}
