package main;

import UnitTest.TestAdminService;
import org.checkerframework.checker.guieffect.qual.UI;

public class Main {
    public static void main(String[] args) {
        LoginService login = new LoginService();

        // Connexion avec l'admin
        LoginResult adminResult = login.loginAdmin("admin@tracktoys.com", "admin123");
        System.out.println(adminResult.message);
        if(adminResult.success){
            Circuit circuit = new Circuit("Spa Francorchamps", "Liège spa", 80.00);
            AdminService adminAction = new AdminService();
            if(adminAction.addCircuit(circuit)){
                System.out.println("Circuit " + circuit.getNom() + " ajouté avec succès !");
            }else {
                System.out.println("Ce circuit existe déja ou une erreur est survenue.");
            }
            Voitures voiture = new Voitures("Porsche 911 GT3", 510, 250.0, true);
            if(adminAction.addVoiture(voiture)){
                System.out.println("Voiture: "+ voiture.getModele() + " ajouté !");
            }else {
                System.out.println("Echec de l'ajout");
            }
            
            adminAction.supprimerVoiture(1);
        }

        // Connexion avec le client
        LoginService loginClient = new LoginService();
        LoginResult result = login.loginClient("client@tracktoys.com", "1234");
        System.out.println(result.message);
    }
}

