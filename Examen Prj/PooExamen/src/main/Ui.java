package main;

import java.util.Scanner;

public class Ui {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LoginService loginService = new LoginService();
    private static final int MAX_ATTEMPS = 3;

    public static void startLoginSystem(){
        System.out.println("=== LOGIN TRACKTOYS ===");

        int attempts = 0;
        boolean authenticated = false;

        while (attempts < MAX_ATTEMPS && !authenticated){
            System.out.print("Email: ");
            String mail = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            authenticated = LoginService.LoginSystem(mail, password);

            if(authenticated){
                System.out.println("\n Connection sucessfull !");
                MainMenu();
            }else{
                attempts ++;
                System.out.printf("\n Failed to connect, tentavie remaining : " + (MAX_ATTEMPS - attempts));
            }
        }

        if(!authenticated){
            System.out.println("Too much temptation to log, restart the app.");
        }
        scanner.close();

    }

    private static void MainMenu(){
        System.out.println("\n=== MAin Menu TRACKTOYS ===");
        System.out.println("1. option 1");
        System.out.println("2. option 2");
        System.out.println("3. Exit");

    }

}
