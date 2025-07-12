# Documentation utilisateur – Profil Administrateur (Kobbo)

## NB: Consulter le fichier src/main/resources/database/migration/V1__Create_data_base.sql pour comprendre l'analyse

## 📚 Introduction

**Kobbo** est une application SaaS open source permettant la gestion des dépenses et recettes pour les particuliers, 
professionnels indépendants, et petites entreprises. Cette documentation présente le parcours complet 
d'un **utilisateur Administrateur**, de la création de son compte jusqu'à sa déconnexion.

---

## 🔧 Scénario complet du profil **Administrateur**

### ✅ 1. Création de compte

#### URL : `POST /auth/register`

L'utilisateur fournit :

- `nom`
- `email`
- `mot_de_passe`

Le backend :

- Crée un utilisateur avec un profil temporaire
- Génère un identifiant UUID
- Retourne une confirmation d'inscription

**✉️ Réponse attendue :**

```json
{
  "message": "Compte créé avec succès. Connectez-vous pour continuer."
}
```

---

### 🔐 2. Connexion

#### URL : `POST /auth/login`

L'utilisateur s'identifie avec :

- `email`
- `mot_de_passe`

Le backend :

- Vérifie les identifiants
- Retourne un **JWT Token** et les informations utilisateur

**✉️ Réponse attendue :**

```json
{
  "token": "eyJhbGci...",
  "profil": "TEMPORAIRE",
  "societe": null
}
```

---

### 🏢 3. Création de société (obligatoire si aucune société liée)

#### URL : `POST /societes`

Lorsque `societes_id` de l'utilisateur est `null`, on affiche un formulaire de création d'entreprise.

Champs requis :

- `raison_sociale`
- `email`
- `adresse` (optionnelle)

Le backend :

- Crée une nouvelle entrée dans la table `societes`
- Met à jour la table `utilisateurs` :
   - Associe `societes_id`
   - Change `profil_utilisateurs_id` en **ADMIN**

**✉️ Réponse attendue :**

```json
{
  "message": "Société créée avec succès. Accès au dashboard disponible."
}
```

---

### 📊 4. Accès au tableau de bord (Dashboard)

#### URL : `GET /dashboard`

Une fois la société créée, l'administrateur accède au tableau de bord qui affiche :

- Solde actuel
- Total dépenses / recettes
- Répartition par nature
- Liste des dernières opérations

---

### 💼 5. Gestion des utilisateurs (membres de la société)

#### URL : `POST /utilisateurs`

Le profil ADMIN peut :

- Inviter des utilisateurs à rejoindre la société
- Leur attribuer un **profil utilisateur** (LECTEUR, MEMBRE...)

Champs requis :

- `nom`, `email`, `mot_de_passe`, `profil_utilisateur`

---

### 📅 6. Enregistrement d'une opération

#### URL : `POST /operations`

Champs requis :

- `date_operation`
- `intitule`
- `montant`
- `type` (`DEPENSE`, `RECETTE`)
- `natures_id`
- `responsables_id` (créable via `/responsables`)
- `url_fichier` (facultatif)

---

### 🔎 7. Consultation, recherche et filtrage

#### URL : `GET /operations?type=DEPENSE&date_debut=2024-01-01&date_fin=2024-12-31`

L'administrateur peut :

- Filtrer par date, type, nature
- Accéder aux détails d'une opération

---

### 🛋️ 8. Ajout de nature ou de responsable

- `POST /natures` pour créer une nouvelle nature d'opération
- `POST /responsables` pour enregistrer un acteur lié à une opération (ex : plombier)

---

### ❌ 9. Déconnexion

#### Frontend uniquement

- Supprimer le JWT Token du stockage local (localStorage ou cookie)
- Rediriger vers la page de connexion

---

## 🔹 Endpoints Backend requis (par module)

### 🌐 Authentification

- `POST /auth/register` : Inscription
- `POST /auth/login` : Connexion
- `POST /auth/logout` (optionnel si token-side uniquement)

### 🏢 Sociétés

- `POST /societes` : Créer une société
- `GET /societes/{id}` : Voir les détails
- `PUT /societes/{id}` : Modifier
- `DELETE /societes/{id}` : Supprimer

### 👤 Utilisateurs

- `POST /utilisateurs` : Créer/inviter un utilisateur
- `GET /utilisateurs` : Lister les utilisateurs de la société
- `GET /utilisateurs/{id}` : Voir les détails d'un utilisateur
- `PUT /utilisateurs/{id}` : Modifier un utilisateur
- `DELETE /utilisateurs/{id}` : Supprimer

### 📋 Profils utilisateurs (rôles)

- `GET /profils` : Lister les rôles disponibles (ADMIN, LECTEUR...)

### 📈 Opérations

- `POST /operations` : Créer une opération
- `GET /operations` : Liste avec filtres
- `GET /operations/{id}` : Détail d'une opération
- `PUT /operations/{id}` : Modifier
- `DELETE /operations/{id}` : Supprimer

### 🛋️ Natures d'opérations

- `POST /natures`
- `GET /natures`
- `PUT /natures/{id}`
- `DELETE /natures/{id}`

### 👨‍💼 Responsables

- `POST /responsables`
- `GET /responsables`
- `PUT /responsables/{id}`
- `DELETE /responsables/{id}`

### ⚖️ Statistiques & dashboard

- `GET /dashboard/solde`
- `GET /dashboard/graphique-par-nature`
- `GET /dashboard/repartition-type`

---

## ✉️ Conclusion

Ce scénario assure une prise en main fluide pour un **utilisateur administrateur** de Kobbo. 
Il suit un chemin clair de l'inscription à la gestion quotidienne de ses opérations, en passant 
par la constitution de son espace de travail (société, membres, natures, etc.).

La liste des endpoints sert de référence technique pour le développeur backend 
et garantit une couverture fonctionnelle exhaustive pour le profil Administrateur.

