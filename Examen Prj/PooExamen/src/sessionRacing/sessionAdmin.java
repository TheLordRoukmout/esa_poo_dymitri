package sessionRacing;

import main.LoginResult;
import main.obj.Circuit;
import main.obj.Voitures;
import services.AdminService;
import services.LoginService;

public class sessionAdmin {

    private final LoginService loginService;
    private final AdminService adminService;

    public sessionAdmin() {
        this.loginService = new LoginService();
        this.adminService = new AdminService(loginService);
    }

    public void debutSession() {
        System.out.println("Connexion en tant qu'admin...");
        LoginResult result = loginService.loginAdmin("admin@tracktoys.com", "admin123");

        if (result.success) {
            System.out.println("✅ Session admin démarrée !");
            System.out.println(result.message);

            // Exemple d'actions possibles ici
            Circuit circuit = new Circuit("Zolder", "Belgique", 60.0, 4);
            if (adminService.addCircuit(circuit)) {
                System.out.println("Circuit ajouté !");
            }

            Voitures voiture = new Voitures("Audi R8", 520, 260.0, true);
            if (adminService.addVoiture(voiture)) {
                System.out.println("Voiture ajoutée !");
            }

        } else {
            System.out.println("❌ Échec de la connexion : " + result.message);
        }
    }
}
