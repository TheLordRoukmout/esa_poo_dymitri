using ExamenCafetiere.Interface;

namespace ExamenCafetiere.objCafetiere;

public class SupportChauffant : IElementCafetiere
{
    private int tempsRestant = 0;
    private const int dureeMax = 1800;

    public bool EstActif => tempsRestant > 0;

    public void Activer()
    {
        if (tempsRestant > dureeMax)
            throw new InvalidOperationException("Le support chauffant est activé avec une durée anormale");

        tempsRestant = dureeMax;
        Console.WriteLine($"Support chauffant activé pour {dureeMax} secondes");
    }

    public void MettreAJour()
    {
        if (tempsRestant < 0)
            throw new InvalidOperationException("Temps du support chauffant en dessous de zéro");

        if (tempsRestant > 0)
        {
            tempsRestant--;
            Console.WriteLine($"Support chauffant : {tempsRestant} secondes restantes");
        }
    }
}