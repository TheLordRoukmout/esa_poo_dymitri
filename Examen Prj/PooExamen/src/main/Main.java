package main;

public class Main {
    public static void main(String[] args) {
        LoginService login = new LoginService();
        boolean sucessfullLog = login.LoginSystem("admin@tracktoys.com", "admin123");

        if(sucessfullLog){
            System.out.println("Login Successful");
        }else{
            System.out.println("Login Failed");
        }
    }
}
