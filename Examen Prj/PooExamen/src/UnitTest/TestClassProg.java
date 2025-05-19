package UnitTest;

import main.LoginService;
import main.Ui;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestClassProg {

    @Test
    void testSuccessfulAdminLogin() {
        // 1. SIMULE UNIQUEMENT LE LOGIN
        System.setIn(new ByteArrayInputStream("admin@tracktoys.com\nadmin123\n".getBytes()));

        // 2. TEST DIRECT SANS MENU
        Ui ui = new Ui();
        boolean[] adminFlag = new boolean[1];
        boolean result = LoginService.LoginSystem("admin@tracktoys.com", "admin123", adminFlag);
        ui.isAdmin = adminFlag[0]; // Accès direct pour le test

        // 3. ASSERTIONS
        assertTrue(result);
        assertTrue(ui.isAdmin);
    }

    @Test
    void testFailedLogin() {
        System.setIn(new ByteArrayInputStream("wrong@mail.com\nwrongpass\n".getBytes()));

        Ui ui = new Ui();
        boolean[] adminFlag = new boolean[1];
        boolean result = LoginService.LoginSystem("wrong@mail.com", "wrongpass", adminFlag);

        assertFalse(result);
        assertFalse(adminFlag[0]);
    }
}