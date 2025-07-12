
# 💰 Kobbo - Gestion des Dépenses et Recettes

**Kobbo** est une application **SaaS open source** de gestion de budget (recettes & dépenses), conçue pour les **particuliers**, **indépendants** et **PME**. Elle permet une gestion collaborative, sécurisée et multi-sociétés des opérations financières.

---

## 🚀 Fonctionnalités

- 🔐 Authentification sécurisée (JWT)
- 🏢 Création et gestion de sociétés
- 👥 Gestion des utilisateurs avec rôles
- 💳 Enregistrement de dépenses et recettes
- 📂 Ajout de justificatifs (reçus, factures)
- 🏷️ Catégorisation des opérations par nature
- 🧾 Attribution à un responsable réel de l’opération
- 📊 Statistiques et synthèses financières

---

## 🧱 Modèle de données (MCD simplifié)

### Utilisateur (`UUID`)
- nom
- email
- motDePasse (hashé)
- profil_id → ProfilUtilisateur
- societe_id → Société

### ProfilUtilisateur (`LONG`)
- libelle : "ADMIN", "LECTEUR", etc.
- description

### Société (`UUID`)
- raison_sociale
- email
- adresse

### Nature (`LONG`)
- intitulé : "Salaire", "Transport", etc.

### Opération (`UUID`)
- date
- intitule
- montant
- type : DEPENSE | RECETTE
- nature_id → Nature
- utilisateur_id → Utilisateur (créateur)
- responsable_id → Responsable (réel)
- justificatif : fichier ou URL

### Responsable (`UUID`)
- nom
- email (optionnel)
- fonction : "Plombier", "Fournisseur"

---

## 📦 Technologies utilisées

- **Backend** : Spring Boot, Spring Security, JPA
- **Base de données** : PostgreSQL ou MySQL
- **Auth** : JWT
- **Stockage fichiers** : local ou cloud (Cloudinary, AWS S3…)

---

## 👨‍💻 Scénario d’utilisation

1. L'utilisateur s’inscrit (`/register`)
2. À la première connexion, il crée sa **société**
3. Il est automatiquement **ADMIN** de sa société
4. Il peut :
    - Créer des **natures** d’opérations
    - Ajouter des **utilisateurs** à sa société
    - Enregistrer ses **opérations financières**
    - Associer un **responsable** réel (ex : fournisseur)
    - Ajouter un **justificatif** à l’opération
5. Il peut consulter des **statistiques financières**

---

## 📚 API REST – Endpoints

### 🔐 Authentification
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `POST /api/auth/logout`

### 🏢 Société
- `POST /api/societes`
- `GET /api/societes/{id}`
- `PUT /api/societes/{id}`
- `DELETE /api/societes/{id}`
- `GET /api/societes/{id}/utilisateurs`
- `POST /api/societes/{id}/inviter`

### 👤 Utilisateurs
- `GET /api/utilisateurs/{id}`
- `PUT /api/utilisateurs/{id}`
- `DELETE /api/utilisateurs/{id}`

### 🛡️ Profils
- `GET /api/profils`
- `POST /api/profils`
- `PUT /api/profils/{id}`
- `DELETE /api/profils/{id}`

### 💼 Natures
- `GET /api/natures`
- `POST /api/natures`
- `PUT /api/natures/{id}`
- `DELETE /api/natures/{id}`

### 💳 Opérations
- `GET /api/operations`
- `GET /api/operations/{id}`
- `POST /api/operations`
- `PUT /api/operations/{id}`
- `DELETE /api/operations/{id}`

### 👥 Responsables
- `GET /api/responsables`
- `POST /api/responsables`
- `PUT /api/responsables/{id}`
- `DELETE /api/responsables/{id}`

### 📊 Statistiques
- `GET /api/statistiques/societe/{id}`
- `GET /api/statistiques/utilisateur/{id}`
- `GET /api/statistiques/par-nature`
- `GET /api/statistiques/par-type?type=DEPENSE|RECETTE`

---

## 🆔 Types d’identifiants

| Entité            | Type   |
|-------------------|--------|
| Utilisateur       | UUID   |
| ProfilUtilisateur | LONG   |
| Société           | UUID   |
| Opération         | UUID   |
| Nature            | LONG   |
| Responsable       | UUID   |

---

## 🛡️ Sécurité

- Authentification via JWT
- Middleware pour vérifier les droits par profil
- Hashage des mots de passe (BCrypt)

---

## 🤝 Contribuer

Le projet est open source et ouvert aux contributions :
- Fork
- Pull Request
- Signalement de bugs ou propositions via les Issues

---

## 🧾 Licence

MIT – libre d’utilisation, modification et distribution.

---

## 📫 Contact

Tu peux me contacter via GitHub ou m’envoyer un message pour toute contribution ou question.

---
