package main.obj;

import java.time.LocalDate;
import java.time.LocalTime;

public class Simulation {

    private int idClient;
    private int idVoiture;
    private int idCircuit;
    private int idAdmin;

    private LocalTime chronoStart;
    private LocalTime chronoEnd;
    private LocalDate date;
    private int nombreTours;

    public Simulation(int idClient, int idVoiture, int idCircuit, int idAdmin, LocalTime chronoStart, LocalTime chronoEnd, LocalDate date, int nombreTours) {
        this.idClient = idClient;
        this.idVoiture = idVoiture;
        this.idCircuit = idCircuit;
        this.idAdmin = idAdmin;
        this.chronoStart = chronoStart;
        this.chronoEnd = chronoEnd;
        this.date = date;
        this.nombreTours = nombreTours;
    }

    // Getters
    public int getIdClient() {
        return idClient;
    }

    public int getIdVoiture() {
        return idVoiture;
    }

    public int getIdCircuit() {
        return idCircuit;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public LocalTime getChronoStart() {
        return chronoStart;
    }

    public LocalTime getChronoEnd() {
        return chronoEnd;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNombreTours() {
        return nombreTours;
    }

}
