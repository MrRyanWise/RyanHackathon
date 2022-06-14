

-- Supprime le schéma hackathon

DROP SCHEMA IF EXISTS hackathon CASCADE;


-- Crée l'utilisateur hackathon
-- (après l'avoir supprimé au préalable s'il existait déjà)

DO $code$
BEGIN
	IF EXISTS (SELECT  FROM pg_catalog.pg_roles WHERE rolname  = 'hackathon')
	THEN
		REVOKE CREATE ON DATABASE postgres FROM hackathon;
		DROP USER hackathon;
	END IF;
END
$code$;

CREATE USER hackathon WITH PASSWORD 'hackathon';
GRANT CREATE ON DATABASE postgres TO hackathon;

