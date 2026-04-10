package ehotels.models;

import java.time.LocalDate;

public class Location {
    private int idLocation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Chambre chambre;
    private Client client;

    public Location() { }

    public Location(int idLocation, LocalDate dateDebut, LocalDate dateFin, Chambre chambre, Client client) {
        this.idLocation = idLocation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chambre = chambre;
        this.client = client;
    }

    // Getters & Setters
    public int getIdLocation() { return idLocation; }
    public void setIdLocation(int idLocation) { this.idLocation = idLocation; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public Chambre getChambre() { return chambre; }
    public void setChambre(Chambre chambre) { this.chambre = chambre; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    @Override
    public String toString() {
        return "Location{" +
                "idLocation=" + idLocation +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", chambre=" + chambre +
                ", client=" + client +
                '}';
    }
}