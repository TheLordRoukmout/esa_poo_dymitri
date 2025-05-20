package UnitTest;

import main.LoginService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLoginService {
    @Test
    void testLoginAdminValid(){
        LoginService login = new LoginService();
        boolean result = login.loginAdmin("admin@tracktoys.com", "admin123");
        assertTrue(result, "La connexion devrait être réussi pour admin");
    }

    @Test
    void testLoginAdminInvalid(){
        LoginService login = new LoginService();
        boolean result = login.loginAdmin("admin@tracktoy.com", "admin123");
        assertFalse(result, "La connexion devrait avoir échoué pour admin");
    }

    @Test
    void testLoginClient(){
        LoginService login = new LoginService();
        boolean result = login.loginClient("client@tracktoys.com", "1234");
        assertTrue(result, "La connexion devrait être réussi pour client");
    }

    @Test
    void testLoginClientInvalid(){
        LoginService login = new LoginService();
        boolean result = login.loginClient("client@tracktoys.com", "234");
        assertFalse(result, "La connexion devrait avoir échoué pour client");
    }

}
