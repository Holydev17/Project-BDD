package ehotels.dao;

import ehotels.models.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClientDAO.java
 * Classe responsable des opérations sur la table Client.
 * Permet de récupérer, ajouter, modifier et supprimer des clients.
 *
 */
public class ClientDAO {

    private final Connection conn;

    // Constructeur — reçoit la connexion à la base de données
    public ClientDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Récupérer tous les clients de la base de données.
     */
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Client";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Client client = new Client(
                rs.getInt("NAS_client"),
                rs.getString("c_first_name"),
                rs.getString("c_last_name"),
                rs.getString("c_adresse"),
                rs.getDate("date_inscription").toLocalDate()
            );
            clients.add(client);
        }
        return clients;
    }

    /**
     * Récupérer un client par son NAS.
     */
    public Client getClientByNAS(int nasClient) throws SQLException {
        String sql = "SELECT * FROM Client WHERE NAS_client = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, nasClient);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Client(
                rs.getInt("NAS_client"),
                rs.getString("c_first_name"),
                rs.getString("c_last_name"),
                rs.getString("c_adresse"),
            );
        }
        return null;
    }

    /**
     * Ajouter un nouveau client dans la base de données.
     */
    public void addClient(Client client) throws SQLException {
        String sql = "INSERT INTO Client (NAS_client, c_first_name, c_last_name, c_adresse) VALUES ( ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, client.getNasClient());
        stmt.setString(3, client.getPrenom());
        stmt.setString(4, client.getNom());
        stmt.setString(5, client.getAdresse());
        stmt.executeUpdate();
    }

    /**
     * Modifier un client existant dans la base de données.
     */
    public void updateClient(Client client) throws SQLException {
        String sql = "UPDATE Client SET c_first_name = ?, c_last_name = ?, c_adresse = ? WHERE NAS_client = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(2, client.getPrenom());
        stmt.setString(3, client.getNom());
        stmt.setString(4, client.getAdresse());
        stmt.setInt(5, client.getNasClient());
        stmt.executeUpdate();
    }

    /**
     * Supprimer un client par son NAS.
     */
    public void deleteClient(int nasClient) throws SQLException {
        String sql = "DELETE FROM Client WHERE NAS_client = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, nasClient);
        stmt.executeUpdate();
    }
}