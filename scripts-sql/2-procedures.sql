SET search_path TO hackathon;


-- Supprime tous les triggers du schema

DO
$code$
    DECLARE
        r RECORD;
    BEGIN
        FOR r IN
            SELECT 'DROP TRIGGER ' || trigger_name || ' ON ' || event_object_table AS sql
			FROM information_schema.triggers t
            WHERE trigger_schema = CURRENT_SCHEMA
            GROUP BY event_object_table, trigger_name
            LOOP
                EXECUTE r.sql;
            END LOOP;
    END;
$code$;


-- Supprime toutes les fonctions du schema

DO $code$
DECLARE 
	r RECORD;
BEGIN
	FOR r IN
		SELECT 'DROP FUNCTION ' || ns.nspname || '.' || proname 
	       || '(' || oidvectortypes(proargtypes) || ')' AS sql
		FROM pg_proc INNER JOIN pg_namespace ns ON (pg_proc.pronamespace = ns.oid)
		WHERE ns.nspname = current_schema  
	LOOP
		EXECUTE r.sql;
	END LOOP;
END;
$code$;


-- Fonctions

CREATE OR REPLACE FUNCTION compte_inserer( 
	p_pseudo		VARCHAR,
	p_motdepasse	VARCHAR,
	p_email			VARCHAR,
	OUT p_idCompte 	INT 
) 
AS $code$
BEGIN
    INSERT INTO compte ( pseudo, motdepasse, email )
    VALUES ( p_pseudo, p_motdepasse, p_email )
	RETURNING idcompte INTO p_idCompte; 
END;
$code$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION compte_modifier( 
	p_pseudo		VARCHAR,
	p_motdepasse	VARCHAR,
	p_email			VARCHAR,
	p_idCompte 		INT 
) 
RETURNS VOID 
AS $code$
BEGIN
    UPDATE compte 
	SET pseudo = p_pseudo,
		motdepasse = p_motdepasse,
		email =	p_motdepasse
	WHERE idcompte = p_idcompte;
END;
$code$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION compte_supprimer( p_idCompte INT ) 
RETURNS VOID 
AS $code$
BEGIN
    DELETE FROM compte WHERE idcompte = p_idcompte;
END;
$code$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION compte_retrouver( p_idCompte INT ) 
RETURNS SETOF compte 
AS $code$
BEGIN
	RETURN QUERY
    SELECT * FROM compte WHERE idcompte = p_idcompte;
END;
$code$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION compte_lister_tout() 
RETURNS SETOF compte 
AS $code$
BEGIN
	RETURN QUERY
    SELECT * FROM compte ORDER BY pseudo;
END;
$code$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION compte_valider_authentification( p_pseudo VARCHAR, p_motdepasse VARCHAR ) 
RETURNS SETOF compte 
AS $code$
BEGIN
	RETURN QUERY
    SELECT * FROM compte WHERE pseudo = p_pseudo AND motdepasse = p_motdepasse;
END;
$code$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION compte_verifier_unicite_pseudo( 
	p_pseudo		VARCHAR,
	p_idCompte 		INT,
	OUT p_unicite	BOOLEAN	
) 
AS $code$
BEGIN
    SELECT COUNT(*) = 0 INTO p_unicite
    FROM compte
    WHERE pseudo = p_pseudo
      AND idcompte <> P_idcompte;
END;
$code$ LANGUAGE plpgsql;



-- Triggers
