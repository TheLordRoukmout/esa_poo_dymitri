package main;

import java.util.Scanner;

public class Ui {
    // Suppression des 'static' sauf pour les constantes
    private final Scanner scanner = new Scanner(System.in);
    private static final int MAX_ATTEMPTS = 3;
    public boolean isAdmin;

    public void startLoginSystem() {  // Plus static
        System.out.println("=== LOGIN TRACKTOYS ===");

        int attempts = 0;
        boolean authenticated = false;
        boolean[] adminFlag = new boolean[1];

        while (attempts < MAX_ATTEMPTS && !authenticated) {
            System.out.print("Email: ");
            String mail = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            authenticated = LoginService.LoginSystem(mail, password, adminFlag);
            this.isAdmin = adminFlag[0];  // Maintenant valide

            if (authenticated) {
                System.out.println("\nConnection successful!");
                showMainMenu();  // Renommé en camelCase
            } else {
                attempts++;
                System.out.printf("\nFailed to connect, attempts remaining: %d%n",
                        (MAX_ATTEMPTS - attempts));
            }
        }

        if (!authenticated) {
            System.out.println("Too many failed attempts. Please restart the app.");
        }
    }

    private void showMainMenu() {  // Plus static
        System.out.println("\n=== MAIN MENU TRACKTOYS ===");

        if (this.isAdmin) {
            System.out.println("=== ADMIN OPTIONS ===");
            System.out.println("1. Manage users");
            System.out.println("2. View statistics");
        } else {
            System.out.println("=== USER OPTIONS ===");
            System.out.println("1. My orders");
            System.out.println("2. My profile");
        }

        System.out.println("3. Exit");
        handleMenuChoice();
    }

    private void handleMenuChoice() {
        int choice = Integer.parseInt(scanner.nextLine());
        // Implémentez la logique des choix ici
    }
}