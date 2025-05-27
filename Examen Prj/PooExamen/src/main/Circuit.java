package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Circuit {
    private String nom;
    private String adresse;
    private double tarif;
    private double distance;

    public Circuit(String nom, String adresse, double tarif, double distance){
        this.nom = nom;
        this.adresse = adresse;
        this.tarif = tarif;
        this.distance = distance;
    }

    public String getNom(){
        return nom;
    }

    public String getAdresse(){
        return adresse;
    }

    public double getTarif(){
        return tarif;
    }

    public double getDistance(){
        return distance;
    }

    @Override
    public String toString(){
        return nom + " _ " + adresse + " (" + tarif + "€/session)";
    }

}
