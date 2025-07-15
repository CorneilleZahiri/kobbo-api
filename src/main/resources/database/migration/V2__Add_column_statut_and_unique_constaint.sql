ALTER TABLE natures ADD CONSTRAINT unique_intitule_societe_id UNIQUE (intitule, societes_id);

ALTER TABLE operations ADD COLUMN statut VARCHAR(50) NOT NULL DEFAULT 'VALIDE';

CREATE INDEX index_statut ON operations (statut);