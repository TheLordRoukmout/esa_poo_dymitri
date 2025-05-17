package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionData {
    private static String url = "jdbc:sqlite:tracktoys.db";

    // Méthode pour connecter et initialiser la db
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        conn.createStatement().execute("PRAGMA foreign_keys=ON");
        initializeDatabase(conn);
        return conn;
    }

    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            String sqlVoitures = "CREATE TABLE IF NOT EXISTS Voitures("
                    + "id_voiture INTEGER PRIMARY KEY,"
                    + "model_voiture TEXT NOT NULL,"
                    + "puissance_voiture INTEGER NOT NULL,"
                    + "priceLocation_voiture REAL NOT NULL,"
                    + "disponibilite_voiture INTEGER NOT NULL"
                    + ");";

            String sqlCircuits = "CREATE TABLE IF NOT EXISTS Circuits("
                    + "id_circuit INTEGER PRIMARY KEY,"
                    + "nom_circuit TEXT NOT NULL UNIQUE,"
                    + "adresse_circuit TEXT NOT NULL,"
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
                    + "numPermis_client TEXT NOT NULL UNIQUE,"
                    + "mail_client TEXT NOT NULL UNIQUE,"
                    + "password_client TEXT NOT NULL,"
                    + "id_role INTEGER NOT NULL,"
                    + "FOREIGN KEY(id_role) REFERENCES Roles(id_role)"
                    + ");";

            String sqlReservation = "CREATE TABLE IF NOT EXISTS Reservation("
                    + "id_reservation INTEGER PRIMARY KEY,"
                    + "id_client INTEGER,"
                    + "id_voiture INTEGER,"
                    + "startDate_reservation TEXT NOT NULL,"
                    + "endDate_reservation TEXT NOT NULL,"
                    + "prixSeance_reservation REAL NOT NULL,"
                    + "status_reservation INTEGER NOT NULL,"
                    + "FOREIGN KEY(id_client) REFERENCES Clients(id_client),"
                    + "FOREIGN KEY(id_voiture) REFERENCES Voitures(id_voiture)"
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

            stmt.execute(sqlVoitures);
            stmt.execute(sqlCircuits);
            stmt.execute(sqlRoles);
            stmt.execute(sqlClients);
            stmt.execute(sqlReservation);
            stmt.execute(sqlMaintenance);
            stmt.execute(sqlAdmin);

            //Insertion automatique d'un admin
            stmt.execute("INSERT OR IGNORE INTO Roles(id_role, nom_role) VALUES (1, 'Admin');");

            //Vérification de l'existance d'un admin
            var rs = stmt.executeQuery("SELECT COUNT(*) AS TOTAL FROM Admin;");
            if (rs.next() && rs.getInt("total") == 0) {
                String insertAdmin = "INSERT INTO Admin(id_admin, nom_admin, prenom_admin, mail_admin, password_admin, id_role)"
                        + "VALUES (1, 'Super', 'Admin', 'admin@tracktoys.com', 'admin123', 1);";
                stmt.executeUpdate(insertAdmin);
                System.out.println("Admin created");
            }else{
                System.out.println("Admin already exists");
            }

            System.out.println("Database check and initialized");

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Méthode test Connection
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("Connexion réussie et structure validée");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
        }
    }
}