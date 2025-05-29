package UnitTest;

import main.obj.Evenement;
import org.junit.jupiter.api.Test;
import services.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestClientService {

    @Test
    void testRecuperationEvenements() {
        ClientService service = new ClientService();
        List<Evenement> resultats = service.getEvenementsDisponibles();

        // Juste vérifier qu'on obtient bien une liste
        assertNotNull(!resultats.isEmpty());
    }

    @Test
    void AucunEvenement_() {
        ClientService service = new ClientService();

        // On appelle simplement la méthode pour vérifier qu'elle ne crash pas
        // (On ne peut pas facilement vérifier le message sans mocks)
        assertDoesNotThrow(() -> service.avertissementSiAucunEvenement());
    }

}
