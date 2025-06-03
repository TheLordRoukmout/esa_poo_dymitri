using ExamenCafetiere.Interface;

namespace ExamenCafetiere.objCafetiere;

public class Reservoir : IElementCafetiere
{
    public int ContenuEau { get; set; }
    public int CapaciteMax { get; set; } = 1000;

    public Reservoir(int quantiteInitiale)
    {
        if (quantiteInitiale < 0)
        {
            ContenuEau = 0;
        }
        else if (quantiteInitiale > CapaciteMax)
        {
            ContenuEau = CapaciteMax;
        }
        else
        {
            ContenuEau = quantiteInitiale;
        }
    }
    
    public bool ReservoirVide()
    {
        return ContenuEau <= 0;
    }

    public int PreleverEau(int quantite)
    {
        if (quantite < 0)
            throw new ArgumentException("La quantité d'eau à prélever ne peut pas être négative");

        if (ContenuEau <= 0)
            return 0;

        int eauPrelevee;

        if (quantite >= ContenuEau)
        {
            eauPrelevee = ContenuEau;
            ContenuEau = 0;
        }
        else
        {
            eauPrelevee = quantite;
            ContenuEau -= quantite;
        }

        return eauPrelevee;
    }

    
    public void MettreAJour()
    {
    }

}