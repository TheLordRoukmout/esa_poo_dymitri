package main.obj;

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


    public void rouler(double km, double consommationParKm) {
        double carburantNecessaire = km * consommationParKm;

        if (fuelLive >= carburantNecessaire) {
            fuelLive -= carburantNecessaire;
            kilometrage += km;
            System.out.println("La voiture a roulé " + km + " km.");
        } else {
            System.out.println("Pas assez de carburant pour parcourir " + km + " km.");
        }
    }

    public void faireLePlein() {
        fuelLive = fuelMax;
        System.out.println("Le plein a été effectué.");
    }

    @Override
    public String toString() {
        return "Carburant : " + fuelLive + "/" + fuelMax + "L | "
                + "Km : " + kilometrage + " | "
                + "État : " + etat;
    }

}


