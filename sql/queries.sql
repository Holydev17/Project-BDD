-- Requete 1 : Chercher les chambres disponibles par ville et dates
-- Utilisee quand un client recherche une chambre sur l'interface.
-- Retourne toutes les chambres disponibles pour une ville et des dates donnees.
SELECT c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, h.ville, h.nom_chaine
FROM Chambre c
JOIN Hotel h ON c.id_hotel = h.id_hotel
WHERE h.ville = 'Montreal'
AND c.num_chambre NOT IN (
    SELECT num_chambre FROM Reservation
    WHERE statut = 'confirmee'
    AND '2026-01-10' < date_fin  -- 2026 juste pour faire les testes , on peut changer 
    AND '2026-01-15' > date_debut
)
AND c.num_chambre NOT IN (
    SELECT num_chambre FROM Locations
    WHERE '2026-01-10' < date_fin
    AND '2026-01-15' > date_debut
);

-- Requete 2 : Voir toutes les reservations d'un client
-- Utilisee pour afficher l'historique des reservations d'un client.
SELECT r.id_reservation, r.date_debut, r.date_fin, r.statut,
       c.num_chambre, c.prix, h.ville, h.nom_chaine
FROM Reservation r
JOIN Chambre c ON r.num_chambre = c.num_chambre
JOIN Hotel h ON c.id_hotel = h.id_hotel
WHERE r.NAS_client = 111;

-- Requete 3 : Voir tous les hotels d'une chaine par categorie
-- Utilisee pour filtrer les hotels par chaine et categorie sur l'interface.
SELECT h.id_hotel, h.ville, h.categorie, h.email, h.telephone, h.nom_gestionaire
FROM Hotel h
WHERE h.nom_chaine = 'Marriott'
AND h.categorie = '5 etoiles'
ORDER BY h.ville;

-- Requete 4 : Voir les chambres disponibles par prix
-- Utilisee quand un client filtre les chambres par budget.
SELECT c.num_chambre, c.prix, c.capacite, c.vue, c.commodite, h.ville, h.nom_chaine
FROM Chambre c
JOIN Hotel h ON c.id_hotel = h.id_hotel
WHERE c.prix <= 200
AND c.num_chambre NOT IN (
    SELECT num_chambre FROM Reservation
    WHERE statut = 'confirmee'
    AND CURRENT_DATE BETWEEN date_debut AND date_fin -- prend la date d'aujourd'hui
)
ORDER BY c.prix ASC;

-- Requete 5 : Voir le nombre de reservations par hotel
-- Utilisee pour avoir une statistique sur les reservations par hotel.
SELECT h.id_hotel, h.ville, h.nom_chaine, COUNT(r.id_reservation) AS nombre_reservations
FROM Hotel h
JOIN Chambre c ON h.id_hotel = c.id_hotel
JOIN Reservation r ON c.num_chambre = r.num_chambre
GROUP BY h.id_hotel, h.ville, h.nom_chaine
ORDER BY nombre_reservations DESC;

-- Requete 6 : Voir les clients qui ont le plus loue de chambres
-- Utilisee pour identifier les clients les plus actifs.
SELECT cl.NAS_client, cl.c_first_name, cl.c_last_name,
       COUNT(l.id_location) AS nombre_locations
FROM Client cl
JOIN Locations l ON cl.NAS_client = l.NAS_client
GROUP BY cl.NAS_client, cl.c_first_name, cl.c_last_name
ORDER BY nombre_locations DESC;