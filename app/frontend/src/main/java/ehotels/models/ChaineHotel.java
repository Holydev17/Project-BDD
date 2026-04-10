package ehotels.models;

public class ChaineHotel {
    private int idChaine;
    private String nom;
    private String adresseSiege;
    private String telephone;
    private String email;

    // Constructeur
    public ChaineHotel(int idChaine, String nom, String adresseSiege, String telephone, String email) {
        this.idChaine = idChaine;
        this.nom = nom;
        this.adresseSiege = adresseSiege;
        this.telephone = telephone;
        this.email = email;
    }

    // Getters & Setters
    public int getIdChaine() { return idChaine; }
    public String getNom() { return nom; }
    public String getAdresseSiege() { return adresseSiege; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }

    public void setNom(String nom) { this.nom = nom; }
}