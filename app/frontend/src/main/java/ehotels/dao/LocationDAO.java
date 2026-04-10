package ehotels.dao;

import java.sql.Connection;
import java.sql.Date;           // java.sql.Date explicite
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;         

public class LocationDAO {
    private final Connection connection;

    public LocationDAO(Connection connection){
        this.connection = connection;
    }

    // Ajouter une location (CHECK-IN client)
    public boolean ajouterLocation(int NAS_client,int num_chambre, String date_debut, String date_fin) {

        String query = "INSERT INTO locations (id_location, nas_client, nas_employe, num_chambre, checkin, date_debut, date_fin) " +
                       "VALUES (?, ?, ?, ?, CURRENT_DATE, ?, ?)";

        try {
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(2, NAS_client);
            stmt.setInt(4, num_chambre);
            stmt.setDate(5, Date.valueOf(date_debut));  // java.sql.Date
            stmt.setDate(6, Date.valueOf(date_fin));    // java.sql.Date

            stmt.executeUpdate();
            System.out.println("Location ajoutée (check-in réussi)");
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }

    // Voir toutes les locations d'un client
    public List<String> getLocationsByClient(int NAS_client) {

        List<String> list = new ArrayList<>();

        String query = "SELECT l.id_location, l.date_debut, l.date_fin, l.checkin, " +
                       "c.num_chambre, h.ville, h.nom_chaine " +
                       "FROM locations l " +
                       "JOIN chambre c ON l.num_chambre = c.num_chambre " +
                       "JOIN hotel h ON c.id_hotel = h.id_hotel " +
                       "WHERE l.nas_client = ?";

        try {
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, NAS_client);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(
                    "Location #" + rs.getInt("id_location") +
                    " | Ville: " + rs.getString("ville") +
                    " | Chambre: " + rs.getInt("num_chambre") +
                    " | Check-in: " + rs.getDate("checkin") +
                    " | " + rs.getDate("date_debut") + " → " + rs.getDate("date_fin")
                );
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return list;
    }
}