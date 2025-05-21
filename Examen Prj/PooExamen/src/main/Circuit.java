package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Circuit {
    private String nom;
    private String adresse;
    private double tarif;

    public Circuit(String nom, String adresse, double tarif){
        this.nom = nom;
        this.adresse = adresse;
        this.tarif = tarif;
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

    @Override
    public String toString(){
        return nom + " _ " + adresse + " (" + tarif + "€/session)";
    }

}
