package ehotels.models;

public class Chambre {
    private int numChambre;
    private double prix;
    private int capacite;
    private String vue;
    private String commodite;
    private Hotel hotel; // lien vers l'objet Hotel

    public Chambre() { }

    public Chambre(int numChambre, double prix, int capacite, String vue, String commodite, Hotel hotel) {
        this.numChambre = numChambre;
        this.prix = prix;
        this.capacite = capacite;
        this.vue = vue;
        this.commodite = commodite;
        this.hotel = hotel;
    }

    // Getters & Setters
    public int getNumChambre() { return numChambre; }
    public void setNumChambre(int numChambre) { this.numChambre = numChambre; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    public String getVue() { return vue; }
    public void setVue(String vue) { this.vue = vue; }

    public String getCommodite() { return commodite; }
    public void setCommodite(String commodite) { this.commodite = commodite; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    

    @Override
    public String toString() {
        return "Chambre{" +
                "numChambre=" + numChambre +
                ", prix=" + prix +
                ", capacite=" + capacite +
                ", vue='" + vue + '\'' +
                ", commodite='" + commodite + '\'' +
                ", hotel=" + hotel +
                '}';
    }
}


