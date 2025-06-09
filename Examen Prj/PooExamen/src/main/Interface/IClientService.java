package main.Interface;

import main.obj.Evenement;
import main.obj.Voitures;
import main.obj.Client;

import java.util.List;

public interface IClientService {
    List<Evenement> getEvenementsDisponibles();
    boolean reserverEvenement(int idClient, Evenement evenement, Voitures voiture, String dateReservation) throws Exception;
    boolean annulerReservation(int idClient, String nomEvenement);
    boolean clientPermisValid(int idClient);
    void avertissementSiAucunEvenement();
    Voitures getVoitureById(int idVoiture);
    Client getClientById(int idClient);

}
