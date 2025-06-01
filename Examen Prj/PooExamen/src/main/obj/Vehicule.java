package main.obj;

public abstract class Vehicule {
    protected String modele;
    protected int puissance;

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

    public abstract void afficherDetails();
}
