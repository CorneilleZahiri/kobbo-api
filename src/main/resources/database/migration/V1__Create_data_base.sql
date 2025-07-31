-- Table SOCIETES
CREATE TABLE societes (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  raison_sociale VARCHAR(255) NOT NULL,
  email VARCHAR(255) DEFAULT '',
  adresse VARCHAR(255) DEFAULT ''
);

-- Table ROLES
CREATE TABLE roles (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  libelle VARCHAR(50) NOT NULL,
  societes_id BINARY(16) NOT NULL,
  UNIQUE (libelle, societes_id),
  FOREIGN KEY (societes_id) REFERENCES societes(id) ON DELETE CASCADE
);

-- Table UTILISATEURS
CREATE TABLE utilisateurs (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  nom VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  mot_de_passe VARCHAR(255) NOT NULL
);

-- Table COMPTES_SOCIETE
CREATE TABLE comptes_societe (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  utilisateurs_id BINARY(16) NOT NULL,
  roles_id BINARY(16) NOT NULL,
  actif BOOLEAN DEFAULT TRUE,
  date_creation TIMESTAMP DEFAULT(current_timestamp()),
  date_modify TIMESTAMP DEFAULT(current_timestamp()) ON UPDATE current_timestamp(),
  UNIQUE (utilisateurs_id, roles_id),
  FOREIGN KEY (utilisateurs_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
  FOREIGN KEY (roles_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Table TIERS
CREATE TABLE tiers (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  nom VARCHAR(255) NOT NULL,
  nature VARCHAR(255),
  contact VARCHAR(50),
  comptes_societe_id BINARY(16) NOT NULL,
  FOREIGN KEY (comptes_societe_id) REFERENCES comptes_societe(id) ON DELETE CASCADE
);

-- Table NATURES
CREATE TABLE natures (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  intitule VARCHAR(255) NOT NULL,
  comptes_societe_id BINARY(16) NOT NULL,
  UNIQUE (intitule, comptes_societe_id),
  FOREIGN KEY (comptes_societe_id) REFERENCES comptes_societe(id) ON DELETE CASCADE
);

-- Table OPERATIONS
CREATE TABLE operations (
  id BINARY(16) PRIMARY KEY NOT NULL DEFAULT(uuid_to_bin(uuid())),
  date_operation DATE NOT NULL,
  type_operation VARCHAR(50) NOT NULL,
  description LONGTEXT NOT NULL,
  montant DECIMAL(10,2) NOT NULL,
  comptes_societe_id BINARY(16) NOT NULL,
  tiers_id BINARY(16) NOT NULL,
  natures_id BINARY(16) NOT NULL,
  statut VARCHAR(50) NOT NULL DEFAULT 'VALIDE',
  mode_de_payement VARCHAR(255) NOT NULL,
  reference_mode_de_payement VARCHAR(255) DEFAULT '',
  numero_formater VARCHAR(255) DEFAULT '',
  url_fichier VARCHAR(255),
  date_creation TIMESTAMP DEFAULT(current_timestamp()),
  date_modify TIMESTAMP DEFAULT(current_timestamp()) ON UPDATE current_timestamp(),
  FOREIGN KEY (comptes_societe_id) REFERENCES comptes_societe(id) ON DELETE CASCADE,
  FOREIGN KEY (tiers_id) REFERENCES tiers(id) ON DELETE CASCADE,
  FOREIGN KEY (natures_id) REFERENCES natures(id) ON DELETE CASCADE,
  INDEX (date_operation),
  INDEX (type_operation),
  INDEX (statut)
);
