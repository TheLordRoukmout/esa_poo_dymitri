package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe utilitaire pour gérer la connexion à la base de données SQLite
 * et initialiser la structure des tables nécessaires à l'application TrackToys.
 */
public class ConnexionData {

    // URL de connexion à la base SQLite
    private static String url = "jdbc:sqlite:tracktoys.db";

    /**
     * Établit une connexion à la base de données, active les clés étrangères
     * et initialise les tables si elles n'existent pas encore.
     *
     * @return une instance {@link Connection} active vers la base de données
     * @throws SQLException en cas d'erreur de connexion à la base
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        conn.createStatement().execute("PRAGMA foreign_keys=ON");
        initializeDatabase(conn);
        return conn;
    }

    /**
     * Initialise la structure de la base de données en créant toutes les tables nécessaires
     * si elles n'existent pas encore. Insère également des données par défaut (admin et client).
     *
     * @param conn la connexion active à la base de données
     */
    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            // Création des tables principales
            String sqlVoitures = "CREATE TABLE IF NOT EXISTS Voitures("
                    + "id_voiture INTEGER PRIMARY KEY,"
                    + "model_voiture TEXT NOT NULL,"
                    + "puissance_voiture INTEGER NOT NULL,"
                    + "priceLocation_voiture REAL NOT NULL,"
                    + "disponibilite_voiture INTEGER NOT NULL"
                    + ");";

            String sqlInfosMecha = "CREATE TABLE IF NOT EXISTS InfoMecha("
                    + "id_infomecha INTEGER PRIMARY KEY,"
                    + "id_voiture INTEGER NOT NULL,"
                    + "fuelMax_infomecha REAL NOT NULL,"
                    + "fuelLive_infomecha REAL NOT NULL,"
                    + "kilometrage_infomecha REAL NOT NULL,"
                    + "etat_infomecha TEXT NOT NULL,"
                    + "FOREIGN KEY(id_voiture) REFERENCES Voitures(id_voiture) ON DELETE CASCADE"
                    + ");";

            String sqlCircuits = "CREATE TABLE IF NOT EXISTS Circuits("
                    + "id_circuit INTEGER PRIMARY KEY,"
                    + "nom_circuit TEXT NOT NULL UNIQUE,"
                    + "adresse_circuit TEXT NOT NULL,"
                    + "distance_circuit REAL NOT NULL,"
                    + "tarif_circuit REAL NOT NULL"
                    + ");";

            String sqlRoles = "CREATE TABLE IF NOT EXISTS Roles("
                    + "id_role INTEGER PRIMARY KEY,"
                    + "nom_role TEXT NOT NULL UNIQUE"
                    + ");";

            String sqlClients = "CREATE TABLE IF NOT EXISTS Clients("
                    + "id_client INTEGER PRIMARY KEY,"
                    + "nom_client TEXT NOT NULL,"
                    + "prenom_client TEXT NOT NULL,"
                    + "age_client INTEGER NOT NULL,"
                    + "numPermis_client TEXT UNIQUE,"
                    + "mail_client TEXT NOT NULL UNIQUE,"
                    + "password_client TEXT NOT NULL,"
                    + "id_role INTEGER NOT NULL,"
                    + "FOREIGN KEY(id_role) REFERENCES Roles(id_role)"
                    + ");";

            String sqlEvenement = "CREATE TABLE IF NOT EXISTS Evenements("
                    + "id_evenement INTEGER PRIMARY KEY,"
                    + "nom_evenement TEXT NOT NULL,"
                    + "date_evenement TEXT NOT NULL,"
                    + "description_evenement TEXT NOT NULL,"
                    + "id_circuit INTEGER NOT NULL,"
                    + "FOREIGN KEY(id_circuit) REFERENCES Circuits(id_circuit) ON DELETE CASCADE"
                    + ");";

            String sqlReservation = "CREATE TABLE IF NOT EXISTS Reservation("
                    + "id_reservation INTEGER PRIMARY KEY,"
                    + "nom_event TEXT NOT NULL,"
                    + "id_client INTEGER,"
                    + "id_voiture INTEGER,"
                    + "id_circuit INTEGER,"
                    + "startDate_reservation TEXT NOT NULL,"
                    + "endDate_reservation TEXT NOT NULL,"
                    + "prixSeance_reservation REAL NOT NULL,"
                    + "status_reservation INTEGER NOT NULL,"
                    + "FOREIGN KEY(id_client) REFERENCES Clients(id_client) ON DELETE CASCADE,"
                    + "FOREIGN KEY(id_voiture) REFERENCES Voitures(id_voiture) ON DELETE CASCADE,"
                    + "FOREIGN KEY(id_circuit) REFERENCES Circuits(id_circuit) ON DELETE CASCADE"
                    + ");";

            String sqlMaintenance = "CREATE TABLE IF NOT EXISTS Maintenance("
                    + "id_maintenance INTEGER PRIMARY KEY,"
                    + "id_voiture INTEGER,"
                    + "start_maintenance TEXT NOT NULL,"
                    + "end_maintenance TEXT NOT NULL,"
                    + "FOREIGN KEY(id_voiture) REFERENCES Voitures(id_voiture)"
                    + ");";

            String sqlAdmin = "CREATE TABLE IF NOT EXISTS Admin("
                    + "id_admin INTEGER PRIMARY KEY,"
                    + "nom_admin TEXT NOT NULL,"
                    + "prenom_admin TEXT NOT NULL,"
                    + "mail_admin TEXT NOT NULL,"
                    + "password_admin TEXT NOT NULL,"
                    + "id_role INTEGER NOT NULL,"
                    + "FOREIGN KEY(id_role) REFERENCES Roles(id_role)"
                    + ");";

            String sqlChronos = "CREATE TABLE IF NOT EXISTS Chronos("
                    + "id_chrono INTEGER PRIMARY KEY,"
                    + "id_client INTEGER NOT NULL,"
                    + "id_voiture INTEGER NOT NULL,"
                    + "id_circuit INTEGER NOT NULL,"
                    + "chronoStart TEXT NOT NULL,"
                    + "chronoEnd TEXT NOT NULL,"
                    + "date_chrono TEXT NOT NULL,"
                    + "id_admin INTEGER NOT NULL,"
                    + "FOREIGN KEY(id_client) REFERENCES Clients(id_client),"
                    + "FOREIGN KEY(id_voiture) REFERENCES Voitures(id_voiture),"
                    + "FOREIGN KEY(id_circuit) REFERENCES Circuits(id_circuit),"
                    + "FOREIGN KEY(id_admin) REFERENCES Admin(id_admin)"
                    +");";

            // Exécution des requêtes
            stmt.execute(sqlVoitures);
            stmt.execute(sqlInfosMecha);
            stmt.execute(sqlCircuits);
            stmt.execute(sqlRoles);
            stmt.execute(sqlClients);
            stmt.execute(sqlEvenement);
            stmt.execute(sqlReservation);
            stmt.execute(sqlMaintenance);
            stmt.execute(sqlAdmin);
            stmt.execute(sqlChronos);

            // Insertion des rôles par défaut
            stmt.execute("INSERT OR IGNORE INTO Roles(id_role, nom_role) VALUES (1, 'Admin');");
            stmt.execute("INSERT OR IGNORE INTO Roles(id_role, nom_role) VALUES (2, 'Client');");

            // Vérification et insertion d'un administrateur par défaut
            var rs = stmt.executeQuery("SELECT COUNT(*) AS TOTAL FROM Admin;");
            if (rs.next() && rs.getInt("total") == 0) {
                String insertAdmin = "INSERT INTO Admin(id_admin, nom_admin, prenom_admin, mail_admin, password_admin, id_role)"
                        + "VALUES (1, 'Super', 'Admin', 'admin@tracktoys.com', 'admin123', 1);";
                stmt.executeUpdate(insertAdmin);
                System.out.println("Admin created");
            }

            // Vérification et insertion d'un client par défaut
            var rsClient = stmt.executeQuery("SELECT COUNT(*) AS total FROM Clients;");
            if (rsClient.next() && rsClient.getInt("total") == 0) {
                String insertClient = "INSERT INTO Clients(nom_client, prenom_client, age_client, numPermis_client, mail_client, password_client, id_role) "
                        + "VALUES ('Jean', 'Client', 30, 'PERMIS123', 'client@tracktoys.com', '1234', 2);";
                stmt.executeUpdate(insertClient);
                System.out.println("Client created");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Méthode utilitaire pour tester la connexion à la base de données
     * et afficher un message de confirmation si la structure est correcte.
     */
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("Connexion réussie et structure validée");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
        }
    }
}
