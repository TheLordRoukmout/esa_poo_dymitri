package main.Interface;

import main.obj.Evenement;
import main.obj.Voitures;
import main.obj.Client;

import java.util.List;

/**
 * Interface représentant les services disponibles pour un client dans le système de gestion des événements automobiles.
 * Elle définit les méthodes permettant à un client de consulter, réserver ou annuler des événements,
 * ainsi que de vérifier la validité de son permis ou de récupérer des informations spécifiques.
 */
public interface IClientService {

    /**
     * Récupère la liste des événements actuellement disponibles à la réservation.
     *
     * @return une liste d'objets {@link Evenement} représentant les événements disponibles.
     */
    List<Evenement> getEvenementsDisponibles();

    /**
     * Permet à un client de réserver un événement en spécifiant son identifiant, l'événement choisi,
     * la voiture utilisée, et la date souhaitée.
     *
     * @param idClient          l'identifiant unique du client.
     * @param evenement         l'événement à réserver.
     * @param voiture           la voiture utilisée pour l'événement.
     * @param dateReservation   la date de la réservation, au format String.
     * @return true si la réservation a été effectuée avec succès, false sinon.
     * @throws Exception en cas d'erreur lors de la réservation (ex : date invalide, données manquantes, etc.).
     */
    boolean reserverEvenement(int idClient, Evenement evenement, Voitures voiture, String dateReservation) throws Exception;

    /**
     * Permet à un client d'annuler une réservation existante en précisant le nom de l'événement.
     *
     * @param idClient      l'identifiant unique du client.
     * @param nomEvenement  le nom de l'événement à annuler.
     * @return true si l'annulation a été effectuée avec succès, false sinon.
     */
    boolean annulerReservation(int idClient, String nomEvenement);

    /**
     * Vérifie si le client a un permis valide pour pouvoir participer à un événement.
     *
     * @param idClient  l'identifiant unique du client.
     * @return true si le permis est valide, false sinon.
     */
    boolean clientPermisValid(int idClient);

    /**
     * Affiche un avertissement ou prend une action si aucun événement n'est disponible.
     */
    void avertissementSiAucunEvenement();

    /**
     * Récupère une voiture par son identifiant.
     *
     * @param idVoiture  l'identifiant unique de la voiture.
     * @return l'objet {@link Voitures} correspondant à l'identifiant.
     */
    Voitures getVoitureById(int idVoiture);

    /**
     * Récupère un client par son identifiant.
     *
     * @param idClient  l'identifiant unique du client.
     * @return l'objet {@link Client} correspondant à l'identifiant.
     */
    Client getClientById(int idClient);
}

