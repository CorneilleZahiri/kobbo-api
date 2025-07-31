# 💰 Kobbo - Gestion des Dépenses et Recettes Multi-Sociétés

Kobbo est une application de gestion des finances personnelles et professionnelles permettant à
un utilisateur de gérer ses dépenses et recettes dans plusieurs sociétés à partir d’un seul compte.

---

## 📌 Fonctionnalités principales

- Gestion multi-sociétés avec isolation logique des données
- Un utilisateur unique peut avoir plusieurs comptes dans différentes sociétés
- Définition de rôles personnalisés par société
- Suivi des opérations (dépenses / recettes)
- Catégorisation des opérations par nature
- Gestion des tiers (clients, fournisseurs, etc.)
- Pièces justificatives (upload de fichiers via URL)

---

## 🧱 Modèle de données relationnel

L'application repose sur une base de données MySQL structurée comme suit :

### Tables et relations :

- **`societes`** : Entités indépendantes représentant des entreprises.
- **`utilisateurs`** : Comptes d'accès individuels, uniques et globaux.
- **`roles`** : Définis par chaque société, utilisés pour les autorisations.
- **`comptes_societe`** : Lien entre utilisateur, rôle et société.
- **`tiers`** : Clients, fournisseurs, ou autres entités externes associées à des opérations.
- **`natures`** : Catégories d'opérations personnalisables par utilisateur-société.
- **`operations`** : Représentent les transactions financières (recettes ou dépenses).

📊 Un diagramme UML est fourni pour visualiser les relations entre les entités :

![Diagramme - Kobbo](Diagramme_kobbo.png)

---

## ⚙️ Technologies utilisées

- **Langage** : SQL / Java / Spring Boot
- **Base de données** : MySQL
- **Sécurité** : UUID, contraintes d'intégrité, authentification (JWT)
- **Frontend** : Libre choix (Angular, React, etc.)

---

## 📝 Processus de création de compte et d’authentification

La création de compte sur la plateforme suit un processus bien défini garantissant la cohérence des données en base et
une expérience utilisateur fluide.

### 🧾 Étapes de création de compte

Accès au formulaire d'inscription :
L'utilisateur clique sur le bouton Créer un compte ou Inscription sur la page d'accueil.

Remplissage du formulaire :
Le formulaire comprend les champs suivants :

Société : nom ou raison sociale de la société à créer

Nom de l’utilisateur

Email

Mot de passe

Rôle : automatiquement fixé à ADMINISTRATEUR (non modifiable)

Enregistrement en base :
Une transaction unique est déclenchée pour enregistrer les données dans l’ordre suivant :

Table societes

Table roles

Table utilisateurs

Table comptes_societe (associant l’utilisateur, le rôle et la société)

Redirection vers la page de connexion :
Une fois l’inscription réussie, l’utilisateur est invité à se connecter via un formulaire de connexion.

### 🔐 Authentification

L’utilisateur saisit son email et mot de passe.

Si les identifiants sont valides, un JWT (JSON Web Token) est généré et retourné dans la réponse.

Une fois authentifié, l'utilisateur est redirigé vers une page affichant la liste de ses sociétés.

### 🏢 Sélection de la société et navigation

L'utilisateur sélectionne une société pour définir le contexte d’exécution.

Une fois la société choisie, le menu principal est affiché, donnant accès aux différentes fonctionnalités (gestion des
utilisateurs, rôles, transactions, etc.).

### ➕ Création de nouvelles sociétés (post-inscription)

Un utilisateur authentifié peut créer de nouvelles sociétés (si son rôle le permet).

Pour chaque nouvelle société créée, un nouveau lien est établi dans la table comptes_societe.

# 📋 Endpoints REST - Kobbo API

| Méthode | Endpoint                             | Description                               |
|---------|--------------------------------------|-------------------------------------------|
| POST    | `/api/auth/register`                 | Créer un compte utilisateur avec société  |
| POST    | `/api/auth/login`                    | Connexion utilisateur, renvoie un JWT     |
| GET     | `/api/societes`                      | Lister les sociétés d’un utilisateur      |
| POST    | `/api/societes`                      | Créer une société                         |
| GET     | `/api/societes/{id}/utilisateurs`    | Liste des utilisateurs d’une société      |
| POST    | `/api/societes/{id}/utilisateurs`    | Ajouter un utilisateur à une société      |
| GET     | `/api/societes/{id}/roles`           | Lister les rôles disponibles              |
| POST    | `/api/societes/{id}/roles`           | Ajouter un rôle à la société              |
| GET     | `/api/affectations?societeId={uuid}` | Liste des affectations d’une société      |
| POST    | `/api/affectations`                  | Créer une affectation utilisateur-société |

## 🚀 Installation

### 🔧 Prérequis

- Java 17+
- MySQL 8+
- Maven / Gradle
- (Optionnel) Docker & Docker Compose

### 📥 Étapes

1. Clonez le projet :

```bash
git clone https://github.com/votre-utilisateur/kobbo.git
cd kobbo
```

### 🤝 Contribution

Les contributions sont les bienvenues !

Forkez le projet

Créez une branche (feature/ma-fonctionnalite)

Commitez vos modifications (git commit -am 'Ajout nouvelle fonctionnalité')

Pushez (git push origin feature/ma-fonctionnalite)

Ouvrez une Pull Request

### 👨‍💻 Auteur

Développé avec ❤️ par Corneille GBEAGA.
Contact : cgbeaga@gmail.com

---
