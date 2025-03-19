using Microsoft.AspNetCore.Mvc;
using WebApplicationTrackToys.Models;

public class CarsController : Controller{
    public IActionResult AddCar(){
        return View();
    }

    [HttpPost]
    public IActionResult AddCar(string nameCar, int categoryCar){
        Cars.CreateCar(nameCar, categoryCar);
        return RedirectToAction("AllCars");
    }

    public IActionResult AllCars(){
        var cars = Cars.ShowAllCars();
        return View(cars);
    }
}