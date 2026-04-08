create table ChaineHoteliere(
	nom_chaine varchar(255),
	nb_hotels integer, 
	email varchar(255),
	telephone varchar(255),
	adresse varchar(255),
	primary key(nom_chaine)
);

create table Client(
	NAS_client integer,
	date_inscription Date,
	c_first_name varchar(255),
	c_last_name varchar(255),
	c_adresse varchar(255),
	primary key(NAS_client)
);

create table Hotel(
    id_hotel integer,
    nom_chaine varchar(255),
    nb_chambre integer,
    email varchar(255),
    telephone varchar(255),
    categorie varchar(20),
    nom_gestionaire varchar(255),
    ville varchar(255),          
    primary key(id_hotel),
    foreign key(nom_chaine) references ChaineHoteliere
);

create table Employe(
	NAS_employe integer,
	id_hotel integer, 
	E_first_name varchar(255),
	E_last_name varchar(255),
	E_adresse varchar(255),
	roles varchar(255),
	primary key(NAS_employe),
	foreign key(id_hotel) references Hotel
);

create table Chambre(
    num_chambre integer,
    id_hotel integer,
    prix integer,
    commodite varchar(255),
    capacite integer,
    vue varchar(255),
    etat varchar(255),
    primary key(num_chambre),
    foreign key(id_hotel) references Hotel
);

create table Reservation(
    id_reservation integer,
    NAS_client integer,
    num_chambre integer,
    date_debut Date,
    date_fin Date,
    statut varchar(255),
    primary key(id_reservation),
    foreign key(NAS_client) references Client,
    foreign key(num_chambre) references Chambre
);

create table Locations(
    id_location integer,
    NAS_client integer,
    NAS_employe integer,
    num_chambre integer,
    checkin Date,
    date_debut Date,
    date_fin Date,
    primary key(id_location),
    foreign key(NAS_client) references Client,
    foreign key(NAS_employe) references Employe,
    foreign key(num_chambre) references Chambre
);