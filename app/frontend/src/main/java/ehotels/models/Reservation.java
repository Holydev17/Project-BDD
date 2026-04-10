package ehotels.models;

import java.time.LocalDate;

public class Reservation {
    private int idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    private int numChambre;
    private int nasClient;
    private Chambre chambre;
    private Client client;

    public Reservation() { }

    public Reservation(int idReservation, LocalDate dateDebut, LocalDate dateFin, String statut, Chambre chambre, Client client) {
        this.idReservation = idReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.chambre = chambre;
        this.client = client;
    }

    public Reservation(int nasClient ,int numchambre,LocalDate dateDebut, LocalDate dateFin){
        this.nasClient = nasClient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.numChambre = numChambre;
    }

    // Getters & Setters

    public int getNasClient(){return nasClient;}
    
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Chambre getChambre() { return chambre; }
    public void setChambre(Chambre chambre) { this.chambre = chambre; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public int getNumChambre(){return numChambre;}
    public void setNumChambre(int NumChambre){this.numChambre = NumChambre;}
    

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", statut='" + statut + '\'' +
                ", chambre=" + chambre +
                ", client=" + client +
                '}';
    }
}