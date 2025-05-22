package main;

public class Voitures {
    private String modele;
    private int puissance;
    private double prixLocation;
    private boolean disponible;

    public Voitures(String modele, int puissance, double prixLocation, boolean disponible){
        this.modele = modele;
        this.puissance = puissance;
        this.prixLocation = prixLocation;
        this.disponible = disponible;
    }

    public String getModele(){
        return modele;
    }

    public int getPuissance(){
        return puissance;
    }

    public double getPrixLocation(){
        return prixLocation;
    }

    public boolean getDisponible(){
        return disponible;
    }

    @Override
    public String toString() {
        String etat = disponible ? "Disponible" : "Indisponible";
        return modele + " - " + puissance + "CV - " + prixLocation + "€/j - " + etat;
    }

}
