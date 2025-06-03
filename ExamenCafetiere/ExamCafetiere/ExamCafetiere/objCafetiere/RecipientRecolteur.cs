using ExamenCafetiere.Interface;

namespace ExamenCafetiere.objCafetiere;

public class RecepientRecolteur : IElementCafetiere
{
    public int Contenu { get; set; }
    public int CapaciteMax { get; set; }
    
    public RecepientRecolteur(int capacite = 500)
    {
        if (capacite <= 0)
            throw new ArgumentException("La capacite du récipient doit être positive");

        CapaciteMax = capacite;
    }
    
    public void RecevoirCafe(int quantite)
    {
        if (quantite < 0)
            throw new ArgumentException("La quantité de café reçue ne peut pas être négative");

        Contenu += quantite;

        if (Contenu > CapaciteMax)
        {
            Console.WriteLine("Le récipiente déborde !");
            Contenu = CapaciteMax;
        }
    }


    public void Vider()
    {
        if (Contenu == 0)
            throw new InvalidOperationException("Le récipient est déjà vide");

        Contenu = 0;
    }


    public void MettreAJour()
    {
        
    }
}