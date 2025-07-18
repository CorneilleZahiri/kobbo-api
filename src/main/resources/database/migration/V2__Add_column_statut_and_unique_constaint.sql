ALTER TABLE natures ADD CONSTRAINT unique_intitule_societe_id UNIQUE (intitule, societes_id);

ALTER TABLE operations ADD COLUMN statut VARCHAR(50) NOT NULL DEFAULT 'VALIDE';

CREATE INDEX index_statut ON operations (statut);

ALTER TABLE utilisateurs DROP FOREIGN KEY fk_utilisateur_societes;

ALTER TABLE utilisateurs DROP COLUMN societes_id;

ALTER TABLE profil_utilisateurs ADD COLUMN societes_id binary(16) NOT NULL;

ALTER TABLE profil_utilisateurs ADD CONSTRAINT fk_societe_profil FOREIGN KEY (societes_id) REFERENCES societes (id) ON DELETE CASCADE;