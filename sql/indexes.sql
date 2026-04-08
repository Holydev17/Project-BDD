-- Index 1 : Recherche des chambres par hotel
-- Lorsqu'on cherche toutes les chambres d'un hotel specifique,
-- PostgreSQL n'a pas besoin de parcourir toutes les chambres.
-- Il va directement aux chambres de l'hotel voulu.
CREATE INDEX idx_chambre_hotel
ON Chambre(id_hotel);

-- Index 2 : Verifie la disponibilite d'une chambre
-- Lorsqu'un client veut reserver une chambre, on doit verifier
-- si elle est deja reservee. l'index va accelere la verification
-- en evitant de parcourir toutes les reservations.
CREATE INDEX idx_reservation_chambre
ON Reservation(num_chambre);

-- Index 3 : Recherche des hotels par ville
-- Lorsqu'un client cherche des hotels dans une ville specifique,
-- l'index va permettre de trouver rapidement tous les hotels
-- de cette ville sans parcourir toute la table Hotel.
CREATE INDEX idx_hotel_ville
ON Hotel(ville);