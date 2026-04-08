-- Triggers 1 : Verifie la disponibilite d'une chambre
-- avant une reservation 

create or replace Function verifier_disponibilite()
returns trigger as $$ -- but fonction 
begin 
	if exists (
		select 1 from Reservation
		where num_chambre = new.num_chambre
		and new.date_debut < date_fin
		and new.date_fin > date_debut
	) then
		raise exception 'Chambre deja reservee pour ces dates';
	end if;
	return new;
end;

$$ language plpgsql; -- fin de fonction 

-- Trigger pour appel la fonction trigger_disponibilite
create trigger trigger_disponibilite
before insert on Reservation
for each row execute function verifier_disponibilite();



-- trigger 2 : Conversion reservation a location 
create or replace function convertir_reservation()
returns trigger as $$ -- debut fonction 
begin 
	update Reservation
	set statut = 'convertie'
	where num_chambre = new.num_chambre
	and NAS_client = new.NAS_client
	and statut = 'confirme';
	return new;
end;

$$ language plpgsql; -- fin fonction 

-- Trigger pour appel la fonction trigger_conversion 
create trigger trigger_conversion
before insert on Locations
for each row execute function convertir_reservation();