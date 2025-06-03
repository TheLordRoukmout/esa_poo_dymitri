using System;
using ExamenCafetiere.objCafetiere;

namespace ExamenCafetiere
{
    internal class Program
    {
        static void Main(string[] args)
        {
            var reservoir = new Reservoir(60);
            var resistance = new ResistanceChauffante();
            var filtre = new Filtre(reservoir, resistance);
            var recipient = new RecepientRecolteur(60);
            var suppoert = new SupportChauffant();
            
            filtre.InsererRecepient(recipient);
            
            var bloc = new BlocPrincipal(reservoir, recipient, suppoert, resistance);

            bloc.AjouterComposant(reservoir);
            bloc.AjouterComposant(filtre);
            bloc.AjouterComposant(recipient);
            bloc.AjouterComposant(suppoert);
            
            bloc.BoutonOnOff();
        
            for (int i = 0; i < 120; i++)
            {
                //Console.Clear(); // Rafraichir la console pour que ce soit beau
                Console.WriteLine($"Temps : {i + 1} sec");
                Console.WriteLine($"Température : {resistance.Temperature} °C");
                Console.WriteLine($"Eau restante dans le réservoir : {reservoir.ContenuEau} ml");
                Console.WriteLine($"Café dans le récipient : {recipient.Contenu} ml");
                Console.WriteLine($"Support chauffant actif : {(suppoert.EstActif ? "Oui" : "Non")}");
                Console.WriteLine();
                
                bloc.MettreAJour();

                Thread.Sleep(1000);
            }
            
            Console.WriteLine("Simulation terminé.");

        }
    }
}