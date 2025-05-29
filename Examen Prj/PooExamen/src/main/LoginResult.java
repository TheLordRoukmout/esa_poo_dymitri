package main;


import main.obj.Admin;
import main.obj.Client;
import org.apache.commons.logging.Log;

public class LoginResult {
    public boolean success;
    public String message;
    public Admin admin;
    public Client client;

    public LoginResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public LoginResult(boolean success, String message, Admin admin){
        this.success = success;
        this.message = message;
        this.admin = admin;
    }

    public LoginResult(boolean success, String message, Client client){
        this.success = success;
        this.message = message;
        this.client = client;
    }

}
