#-- La société qui va contenir les utilisateurs et leurs opérations --
CREATE TABLE societes(
id binary(16) primary key not null default(uuid_to_bin(uuid())),
raison_sociale VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
adresse VARCHAR(255)
);

#-- Le profil utilisateur aussi appellé ROLE --
CREATE TABLE profil_utilisateurs(
id BIGINT primary key AUTO_INCREMENT NOT NULL,
libelle VARCHAR(50) NOT NULL
);

#-- Cette table contient tous les utilisateurs qui peuvent accéder à la plateforme
CREATE TABLE utilisateurs(
id binary(16) primary key not null default(uuid_to_bin(uuid())),
nom VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
mot_de_passe  VARCHAR(255) NOT NULL,
profil_utilisateurs_id BIGINT NOT NULL,
societes_id binary(16),
CONSTRAINT fk_utilisateur_profils FOREIGN KEY (profil_utilisateurs_id) REFERENCES profil_utilisateurs (id) ON DELETE CASCADE,
CONSTRAINT fk_utilisateur_societes FOREIGN KEY (societes_id) REFERENCES societes (id) ON DELETE CASCADE
);

#-- Cette table enregistre les natures des opérations comme le SALAIRE, le LOYER etc. --
CREATE TABLE natures(
id BIGINT primary key AUTO_INCREMENT NOT NULL,
intitule VARCHAR(255) NOT NULL,
societes_id binary(16) NOT NULL,
CONSTRAINT fk_nature_societe FOREIGN KEY (societes_id) REFERENCES societes (id) ON DELETE CASCADE
);

#-- Cette table permet d'associer à chaque opération
#-- le responsable qui a effectué l'opération dans la vie réelle par exemple le plombier du nom de Corneille
CREATE TABLE responsables(
id binary(16) primary key not null default(uuid_to_bin(uuid())),
nom VARCHAR(255) NOT NULL,
fonction  VARCHAR(255),
societes_id binary(16) NOT NULL,
CONSTRAINT fk_responsable_societe FOREIGN KEY (societes_id) REFERENCES societes (id) ON DELETE CASCADE
);

#--La table de toutes les opérations
CREATE TABLE operations(
id binary(16) primary key not null default(uuid_to_bin(uuid())),
date_operation date NOT NULL,
intitule LONGTEXT NOT NULL,
montant  DECIMAL(10, 2) NOT NULL,
type_operation VARCHAR(50) NOT NULL,
url_fichier VARCHAR(255),
utilisateurs_id binary(16) NOT NULL,
responsables_id binary(16) NOT NULL,
natures_id BIGINT NOT NULL,
date_creation timestamp default(current_timestamp()),
societes_id binary(16) NOT NULL,

CONSTRAINT fk_operation_utilisateurs FOREIGN KEY (utilisateurs_id) REFERENCES utilisateurs (id) ON DELETE CASCADE,
CONSTRAINT fk_operation_responsables FOREIGN KEY (responsables_id) REFERENCES responsables (id) ON DELETE CASCADE,
CONSTRAINT fk_operation_natures FOREIGN KEY (natures_id) REFERENCES natures (id) ON DELETE CASCADE,
CONSTRAINT fk_operation_societe FOREIGN KEY (societes_id) REFERENCES societes (id) ON DELETE CASCADE
);

#-- Pour les filtres indépendants ou combinés
CREATE INDEX idx_operations_date ON operations (date_operation);
CREATE INDEX idx_operations_type ON operations (type_operation);
