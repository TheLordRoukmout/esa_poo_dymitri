namespace WebApplicationTrackToys.Models{
    public class Cars{

        public static List<Cars> Garage = new List<Cars>();

        public int id {get; set;}
        public string NameCar {get; set;}
        public int CategoryCar {get; set;}

        public Cars(string nameCars, int categoryCar){
            id = Garage.Count+1;
            NameCar = nameCars;
            CategoryCar = categoryCar;
            Garage.Add(this);
        }

        public static void CreateCar(string nameCar, int categoryCar){
            Cars newCar = new Cars(nameCar, categoryCar);
        }

        public static List<Cars> ShowAllCars(){
            return Garage;
        }

    }
}