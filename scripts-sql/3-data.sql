SET search_path TO hackathon;


-- Supprimer toutes les données
DELETE FROM personne;
DELETE FROM admin;
DELETE FROM superUser;
DELETE FROM correcteur;
DELETE FROM hackathon;
DELETE FROM jury;
DELETE FROM equipe;
DELETE FROM participant;
DELETE FROM composer;
DELETE FROM evaluer;
DELETE FROM intervenir;

DELETE FROM role;
DELETE FROM compte;


-- Compte

INSERT INTO compte (idcompte, pseudo, motdepasse, email ) VALUES 
  (0, 'john', 'john', 'john@tryg.fr'),
  (1, 'geek', 'geek', 'geek@tryg.fr' ),
  (2, 'correcteur', 'correcteur', 'chef@tryg.fr' ),
  (3, 'job', 'job', 'job@tryg.fr' ),
  (4, 'admin', 'admin', 'admin@tryg.fr'),
  (5, 'correcteur2', 'correcteur2', 'chef@tryg.fr' ),
  (6, 'correcteur3', 'correcteur3', 'chef@tryg.fr' ) ;

ALTER TABLE compte ALTER COLUMN idcompte RESTART WITH 4;


-- Role

INSERT INTO role (idcompte, role) VALUES 
  ( 1, 'ADMINISTRATEUR' ),
  ( 2, 'CORRECTEUR' ),
  ( 3, 'UTILISATEUR' ),
  ( 4, 'ADMINBIS'),
  ( 5, 'CORRECTEUR' ),
  ( 6, 'CORRECTEUR' );

--personne 

INSERT INTO personne (id , nom, idcompte ) VALUES
  --données pour les Candidat N°didats
  (1, 'Candidat N° 1 Equipe N° 1', 0 ),
  (2, 'Candidat N° 2 Equipe N° 1', 0 ),
  (3, 'Candidat N° 3 Equipe N° 1', 0 ),
  (4, 'Candidat N° 4 Equipe N° 1', 0 ),

  (5, 'Candidat N° 1 Equipe N° 2', 0 ),
  (6, 'Candidat N° 2 Equipe N° 2', 0 ),
  (7, 'Candidat N° 3 Equipe N° 2', 0 ),

  (8, 'Candidat N° 1 Equipe N° 3', 0 ),
  (9, 'Candidat N° 2 Equipe N° 3',  0  ),
  (10, 'Candidat N° 3 Equipe N° 3', 0  ),
  (11, 'Candidat N° 4 Equipe N° 3', 0 ),

  (12, 'Candidat N° 1 Equipe N° 4', 0  ),
  (13, 'Candidat N° 2 Equipe N° 4', 0  ),
  (14, 'Candidat N° 3 Equipe N° 4', 0  ),
  (15, 'Candidat N° 4 Equipe N° 4', 0  ),

   
  
  --donneées pour les admins
  (16, 'admin 1', 4),

--donneées pour les le jury
  (18, 'Correcteur 1', 2),
  (19, 'Correcteur 2', 5),
  (20, 'Correcteur 3', 6),

  --données pour le super User
  (30 , 'superUser', 1 );

ALTER TABLE personne ALTER COLUMN id RESTART WITH 31;

--Admin
INSERT INTO admin VALUES 
  ( 16, 'Gestionnaire', 'admin' );

-- SuperUser

INSERT INTO superUser (id , cle ) VALUES 
  (30 , 'cleSuperUser');

-- hackathon 

INSERT INTO hackathon (idHackathon, nomHackathon, idSuperUser) VALUES 
  (1, 'First Hackathon', 30);

ALTER TABLE hackathon ALTER COLUMN idHackathon RESTART WITH 2;

--jury 

--Correcteur
INSERT INTO correcteur VALUES 
  ( 18, 'MDP123', 'Developpeur' ),
  ( 19, 'MDP210', 'Adminsitrateur Système' ),
  ( 20, 'MDP551', 'Big Data' );




INSERT INTO jury (idJury, nomJury, idHackathon ) VALUES 
  (1, 'jury 1', 1), 
  (2, 'jury 2', 1),
  (3, 'jury 3', 1);

ALTER TABLE jury ALTER COLUMN idJury RESTART WITH 2;

-- Equipe 

 

  INSERT INTO equipe(idEquipe, pseudo, nombreMembre, lienTravaux) VALUES
  (1, 'equipe 1', 4, 'lien vers travaux equipe 1'),
  (2, 'equipe 2', 3, 'lien vers travaux equipe 2'),
  (3, 'equipe 3', 4, 'lien vers travaux equipe 3'),
  (4, 'equipe 4', 4, 'lien vers travaux equipe 4');


ALTER TABLE equipe ALTER COLUMN idEquipe RESTART WITH 11;

--evaluer
INSERT INTO evaluer VALUES 
  (1, 19, 18),
  (1, 20, 20),
  
  (2, 18, 10),
  (2, 19, 11),
  (2, 20, 15),

  (3, 18, 18),
  (3, 19, 10),
  (3, 20, 13),

  (4, 18, 18),
  (4, 19, 15),
  (4, 20, 11);

-- Participant

INSERT INTO participant(id, idEquipe) VALUES 
  (1,  1 ),
  (2,  1 ),
  (3,  1 ),
  (4,  1 ),

  (5,  2 ),
  (6,  2 ),
  (7,  2 ),

  (8,  3 ),
  (9,  3 ),
  (10,  3 ),
  (11,  3 ),

  (12,  4 ),
  (13,  4 ),
  (14,  4 ),
  (15,  4 );

INSERT INTO intervenir (id , idHackathon) VALUES
  --données pour les Candidat N°didats
  (1, 1),
  (2, 1 ),
  (3, 1 ),
  (4, 1 ),

  (5, 1  ),
  (6, 1 ),
  (7, 1 ),

  (8,  1 ),
  (9,  1 ),
  (10, 1 ),
  (11, 1 ),

  (12, 1 ),
  (13, 1 ),
  (14, 1 ),
  (15, 1 ),

   
  
--donneées pour les admins
  (16, 1),

--donneées pour les le jury
  (18, 1 ),
  (19, 1 ),
  (20, 1 ),

  --données pour le super User
  (30 , 1 );


-- Categorie
  
-- INSERT INTO categorie (idcategorie, libelle, debut ) VALUES 
--   (1, 'Employés', {d '2021-02-25' } ),
--   (2, 'Partenaires', NULL ),
--   (3, 'Clients', NULL ),
--   (4, 'Fournisseurs', {d '2021-02-25' } ),
--   (5, 'Dirigeants', {d '2021-02-25' } );

-- ALTER TABLE categorie ALTER COLUMN idcategorie RESTART WITH 6;


-- -- Personne

-- INSERT INTO personne (idpersonne, idcategorie, nom, prenom) VALUES 
--   ( 1, 1, 'GRASSET', 'Jérôme' ),
--   ( 2, 1, 'BOUBY', 'Claude' ),
--   ( 3, 1, 'AMBLARD', 'Emmanuel' );

-- ALTER TABLE personne ALTER COLUMN idpersonne RESTART WITH 4;


-- -- Telephone

-- INSERT INTO telephone (idtelephone, idpersonne, libelle, numero ) VALUES 
--   ( 11, 1, 'Portable', '06 11 11 11 11' ),
--   ( 12, 1, 'Fax', '05 55 99 11 11' ),
--   ( 13, 1, 'Bureau', '05 55 11 11 11' ),
--   ( 21, 2, 'Portable', '06 22 22 22 22' ),
--   ( 22, 2, 'Fax', '05 55 99 22 22' ),
--   ( 23, 2, 'Bureau', '05 55 22 22 22' ),
--   ( 31, 3, 'Portable', '06 33 33 33 33' ),
--   ( 32, 3, 'Fax', '05 55 99 33 33' ),
--   ( 33, 3, 'Bureau', '05 55 33 33 33' );

-- ALTER TABLE telephone ALTER COLUMN idtelephone RESTART WITH 100;


-- -- mémo

-- INSERT INTO memo (idmemo, titre, description, flagurgent, idcategorie, statut, effectif, budget, echeance, heure ) VALUES 
--   (1, 'Mémo n°1', 'Texte du mémo n°1', TRUE, 1 , 'F', 2, 1234.56, {d '2022-02-25' }, {t '18:30' } ),
--   (2, 'Mémo n°2', 'Texte du mémo n°2', FALSE, 1, 'E', 4, 5000.00, {d '2022-05-18' }, {t '09:15' } ),
--   (3, 'Mémo n°3', NULL, TRUE, NULL, 'A',   NULL,   NULL,   NULL, NULL );

-- ALTER TABLE memo ALTER COLUMN idmemo RESTART WITH 4;


-- -- abonner

-- INSERT INTO abonner (idmemo, idCompte) VALUES 
--   ( 1, 1 ),
--   ( 1, 2 ),
--   ( 1, 3 ),
--   ( 2, 1 ),
--   ( 2, 2 );


-- -- abonner

-- INSERT INTO agir (idmemo, idpersonne, fonction, debut) VALUES 
--   ( 1, 1, 'pilote', {d '2022-01-01' } ),
--   ( 1, 2, 'secrétaire', {d '2022-01-06' } ),
--   ( 1, 3, 'trésorier', {d '2022-01-15' } ),
--   ( 2, 1, NULL, NULL ),
--   ( 2, 2, 'pilote', NULL );

-- -- Service

-- INSERT INTO service ( idservice, nom, anneecreation, flagsiege, idpersonne ) VALUES 
--   ( 1, 'Direction', 2007, TRUE, 1 ),
--   ( 2, 'Comptabilité', NULL, TRUE, 2 ),
--   ( 3, 'Agence Limoges', 2008, FALSE, 3 ),
--   ( 4, 'Agence Brive', 2014, FALSE, NULL );

-- ALTER TABLE service ALTER COLUMN idservice RESTART WITH 5;

