namespace TrackToys;

public class Circuit
{
   public string circuitName { get; set; }
   public int circuitDistance {get; set;}
   public string circuitLocalisation {get; set;}

    public Circuit(string Name, int Distance, string Localisation)
    {
        if (string.IsNullOrEmpty(Name))
            throw new ArgumentException("Name cannot be null or empty");
        
        if(Distance <=1)
            throw new ArgumentException("Distance cannot be less than 1");
        
        if(string.IsNullOrEmpty(Localisation))
            throw new ArgumentException("Localisation cannot be null or empty");
        
        circuitName = Name;
        circuitDistance = Distance;
        circuitLocalisation = Localisation;
    }

    public void ShowCircuit()
    {
        System.Console.WriteLine("===== Infos Circuit =====");
        System.Console.WriteLine($"Circuit Name: {circuitName}\nDistance: {circuitDistance}Km\nLocalisation: {circuitLocalisation}");
    }
}

