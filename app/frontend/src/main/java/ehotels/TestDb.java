package ehotels;


import ehotels.db.DBConnection;
import ehotels.dao.ReservationDAO;
import ehotels.models.Reservation;
 
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
 
/**
 * Fichier de test jetable pour valider la connexion à la base de données
 * et le fonctionnement de ReservationDAO.
 *
 * À supprimer une fois le projet terminé.
 *
 * Lancement : clic droit sur le fichier dans l'IDE → Run 'TestDB.main()'
 */
public class TestDb {
 
    public static void main(String[] args) {
 
        System.out.println("=== Test de connexion à la base ehotels ===\n");
 
        try (Connection conn = DBConnection.getConnection()) {
 
            System.out.println("✅ Connexion DB OK");
            System.out.println("   URL : " + conn.getMetaData().getURL());
            System.out.println("   User : " + conn.getMetaData().getUserName());
            System.out.println();
 
            // --- Test du DAO ---
            ReservationDAO dao = new ReservationDAO(conn);
 
            // 1. getAllReservations()
            System.out.println("--- Test getAllReservations() ---");
            List<Reservation> reservations = dao.getAllReservations();
            System.out.println("Nombre de réservations trouvées : " + reservations.size());
 
            if (!reservations.isEmpty()) {
                System.out.println("\nPremière réservation :");
                Reservation r = reservations.get(0);
                System.out.println("  id        = " + r.getIdReservation());
                System.out.println("  dateDebut = " + r.getDateDebut());
                System.out.println("  dateFin   = " + r.getDateFin());
                System.out.println("  statut    = " + r.getStatut());
                System.out.println("  client    = " + r.getClient().getPrenom()
                                                    + " " + r.getClient().getNom()
                                                    + " (NAS " + r.getClient().getNasClient() + ")");
                System.out.println("  chambre   = #" + r.getChambre().getNumChambre()
                                                    + " (" + r.getChambre().getPrix() + "$)");
            } else {
                System.out.println("⚠️  Aucune réservation dans la table. Vérifie tes données importées.");
            }
 
            // 2. getReservationsByClient() — test avec le NAS du premier client trouvé
            if (!reservations.isEmpty()) {
                int nasTest = reservations.get(0).getClient().getNasClient();
                System.out.println("\n--- Test getReservationsByClient(" + nasTest + ") ---");
                List<Reservation> resClient = dao.getReservationsByClient(nasTest);
                System.out.println("Réservations pour ce client : " + resClient.size());
            }
 
            System.out.println("\n=== Tous les tests sont passés ===");
 
        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL : " + e.getMessage());
            System.out.println("\nStack trace complète :");
            e.printStackTrace();
            System.out.println("\n💡 Pistes de débogage :");
            System.out.println("  - Postgres.app est-il démarré ? (éléphant dans la barre de menu)");
            System.out.println("  - La base 'ehotels' existe-t-elle ?");
            System.out.println("  - URL/USER/PASSWORD corrects dans DBConnection.java ?");
            System.out.println("  - Le driver postgresql est-il dans pom.xml (sans scope 'provided') ?");
        }
    }
}
