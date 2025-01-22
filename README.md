# TP Programmation Avancée : Détection et Mesure de Similarité entre Textes

Ce projet vise à fournir une application permettant de détecter et de mesurer les similarités entre deux textes (ouvrages ou articles). L'objectif principal est d'assurer une analyse rapide et précise, aidant ainsi une maison d'édition à se prémunir contre les plaintes pour plagiat.

## Fonctionnalités
- Analyse rapide des similarités entre deux textes.
- Implémentation de deux algorithmes de détection :
  1. **Winnowing** : une méthode populaire pour détecter le plagiat basée sur des empreintes digitales de texte. Nous sommes partis de cet algorithme pour créer le nôtre.
  2. **Algorithme développé par l'équipe** : une solution sur mesure pour optimiser la détection des similarités.

## Prérequis
- Java 8 ou version ultérieure installée.
- Un terminal ou un IDE pour compiler et exécuter les fichiers Java.

---

## Installation et Lancement de l'Application

1. **Télécharger l'application** :
   - Extraire l'archive ZIP depuis GitHub.
   - Ou cloner le projet via GitHub :
     ```bash
     git clone <lien_du_projet>
     ```

2. **Accéder au répertoire** :
   Ouvrez un terminal et placez-vous dans le répertoire du projet :
   ```bash
   cd chemin/vers/le/répertoire
   ```

3. **Lancer l'application** :
   ```bash
   ./run.bat
   ```
    ```bash
   ./run.sh
   ```
---

## Architecture de l'Application

L'application suit le modèle architectural **MVC (Modèle-Vue-Contrôleur)**, qui sépare la logique en trois composants distincts pour une meilleure organisation et maintenabilité.

### 1. **Modèle (Model)**
Le modèle contient les algorithmes principaux pour la détection de plagiat.
- **`WinnowingWords.java`** : implémentation de l'algorithme Winnowing.
- **`FaisMain.java`** : algorithme développé par l'équipe pour une détection optimale.

### 2. **Vue (View)**
Le composant Vue gère l'affichage des résultats pour l'utilisateur.
- **`View.java`** : gère les interactions utilisateur et la présentation des résultats.

### 3. **Contrôleur (Controller)**
Le contrôleur coordonne les actions entre la Vue et le Modèle.
- **`Controller.java`** : récupère les données d'entrée de l'utilisateur, lance les calculs dans le modèle et transmet les résultats à la vue.

### 4. **Point d'Entrée**
- **`Main.java`** : point d’entrée principal pour l’exécution de l’application.

---

## Algorithmes Implémentés

1. **Winnowing**
   - Utilise des empreintes digitales (fingerprinting) pour identifier les similarités textuelles.
   - Avantages : rapide et efficace pour des textes volumineux.

2. **Algorithme FaisMain**
   - Conçu sur mesure par notre équipe pour améliorer la précision de détection des similarités.
   - Complémentaire à Winnowing pour une détection plus approfondie.

---

## Contributeurs
Ce projet a été réalisé dans le cadre d'un TP de synthèse en Programmation Avancée. Nous remercions l'ensemble de l'équipe pour leur implication dans le développement et les tests de cette application.

---

## Licence
Ce projet est distribué sous la licence MIT. Vous êtes libre de le modifier et de le redistribuer tout en respectant les termes de cette licence.

---

Pour toute question ou contribution, n'hésitez pas à ouvrir une issue sur le dépôt GitHub ou à nous contacter par email. Bonne utilisation !

