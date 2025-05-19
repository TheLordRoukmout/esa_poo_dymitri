package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public static boolean LoginSystem (String mail, String password, boolean[] isAdmin) {
        String sqlrequest = "SELECT a.id_role, r.nom_role FROM Admin a " +
                "INNER JOIN Roles r ON a.id_role = r.id_role " +
                "WHERE a.mail_admin = ? AND a.password_admin = ?";

        try(Connection conn = ConnexionData.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlrequest)){

            stmt.setString(1, mail);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String roleName = rs.getString("nom_role");
                isAdmin[0] = "admin".equalsIgnoreCase(roleName);
                return true;
            }
        }catch (SQLException e) {
            System.err.println("Login Error" + e.getMessage());
        }
        return false;
    }

}
