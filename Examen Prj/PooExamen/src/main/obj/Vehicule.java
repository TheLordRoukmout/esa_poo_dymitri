package main.obj;

/**
 * Classe abstraite représentant un véhicule générique.
 * Sert de base aux différentes catégories de véhicules utilisés dans le système,
 * en définissant les attributs communs tels que le modèle et la puissance.
 */
public abstract class Vehicule {
    protected String modele;
    protected int puissance;

    /**
     * Constructeur de la classe Vehicule.
     *
     * @param modele    le nom ou modèle du véhicule
     * @param puissance la puissance du véhicule (en chevaux, par exemple)
     */
    public Vehicule(String modele, int puissance) {
        this.modele = modele;
        this.puissance = puissance;
    }

    public String getModele() {
        return modele;
    }

    public int getPuissance() {
        return puissance;
    }

    /**
     * Méthode abstraite devant être implémentée par les classes,
     * permettant d'afficher les détails du véhicule.
     */
    public abstract void afficherDetails();
}
