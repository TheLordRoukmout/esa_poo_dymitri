package main;

public class InfoMecha {
    private int idVoiture;
    private double fuelMax;
    private double fuelLive;
    private double kilometrage;
    private String etat;

    public InfoMecha(int idVoiture, double fuelMax, double fuelLive, double kilometrage, String etat){
        this.idVoiture = idVoiture;
        this.fuelMax = fuelMax;
        this.fuelLive = fuelLive;
        this.kilometrage = kilometrage;
        this.etat = etat;
    }

    public int getIdVoiture(){
        return idVoiture;
    }

    public double getFuelMax(){
        return fuelMax;
    }

    public double getFuelLive(){
        return fuelLive;
    }

    public double getKilometrage(){
        return kilometrage;
    }

    public String getEtat(){
        return etat;
    }

    @Override
    public String toString() {
        return "Carburant : " + fuelLive + "/" + fuelMax + "L | "
                + "Km : " + kilometrage + " | "
                + "État : " + etat;
    }

}


