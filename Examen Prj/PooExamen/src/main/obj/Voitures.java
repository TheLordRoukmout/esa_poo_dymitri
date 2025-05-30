package main.obj;

public class Voitures {
    private Integer idVoiture;
    private String modele;
    private int puissance;
    private double prixLocation;
    private boolean disponible;

    // Ancien constructeur sans id (rien à changer pour le code existant)
    public Voitures(String modele, int puissance, double prixLocation, boolean disponible){
        this(null, modele, puissance, prixLocation, disponible); // délègue au constructeur principal
    }

    // Nouveau constructeur avec id (à utiliser là où c'est nécessaire)
    public Voitures(Integer id, String modele, int puissance, double prixLocation, boolean disponible){
        this.idVoiture = id;
        this.modele = modele;
        this.puissance = puissance;
        this.prixLocation = prixLocation;
        this.disponible = disponible;
    }

    public Integer getIdVoiture(){
        return idVoiture;
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
