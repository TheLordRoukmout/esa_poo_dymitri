package UnitTest;

import org.junit.jupiter.api.Test;
import main.LoginService;
import static org.junit.jupiter.api.Assertions.*;

public class TestClassProg {
    @Test
    void LoginSucessFull(){
        LoginService login = new LoginService();
        boolean result = login.LoginSystem("admin@tracktoys.com", "admin123");

        assertTrue(result, "Connexion devrait être réussi");
    }
}
