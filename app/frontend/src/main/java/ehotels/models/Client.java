package ehotels.models;

import java.time.LocalDate;

public class Client {

    private int nasClient;

    private String prenom;

    private String nom;

    private String adresse;

    private LocalDate dateInscription;

    
    // Getters & Setters

    public int getNasClient() { return nasClient; }
    public void setNasClient(int nasClient) { this.nasClient = nasClient; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse(){return adresse;}
    public void setAdresse(String adresse){this.adresse = adresse;}


    @Override
    public String toString() {
        return "Client{" +
                "nasClient=" + nasClient +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
    // New constructor

    public Client(int nasClient, String firstName, String lastName, String adresse, LocalDate dateInscription) {

        this.nasClient = nasClient;

        this.prenom = firstName;

        this.nom = lastName;

        this.adresse = adresse;

        this.dateInscription = dateInscription;

    }

    public Client(int nasClient, String firstName, String lastName, String adresse) {

        this.nasClient = nasClient;

        this.prenom = firstName;

        this.nom = lastName;

        this.adresse = adresse;


    }





}