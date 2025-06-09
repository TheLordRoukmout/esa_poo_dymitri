package main.obj;

/**
 * Représente un circuit disponible pour des événements automobiles.
 * Contient les informations liées au nom, à l'adresse, au tarif et à la distance du circuit.
 */
public class Circuit {
    private String nom;
    private String adresse;
    private double tarif;
    private double distance;

    /**
     * Constructeur de la classe Circuit.
     *
     * @param nom      le nom du circuit
     * @param adresse  l'adresse du circuit
     * @param tarif    le tarif par session sur ce circuit
     * @param distance la distance totale du circuit (en kilomètres)
     */
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
