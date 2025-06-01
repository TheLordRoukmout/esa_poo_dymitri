package main.obj;

public class Voitures extends Vehicule {
    private Integer idVoiture;
    private double prixLocation;
    private boolean disponible;

    public Voitures(String modele, int puissance, double prixLocation, boolean disponible) {
        this(null, modele, puissance, prixLocation, disponible);
    }

    public Voitures(Integer id, String modele, int puissance, double prixLocation, boolean disponible) {
        super(modele, puissance); // appel au constructeur de Vehicule
        this.idVoiture = id;
        this.prixLocation = prixLocation;
        this.disponible = disponible;
    }

    public Integer getIdVoiture() {
        return idVoiture;
    }

    public double getPrixLocation() {
        return prixLocation;
    }

    public boolean getDisponible() {
        return disponible;
    }

    @Override
    public String toString() {
        String etat = disponible ? "Disponible" : "Indisponible";
        return modele + " - " + puissance + "CV - " + prixLocation + "€/j - " + etat;
    }

    @Override
    public void afficherDetails() {
        System.out.println(this.toString());
    }
}
