CREATE TABLE Voiures(
    id_voiture integer primary key,
    model_voiture varchar not null,
    puissance_voiture int not null,
    priceLocation_voiture decimal not null,
    disponibilite_voiture bool not null
);

CREATE TABLE Circuits(
    id_circuit int PRIMARY KEY,
    nom_circuit varchar not null unique,
    adresse_circuit varchar not null,
    tarif_circuit decimal not null
);

CREATE TABLE roles(
    id_role int primary key,
    nom_role varchar not null unique
);

CREATE TABLE Clients(
    id_client int PRIMARY KEY,
    nom_client varchar not null,
    prenom_client varchar not null,
    age_client int not null,
    numPermis_client varchar not null unique,
    mail_client varchar not null unique,
    password_client varchar not null,
    id_role int references roles(id_role)
);

CREATE TABLE Reservation(
    id_reservation int primary key,
    id_client int references Clients(id_client),
    id_voiture int references Voiures(id_voiture),
    startDate_reservation date not null,
    endDate_reservation date not null,
    prixSeance_reservation decimal not null,
    status_reservation bool not null
);

CREATE TABLE Maintenance(
    id_maintenance    int primary key,
    id_voiture        int references Voiures (id_voiture),
    start_maintenance date not null,
    end_maintenance   date not null
);

CREATE TABLE Admin(
    id_admin int primary key,
    nom_admin varchar not null,
    prenom_admin varchar not null,
    mail_admin varchar not null unique
);