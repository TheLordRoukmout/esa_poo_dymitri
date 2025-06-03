using ExamenCafetiere.Interface;

namespace ExamenCafetiere.objCafetiere;

public class Filtre : IElementCafetiere
{
    private int contenu;
    private int capaciteMax = 100;

    private readonly Reservoir reservoir;
    private readonly ResistanceChauffante resistance;
    private RecepientRecolteur? recepient;

    public Filtre(Reservoir reservoir, ResistanceChauffante resistance)
    {
        this.reservoir = reservoir;
        this.resistance = resistance;
    }

    public void InsererRecepient(RecepientRecolteur r)
    {
        if (r == null)
            throw new ArgumentNullException(nameof(r), "Le récipient doit être présent");

        recepient = r;
    }

    public void RetirerRecepient()
    {
        recepient = null;
    }

    public void MettreAJour()
    {
        if (resistance.BonneTemperature() && !reservoir.ReservoirVide())
        {
            int eau = reservoir.PreleverEau(5);
            contenu += eau;

            if (contenu > capaciteMax)
                contenu = capaciteMax;
        }

        if (recepient == null)
        {
            throw new InvalidOperationException("Aucun récipient pour recevoir le café");
        }

        recepient.RecevoirCafe(contenu);
        contenu = 0;
    }
}