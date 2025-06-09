package main.obj;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Représente une simulation de roulage sur circuit effectuée par un client avec une voiture spécifique,
 * encadrée par un administrateur, sur un circuit donné à une date et une durée précises.
 */
public class Simulation {

    private int idClient;
    private int idVoiture;
    private int idCircuit;
    private int idAdmin;

    private LocalTime chronoStart;
    private LocalTime chronoEnd;
    private LocalDate date;
    private int nombreTours;

    /**
     * Constructeur de la classe Simulation.
     *
     * @param idClient      l'identifiant du client participant à la simulation
     * @param idVoiture     l'identifiant de la voiture utilisée
     * @param idCircuit     l'identifiant du circuit utilisé
     * @param idAdmin       l'identifiant de l'administrateur encadrant la simulation
     * @param chronoStart   l'heure de début de la simulation
     * @param chronoEnd     l'heure de fin de la simulation
     * @param date          la date à laquelle la simulation a eu lieu
     * @param nombreTours   le nombre de tours effectués lors de la simulation
     */
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
