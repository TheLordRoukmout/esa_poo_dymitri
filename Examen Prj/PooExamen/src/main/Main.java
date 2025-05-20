package main;

import org.checkerframework.checker.guieffect.qual.UI;

public class Main {
    public static void main(String[] args) {
        LoginService login = new LoginService();

        // Connexion avec l'admin
        boolean adminOK = login.loginAdmin("admin@tracktoys.com", "admin123");
        if (adminOK) {
            System.out.println("Connexion admin réussie !");
        } else {
            System.out.println("Connexion admin échouée.");
        }

        // Connexion avec le client
        boolean clientOK = login.loginClient("client@tracktoys.com", "1234");
        if (clientOK) {
            System.out.println("Connexion client réussie !");
        } else {
            System.out.println("Connexion client échouée.");
        }
    }
}

