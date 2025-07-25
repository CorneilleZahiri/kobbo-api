ALTER TABLE natures ADD CONSTRAINT unique_intitule_societe_id UNIQUE (intitule, societes_id);

ALTER TABLE operations ADD COLUMN statut VARCHAR(50) NOT NULL DEFAULT 'VALIDE';

CREATE INDEX index_statut ON operations (statut);

ALTER TABLE utilisateurs DROP FOREIGN KEY fk_utilisateur_societes;

ALTER TABLE utilisateurs DROP COLUMN societes_id;

ALTER TABLE profil_utilisateurs ADD COLUMN societes_id binary(16) NOT NULL;

ALTER TABLE profil_utilisateurs ADD CONSTRAINT fk_societe_profil FOREIGN KEY (societes_id) REFERENCES societes (id) ON DELETE CASCADE;

ALTER TABLE profil_utilisateurs ADD CONSTRAINT unique_libelle_societe_id UNIQUE (libelle, societes_id);

ALTER TABLE utilisateurs DROP INDEX email;

ALTER TABLE responsables RENAME TO tiers;

ALTER TABLE tiers ADD COLUMN contact VARCHAR(50);

ALTER TABLE tiers CHANGE fonction nature VARCHAR(255);

ALTER TABLE profil_utilisateurs RENAME TO roles;

ALTER TABLE utilisateurs DROP FOREIGN KEY fk_utilisateur_profils;

ALTER TABLE utilisateurs CHANGE profil_utilisateurs_id roles_id bigint NOT NULL;

ALTER TABLE utilisateurs ADD CONSTRAINT fk_utilisateur_roles FOREIGN KEY (roles_id) REFERENCES roles(id) ON DELETE CASCADE;