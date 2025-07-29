ALTER TABLE natures ADD CONSTRAINT unique_intitule_societe_id UNIQUE (intitule, societes_id);

ALTER TABLE operations ADD COLUMN statut VARCHAR(50) NOT NULL DEFAULT 'VALIDE';

CREATE INDEX index_statut ON operations (statut);

ALTER TABLE utilisateurs DROP FOREIGN KEY fk_utilisateur_societes;
ALTER TABLE utilisateurs DROP COLUMN societes_id;

ALTER TABLE profil_utilisateurs ADD COLUMN societes_id binary(16) NOT NULL;
ALTER TABLE profil_utilisateurs ADD CONSTRAINT fk_societe_profil FOREIGN KEY (societes_id) REFERENCES societes (id) ON DELETE CASCADE;
ALTER TABLE profil_utilisateurs ADD CONSTRAINT unique_libelle_societe_id UNIQUE (libelle, societes_id);

ALTER TABLE responsables RENAME TO tiers;
ALTER TABLE tiers ADD COLUMN contact VARCHAR(50);
ALTER TABLE tiers CHANGE fonction nature VARCHAR(255);

ALTER TABLE profil_utilisateurs RENAME TO roles;

ALTER TABLE utilisateurs DROP INDEX email;
ALTER TABLE utilisateurs DROP FOREIGN KEY fk_utilisateur_profils;
ALTER TABLE utilisateurs CHANGE profil_utilisateurs_id roles_id bigint NOT NULL;
ALTER TABLE utilisateurs ADD CONSTRAINT fk_utilisateur_roles FOREIGN KEY (roles_id) REFERENCES roles(id) ON DELETE CASCADE;

ALTER TABLE operations CHANGE intitule libelle LONGTEXT NOT NULL;
ALTER TABLE operations CHANGE responsables_id tiers_id binary(16) NOT NULL;
ALTER TABLE operations ADD COLUMN date_modify TIMESTAMP DEFAULT(CURRENT_TIMESTAMP()) ON UPDATE CURRENT_TIMESTAMP();
ALTER TABLE operations ADD COLUMN mode_de_payement VARCHAR(255) NOT NULL;
ALTER TABLE operations ADD COLUMN reference_mode_de_payement VARCHAR(255) DEFAULT '';
ALTER TABLE operations ADD COLUMN numero_formater VARCHAR(255) DEFAULT '';
ALTER TABLE operations DROP FOREIGN KEY fk_operation_societe;
ALTER TABLE operations DROP COLUMN societes_id;


ALTER TABLE utilisateurs DROP FOREIGN KEY fk_utilisateur_roles;
ALTER TABLE utilisateurs DROP COLUMN roles_id;
ALTER TABLE utilisateurs ADD CONSTRAINT unique_email UNIQUE (email);


ALTER TABLE operations change libelle description longtext NOT NULL;
ALTER TABLE operations DROP FOREIGN KEY fk_operation_utilisateurs;
ALTER TABLE operations DROP COLUMN utilisateurs_id;


CREATE TABLE permissions(
id binary(16) key not null default(uuid_to_bin(uuid())),
utilisateurs_id binary(16) not null,
roles_id bigint not null,
actif boolean default(true),
date_creation timestamp default(current_timestamp()),
date_modify timestamp default(current_timestamp()) ON UPDATE CURRENT_TIMESTAMP(),
constraint fk_utilisateurs_permissions foreign key (utilisateurs_id) references utilisateurs(id),
constraint fk_roles_permissions foreign key (roles_id) references roles(id),
constraint unique_utilisateurs_roles unique (utilisateurs_id, roles_id));


ALTER TABLE operations ADD COLUMN  permissions_id binary(16) NOT NULL;
ALTER TABLE operations ADD constraint fk_permissions_operations foreign key (permissions_id) references permissions(id) on delete cascade;
