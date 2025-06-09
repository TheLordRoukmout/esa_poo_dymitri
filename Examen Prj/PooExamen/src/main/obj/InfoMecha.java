package main.obj;

/**
 * Représente les informations mécaniques d'une voiture.
 * Cette classe permet de suivre l'état du carburant, le kilométrage, ainsi que l'état général de la voiture.
 */
public class InfoMecha {
    private int idVoiture;
    private double fuelMax;
    private double fuelLive;
    private double kilometrage;
    private String etat;

    /**
     * Constructeur de la classe InfoMecha.
     *
     * @param idVoiture    l'identifiant unique de la voiture concernée
     * @param fuelMax      la capacité maximale du réservoir en litres
     * @param fuelLive     la quantité actuelle de carburant en litres
     * @param kilometrage  le kilométrage actuel de la voiture
     * @param etat         l'état général de la voiture (ex : "Bon", "Mauvais", etc.)
     */
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

    /**
     * Simule un roulage de la voiture sur une certaine distance en consommant du carburant.
     *
     * @param km                  distance à parcourir (en kilomètres)
     * @param consommationParKm   consommation de carburant par kilomètre
     */
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

    /**
     * Remplit complètement le réservoir de la voiture.
     */
    public void faireLePlein() {
        fuelLive = fuelMax;
        System.out.println("Le plein a été effectué.");
    }

    /**
     * @return une représentation textuelle de l'état mécanique de la voiture
     */
    @Override
    public String toString() {
        return "Carburant : " + fuelLive + "/" + fuelMax + "L | "
                + "Km : " + kilometrage + " | "
                + "État : " + etat;
    }
}
