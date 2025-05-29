package UnitTest;

import main.LoginResult;
import services.LoginService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLoginService {
    @Test
    void testClientConnexionValide() {
        LoginService login = new LoginService();
        LoginResult result = login.loginClient("client@tracktoys.com", "1234");

        System.out.println("Résultat : " + result.message);
        assertTrue(result.success, "Le client devrait pouvoir se connecter avec les bons identifiants.");
    }

    @Test
    void testClientMauvaisMotDePasse() {
        LoginService login = new LoginService();
        LoginResult result = login.loginClient("client@tracktoys.com", "motdepassefaux");

        System.out.println("Résultat : " + result.message);
        assertFalse(result.success, "La connexion devrait échouer avec un mot de passe incorrect.");
        assertTrue(result.message.toLowerCase().contains("mot de passe") || result.message.toLowerCase().contains("fail"));
    }

    @Test
    void testClientEmailInexistant() {
        LoginService login = new LoginService();
        LoginResult result = login.loginClient("inexistant@tracktoys.com", "1234");

        System.out.println("Résultat : " + result.message);
        assertFalse(result.success, "La connexion devrait échouer avec un email inexistant.");
        assertTrue(result.message.toLowerCase().contains("email") || result.message.toLowerCase().contains("fail"));
    }

    @Test
    void testAdminConnexionValide() {
        LoginService login = new LoginService();
        LoginResult result = login.loginAdmin("admin@tracktoys.com", "admin123");

        System.out.println("Résultat : " + result.message);
        assertTrue(result.success, "L'admin devrait pouvoir se connecter avec les bons identifiants.");
        assertTrue(result.message.toLowerCase().contains("admin"));
    }

    @Test
    void testAdminMotDePasseIncorrect() {
        LoginService login = new LoginService();
        LoginResult result = login.loginAdmin("admin@tracktoys.com", "mauvaismdp");

        System.out.println("Résultat : " + result.message);
        assertFalse(result.success, "Connexion admin avec mauvais mot de passe devrait échouer.");
        assertTrue(result.message.toLowerCase().contains("mot de passe"));
    }

    @Test
    void testAdminEmailInexistant() {
        LoginService login = new LoginService();
        LoginResult result = login.loginAdmin("inexistant@tracktoys.com", "1234");

        System.out.println("Résultat : " + result.message);
        assertFalse(result.success, "Connexion avec email inexistant devrait échouer.");
        assertTrue(result.message.toLowerCase().contains("aucun") || result.message.toLowerCase().contains("introuvable"));
    }
}
