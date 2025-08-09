-- Table SOCIETES
CREATE TABLE societes (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  raison_sociale VARCHAR(255) NOT NULL,
  email VARCHAR(255) DEFAULT '',
  adresse VARCHAR(255) DEFAULT ''
);

ALTER TABLE societes ADD COLUMN pays VARCHAR(255) NOT NULL DEFAULT 'Bénin';
ALTER TABLE societes ADD UNIQUE INDEX uq_raison_sociale_pays (raison_sociale, pays);