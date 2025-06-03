using ExamenCafetiere.Interface;

namespace ExamenCafetiere.objCafetiere;

public class ResistanceChauffante
{
    public int Temperature { get; private set; } = 20;
    private bool enTrainDeChauffer = true;

    public bool BonneTemperature()
    {
        return Temperature >= 85 && Temperature <= 95;
    }

    public void MettreAJour(bool estAllume)
    {
        if (Temperature < 0 || Temperature > 150)
            throw new InvalidOperationException("Température hors limites");

        if (estAllume)
        {
            if (enTrainDeChauffer)
            {
                Temperature += 5;
                if (Temperature >= 95)
                {
                    Temperature = 95;
                    enTrainDeChauffer = false;
                }
            }
            else // refroidissement contrôlé
            {
                Temperature -= 1;
                if (Temperature <= 85)
                {
                    Temperature = 85;
                    enTrainDeChauffer = true;
                }
            }
        }
        else // cafetière éteinte → température descend vers 20°
        {
            if (Temperature > 20)
                Temperature -= 1;
        }
    }
}