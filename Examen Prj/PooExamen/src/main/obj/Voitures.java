package main.obj;

/**
 * Représente une voiture disponible à la location.
 * Hérite des propriétés générales d'un {@link Vehicule} et ajoute des attributs spécifiques comme
 * le prix de location, la disponibilité et un identifiant unique.
 */
public class Voitures extends Vehicule {
    private Integer idVoiture;
    private double prixLocation;
    private boolean disponible;

    /**
     * Constructeur sans identifiant (utilisé pour créer une nouvelle voiture avant assignation d'un ID).
     *
     * @param modele        le modèle de la voiture
     * @param puissance     la puissance du moteur (en chevaux)
     * @param prixLocation  le prix de location par jour
     * @param disponible    disponibilité de la voiture (true si disponible)
     */
    public Voitures(String modele, int puissance, double prixLocation, boolean disponible) {
        this(null, modele, puissance, prixLocation, disponible);
    }

    /**
     * Constructeur principal de la classe Voitures.
     *
     * @param id            l'identifiant unique de la voiture (peut être null pour les nouvelles voitures)
     * @param modele        le modèle de la voiture
     * @param puissance     la puissance du moteur (en chevaux)
     * @param prixLocation  le prix de location par jour
     * @param disponible    disponibilité de la voiture (true si disponible)
     */
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
