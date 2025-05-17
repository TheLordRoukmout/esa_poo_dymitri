package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public boolean LoginSystem (String mail, String password) {
        String sqlrequest = "SELECT * FROM Admin WHERE mail_admin = ? AND password_admin = ?";

        try(Connection conn = ConnexionData.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlrequest)){

            stmt.setString(1, mail);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return true;
            }
        }catch (SQLException e) {
            System.err.println("Login Error" + e.getMessage());
        }
        return false;
    }

}
