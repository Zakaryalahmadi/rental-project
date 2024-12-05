Rental API
Rental API est une application RESTful qui permet de gérer des utilisateurs, des locations, et des messages, avec une intégration JWT pour l'authentification et Cloudinary pour la gestion des fichiers.

Fonctionnalités principales
Gestion des utilisateurs : Inscription, connexion, récupération des informations de l'utilisateur.
Gestion des locations : Création, mise à jour, récupération des détails des propriétés.
Gestion des messages : Création et récupération des messages pour des propriétés spécifiques.
Authentification JWT : Sécurisation des endpoints via des tokens JWT.
Intégration avec Cloudinary : Gestion et hébergement des fichiers (images).
Prérequis
Java 17 ou version ultérieure
Maven ou Gradle
Un compte Cloudinary pour gérer les fichiers
Une base de données relationnelle
Installation et configuration
1. Cloner le projet

`git clone https://github.com/votre-repo/rental-api.git`

2. Créer un fichier `.env`
Le projet nécessite un fichier .env pour configurer les variables d'environnement sensibles. Vous pouvez vous baser sur l'exemple fourni dans le fichier `.env.example.`

Exemple de fichier .env

`CLOUDINARY_URL`=your_cloudinary_url_here
`SECRET`=your_jwt_secret_here
`CLOUDINARY_URL` : URL complète de votre compte Cloudinary. Vous pouvez l'obtenir depuis votre tableau de bord Cloudinary.
`SECRET` : Secret utilisé pour signer et vérifier les tokens JWT. Assurez-vous de définir une valeur forte et sécurisée.
3. Installer les dépendances
Utilisez Maven ou Gradle pour télécharger les dépendances du projet.

Avec Maven :
bash
Copier le code
mvn clean install
Avec Gradle :
bash
Copier le code
./gradlew build
4. Configurer la base de données
Configurez votre base de données dans le fichier application.properties ou application.yml (par défaut dans src/main/resources).

Exemple de configuration pour une base PostgreSQL :

properties
Copier le code
spring.datasource.url=jdbc:postgresql://localhost:5432/rental_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
5. Lancer l'application
Utilisez la commande suivante pour démarrer l'application :

mvn spring-boot:run
L'application sera accessible par défaut sur http://localhost:3001.

Documentation API
L'application utilise Swagger pour documenter l'API. Une fois l'application démarrée, accédez à Swagger UI à l'URL suivante :


http://localhost:3001/swagger-ui
Vous y trouverez tous les endpoints avec des descriptions et des exemples.

Structure du projet
`controllers` : Contient les classes pour gérer les endpoints REST.
`services` : Contient la logique métier de l'application.
`repositories` : Contient les interfaces pour l'accès aux données.
`entities` : Contient les entités JPA.
`dto` : Contient les classes pour le transfert de données entre les couches.
