package main;

import main.obj.Admin;
import main.obj.Client;

/**
 * Représente le résultat d'une tentative de connexion (login).
 * Cette classe permet d'identifier si la connexion a réussi et de récupérer
 * les informations associées à l'utilisateur connecté (admin ou client).
 */
public class LoginResult {

    /**
     * Indique si la tentative de connexion a réussi.
     */
    public boolean success;

    /**
     * Message d'information ou d'erreur associé à la tentative.
     */
    public String message;

    /**
     * Objet {@link Admin} représentant un administrateur connecté (si applicable).
     */
    public Admin admin;

    /**
     * Objet {@link Client} représentant un client connecté (si applicable).
     */
    public Client client;

    /**
     * Constructeur pour un résultat de connexion sans utilisateur associé.
     *
     * @param success indique si la connexion a réussi
     * @param message message informatif ou d'erreur
     */
    public LoginResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    /**
     * Constructeur pour un résultat de connexion en tant qu'administrateur.
     *
     * @param success indique si la connexion a réussi
     * @param message message informatif ou d'erreur
     * @param admin   l'administrateur connecté
     */
    public LoginResult(boolean success, String message, Admin admin){
        this.success = success;
        this.message = message;
        this.admin = admin;
    }

    /**
     * Constructeur pour un résultat de connexion en tant que client.
     *
     * @param success indique si la connexion a réussi
     * @param message message informatif ou d'erreur
     * @param client  le client connecté
     */
    public LoginResult(boolean success, String message, Client client){
        this.success = success;
        this.message = message;
        this.client = client;
    }

}
