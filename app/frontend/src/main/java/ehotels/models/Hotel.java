package ehotels.models;

public class Hotel {
    private int idHotel;
    private String ville;
    private String nomChaine;
    private String categorie;
    private String email;
    private String telephone;
    private String nomGestionaire;

    public Hotel() { }

    public Hotel(int idHotel, String ville, String nomChaine, String categorie, String email, String telephone, String nomGestionaire) {
        this.idHotel = idHotel;
        this.ville = ville;
        this.nomChaine = nomChaine;
        this.categorie = categorie;
        this.email = email;
        this.telephone = telephone;
        this.nomGestionaire = nomGestionaire;
    }

    // Getters & Setters
    public int getIdHotel() { return idHotel; }
    public void setIdHotel(int idHotel) { this.idHotel = idHotel; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getNomChaine() { return nomChaine; }
    public void setNomChaine(String nomChaine) { this.nomChaine = nomChaine; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getNomGestionaire() { return nomGestionaire; }
    public void setNomGestionaire(String nomGestionaire) { this.nomGestionaire = nomGestionaire; }

    @Override
    public String toString() {
        return "Hotel{" +
                "idHotel=" + idHotel +
                ", ville='" + ville + '\'' +
                ", nomChaine='" + nomChaine + '\'' +
                ", categorie='" + categorie + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", nomGestionaire='" + nomGestionaire + '\'' +
                '}';
    }
}