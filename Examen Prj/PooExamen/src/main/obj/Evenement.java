package main.obj;

/**
 * Représente un événement organisé sur un circuit.
 * Contient des informations telles que le nom, la date, la description de l'événement,
 * ainsi que l'identifiant du circuit concerné.
 */
public class Evenement {
    private String nomEvenement;
    private String dateEvenement;
    private String descriptionEvenement;
    private int idCircuit;

    public Evenement(String nomEvenement, String dateEvenement, String descriptionEvenement, int idCircuit){
        this.nomEvenement = nomEvenement;
        this.dateEvenement = dateEvenement;
        this.descriptionEvenement = descriptionEvenement;
        this.idCircuit = idCircuit;
    }

    public String getNomEvenement(){
        return nomEvenement;
    }

    public String getDateEvenement(){
        return dateEvenement;
    }

    public String getDescriptionEvenement(){
        return descriptionEvenement;
    }

    public int getIdCircuit(){
        return idCircuit;
    }
}
