package ehotels.dao;

import ehotels.models.Chambre;
import ehotels.models.Hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreDAO {

    private Connection connection;

    public ChambreDAO(Connection connection) {
        this.connection = connection;
    }

    // Récupérer toutes les chambres
    public List<Chambre> getAllChambres() throws SQLException {
        List<Chambre> chambres = new ArrayList<>();

        String sql = "SELECT c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, " +
                     "h.id_hotel, h.ville, h.nom_chaine, h.categorie, " +
                     "h.email, h.telephone, h.nom_gestionaire " +
                     "FROM Chambre c " +
                     "JOIN Hotel h ON c.id_hotel = h.id_hotel";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            chambres.add(buildChambre(rs));
        }
        return chambres;
    }

    // Recherche par vue, commodite, nomChaine
    public List<Chambre> searchChambres(String vue, String commodite, String nom_chaine)
        throws SQLException {

        List<Chambre> chambres = new ArrayList<>();

        String sql = "SELECT c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, " +
                     "h.id_hotel, h.ville, h.nom_chaine, h.categorie, " +
                     "h.email, h.telephone, h.nom_gestionaire " +
                     "FROM Chambre c " +
                     "JOIN Hotel h ON c.id_hotel = h.id_hotel " +
                     "WHERE c.vue = ? AND c.commodite = ? AND h.nom_chaine = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, vue);
        stmt.setString(2, commodite);
        stmt.setString(3, nom_chaine);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            chambres.add(buildChambre(rs));
        }
        return chambres;
    }

    // Ajouter une chambre
    public void addChambre(Chambre c) throws SQLException {
        String sql = "INSERT INTO Chambre (num_chambre, prix, capacite, vue, commodite, id_hotel) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, c.getNumChambre());
        stmt.setDouble(2, c.getPrix());
        stmt.setInt(3, c.getCapacite());
        stmt.setString(4, c.getVue());
        stmt.setString(5, c.getCommodite());
        stmt.setInt(6, c.getHotel().getIdHotel());
        stmt.executeUpdate();
    }

    //  une chambre
    public void updateChambre(Chambre c) throws SQLException {
        String sql = "UPDATE Chambre SET prix = ?, capacite = ?, vue = ?, commodite = ? " +
                     "WHERE num_chambre = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDouble(1, c.getPrix());
        stmt.setInt(2, c.getCapacite());
        stmt.setString(3, c.getVue());
        stmt.setString(4, c.getCommodite());
        stmt.setInt(5, c.getNumChambre());
        stmt.executeUpdate();
    }

    // Suprimer une chambre
    public void deleteChambre(int num_chambre) throws SQLException {
        String sql = "DELETE FROM Chambre WHERE num_chambre = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, num_chambre);
        stmt.executeUpdate();
    }

    // Méthode utilitaire — construire Chambre + Hotel depuis ResultSet
    private Chambre buildChambre(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel(
            rs.getInt("id_hotel"),
            rs.getString("ville"),
            rs.getString("nom_chaine"),
            rs.getString("categorie"),
            rs.getString("email"),
            rs.getString("telephone"),
            rs.getString("nom_gestionaire")
        );

        return new Chambre(
            rs.getInt("num_chambre"),
            rs.getDouble("prix"),
            rs.getInt("capacite"),
            rs.getString("vue"),
            rs.getString("commodite"),
            hotel
        );
    }
    public List<Chambre> getChambresByHotel(int id_hotel) throws SQLException {
        List<Chambre> chambres = new ArrayList<>();

        String sql = "SELECT c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, " +
                     "h.id_hotel, h.ville, h.nom_chaine, h.categorie, " +
                     "h.email, h.telephone, h.nom_gestionaire " +
                     "FROM chambre c " +
                     "JOIN hotel h ON c.id_hotel = h.id_hotel " +
                     "WHERE c.id_hotel = ? " +
                     "ORDER BY c.num_chambre ASC";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id_hotel);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            chambres.add(buildChambre(rs));
        }
        return chambres;
    }

    public List<Chambre> rechercherChambresAvancee(
        String dateDebut,      // null si non saisi
        String dateFin,        // null si non saisi
        Integer capaciteMin,   // null si non saisi
        String commodite,      // null si non saisi
        Double prixMin,        // null si non saisi
        Double prixMax,        // null si non saisi
        String triPar,         // "prix" ou "capacite" ou null
        String ordre           // "ASC" ou "DESC" ou null
) throws SQLException {

    List<Chambre> chambres = new ArrayList<>();

    // Valeurs par défaut pour le tri
    String colonneTri = (triPar != null && triPar.equals("capacite"))
                        ? "c.capacite" : "c.prix";
    String ordreSQL   = (ordre != null && ordre.equals("DESC"))
                        ? "DESC" : "ASC";

    // Requête de base
    StringBuilder sql = new StringBuilder(
        "SELECT c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, " +
        "h.id_hotel, h.ville, h.nom_chaine, h.categorie, " +
        "h.email, h.telephone, h.nom_gestionaire " +
        "FROM chambre c " +
        "JOIN hotel h ON c.id_hotel = h.id_hotel " +
        "WHERE 1=1 "  // facilite l'ajout dynamique des filtres
    );

    // Filtre disponibilité par dates
    if (dateDebut != null && dateFin != null &&
        !dateDebut.isEmpty() && !dateFin.isEmpty()) {
        sql.append(
            "AND c.num_chambre NOT IN ( " +
            "    SELECT r.num_chambre FROM reservation r " +
            "    WHERE r.statut != 'annulee' " +
            "    AND NOT (r.date_fin <= ? OR r.date_debut >= ?) " +
            ") "
        );
    }

    // Filtre capacité
    if (capaciteMin != null) {
        sql.append("AND c.capacite >= ? ");
    }

    // Filtre commodité
    if (commodite != null && !commodite.isEmpty()) {
        sql.append("AND LOWER(c.commodite) LIKE LOWER(?) ");
    }

    // Filtre prix minimum
    if (prixMin != null) {
        sql.append("AND c.prix >= ? ");
    }

    // Filtre prix maximum
    if (prixMax != null) {
        sql.append("AND c.prix <= ? ");
    }

    // Tri
    sql.append("ORDER BY ").append(colonneTri).append(" ").append(ordreSQL);

    PreparedStatement stmt = connection.prepareStatement(sql.toString());

    // Remplissage dynamique des paramètres
    int index = 1;

    if (dateDebut != null && dateFin != null &&
        !dateDebut.isEmpty() && !dateFin.isEmpty()) {
        stmt.setString(index++, dateDebut);
        stmt.setString(index++, dateFin);
    }

    if (capaciteMin != null)                    stmt.setInt(index++, capaciteMin);
    if (commodite != null && !commodite.isEmpty()) stmt.setString(index++, "%" + commodite + "%");
    if (prixMin != null)                        stmt.setDouble(index++, prixMin);
    if (prixMax != null)                        stmt.setDouble(index++, prixMax);

    ResultSet rs = stmt.executeQuery();
    while (rs.next()) {
        chambres.add(buildChambre(rs));
    }
    return chambres;
}

}