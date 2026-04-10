package ehotels.dao;

import ehotels.models.Reservation;
import ehotels.models.Chambre;
import ehotels.models.Client;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private final Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter une réservation
    public boolean ajouterReservation(Reservation r) {
        String query = "INSERT INTO reservation (nas_client, num_chambre, " +
                       "date_debut, date_fin) " +
                       "VALUES ( ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(2, r.getNasClient());
            stmt.setInt(3, r.getNumChambre());
            stmt.setDate(4, Date.valueOf(r.getDateDebut()));  // ✅ java.sql.Date explicite
            stmt.setDate(5, Date.valueOf(r.getDateFin()));    // ✅ java.sql.Date explicite
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            return false;
        }
    }

    // Lire toutes les réservations
    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();

        String query = "SELECT r.id_reservation, r.date_debut, r.date_fin, r.statut, " +
                       "c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, " +
                       "cl.nas_client, cl.c_first_name, cl.c_last_name, " +
                       "cl.c_adresse, cl.date_inscription " +
                       "FROM reservation r " +
                       "JOIN chambre c ON r.num_chambre = c.num_chambre " +
                       "JOIN client cl ON r.nas_client = cl.nas_client";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(buildReservation(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return list;
    }

    // Lire réservations d'un client
    public List<Reservation> getReservationsByClient(int nas_client) {
        List<Reservation> list = new ArrayList<>();

        String query = "SELECT r.id_reservation, r.date_debut, r.date_fin, r.statut, " +
                       "c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, " +
                       "cl.nas_client, cl.c_first_name, cl.c_last_name, " +
                       "cl.c_adresse, cl.date_inscription " +
                       "FROM reservation r " +
                       "JOIN chambre c ON r.num_chambre = c.num_chambre " +
                       "JOIN client cl ON r.nas_client = cl.nas_client " +
                       "WHERE r.nas_client = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, nas_client);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildReservation(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return list;
    }

    // Modifier le statut d'une réservation
    public boolean updateStatut(int idReservation, String nouveauStatut) {
        String query = "UPDATE reservation SET statut = ? WHERE id_reservation = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, idReservation);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            return false;
        }
    }

    // Supprimer une réservation
    public boolean supprimerReservation(int idReservation) {
        String query = "DELETE FROM reservation WHERE id_reservation = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idReservation);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            return false;
        }
    }

    // Méthode utilitaire — construire Reservation depuis ResultSet
    private Reservation buildReservation(ResultSet rs) throws SQLException {
        Client client = new Client(
            rs.getInt("nas_client"),
            rs.getString("c_first_name"),
            rs.getString("c_last_name"),
            rs.getString("c_adresse"),
            rs.getDate("date_inscription") != null ?
                rs.getDate("date_inscription").toLocalDate() : null
        ); 

        Chambre chambre = new Chambre(
            rs.getInt("num_chambre"),
            rs.getDouble("prix"),
            rs.getInt("capacite"),
            rs.getString("vue"),
            rs.getString("commodite"),
            null
        );

        return new Reservation(
            rs.getInt("id_reservation"),
            rs.getDate("date_debut").toLocalDate(),
            rs.getDate("date_fin").toLocalDate(),
            rs.getString("statut"),
            chambre,
            client
        );
    }
}