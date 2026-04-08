-- La vue 1 : le nombre de chambre par zone 

-- Ma logique : 
-- Pour chaque ville (zone) :
--  Prendre toutes les chambres de cette ville
-- 	Enlever celles qui sont réservées (statut confirmée) pour aujourd'hui
--  Enlever celles qui sont en location pour aujourd'hui
--  Compter ce qui reste = chambres disponibles

CREATE VIEW chambres_disponibles_par_zone AS
SELECT 
    h.ville AS zone,
    COUNT(c.num_chambre) AS nombre_chambres_disponibles
FROM Chambre c
JOIN Hotel h ON c.id_hotel = h.id_hotel
WHERE c.num_chambre NOT IN (
    SELECT num_chambre FROM Reservation
    WHERE statut = 'confirmee'
    AND CURRENT_DATE BETWEEN date_debut AND date_fin
    UNION
    SELECT num_chambre FROM Locations
    WHERE CURRENT_DATE BETWEEN date_debut AND date_fin
)
GROUP BY h.ville;


-- Vue 2 : Pour la capacite total pour une hotel 

-- Pour chaque hotels 
--  Additionner la capacite de toutes ses chambres 
--  Afficher le total 

CREATE VIEW capacite_totale_par_hotel AS
SELECT 
    h.id_hotel,
    h.ville,
    h.nom_chaine,
    SUM(c.capacite) AS capacite_totale
FROM Hotel h
JOIN Chambre c ON h.id_hotel = c.id_hotel
GROUP BY h.id_hotel, h.ville, h.nom_chaine;

