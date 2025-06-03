using ExamenCafetiere.Interface;
using System;
using System.Collections.Generic;

namespace ExamenCafetiere.objCafetiere;

public class BlocPrincipal
{
    private List<IElementCafetiere> composants = new List<IElementCafetiere>();
    public bool estAllume = false;

    private Reservoir reservoir;
    private RecepientRecolteur recepient;
    private SupportChauffant support;
    private ResistanceChauffante resistance;

    public BlocPrincipal(Reservoir reservoir, RecepientRecolteur recepient, SupportChauffant support, ResistanceChauffante resistance)
    {
        this.reservoir = reservoir;
        this.recepient = recepient;
        this.support = support;
        this.resistance = resistance;
    }


    public void AjouterComposant(IElementCafetiere composant)
    {
        composants.Add(composant);
    }

    public void BoutonOnOff()
    {
        estAllume = !estAllume;

        if (estAllume)
        {
            Console.WriteLine("Cafetière allumee");
        }
        else
        {
            Console.WriteLine("Cafetière eteinte");
            support.Activer();
        }
    }

    public void MettreAJour()
    {
        if (estAllume)
        {
            resistance.MettreAJour(estAllume);

            foreach (var composant in composants)
            {
                composant.MettreAJour();
            }

            if (recepient.Contenu >= recepient.CapaciteMax)
            {
                Console.WriteLine("Recipient plein, arrêt du remplissage");
                estAllume = false;
                support.Activer();
            }

            if (reservoir.ReservoirVide())
            {
                Console.WriteLine("Réservoir vide");
                estAllume = false;
                support.Activer();
            }
        }
        
        support.MettreAJour();
    }

}