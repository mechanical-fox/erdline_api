

# Projet

Ce projet contient le code de la partie backend / serveur, du site Erdline. Ce site à pour but de permettre
la création de jeux de type Visual Novel. Afin d'aider à pouvoir plus facilement tester une idée de Visual
Novel, des sprites de base sont fournis, et il est utilisé comme décors de simples dégradés de couleurs.

Les fonctionnalités actuelles sont les suivantes:
- Sauvegarde via le système de session
- Possibilité de créer des Visual Novels de type Kinetic
- Décors gérés sous formes de dégradés de couleurs
- Gestion des Sprites des personnages 
- Page Exemple
- Page A propos


# Utilisation du mode HTTPS

Cette API fonctionne actuellement en HTTP, et non en HTTPS y compris en production. Cela est une modification
effectuée, car après test un portfolio sous forme de site internet rencontre assez peu de succès. Et un portfolio
Github a donc été préféré.

Vous pouvez néanmoins activer le mode HTPPS en mettant le paramètre server.ssl.enabled à true au lieu de false.
De plus, actuellement le certificat SSL utilisé avec le protocole HTTPS, et stocké à keystore/cert.p12 est un
certificat auto-signé. Ce qui est utilisé en développement. Mais cela a le soucis de faire afficher des messages
d'erreurs en navigateur client, et de forcer l'utilisateur à accepter le risque de sécurité.

Donc si vous souhaitez effectuer un déploiement en HTTPS, et non en HTTP, ne pas oublier de remplacer 
keystore/cert.p12 par un certificat valide. Pour les propriétés à utiliser pour le certificat voir le fichier
suivant
     
[src/main/resources/application.yml](./src/main/resources/application.yml)      


# Execution   


Vous pouvez executer l'API en http en utilisant une base de donnée localhost avec le profil defaut.
Une base de donnée devra tourner sur votre ordinateur en port 5432, ou bien le programme s'arretera avec une
erreur de connexion.

```sh
mvn spring-boot:run
```

Vous pouvez ensuite vérifier que l'API fonctionne en vous connectant au swagger.

http://localhost:8081/swagger-ui/index.html  


# Tests unitaires    

Vous pouvez lancer les tests unitaire, puis vérifier le taux de coverage avec la commande suivante. La commande
verify est configuré pour échoué si le taux de couverture de code est inférieur à 70%

```sh
mvn verify
```

Après les tests, un rapport html avec la couverture de test sera alors crée à l'emplacement suivant

**target/site/jacoco/index.html**    


# Configuration Administrateur

Il existe des sessions Admin, et des sessions non Admin. L'API ne permet cependant pas de créer de session Admin. Ni 
de modifier une session déjà existante. Pour des raisons de sécurité, il a été choisit de ne permettre la création 
d'un administrateur, que depuis la base de donnée.


Pour créer une session administrateur:
- Créer une session avec l'API, ou l'interface graphique
- Se connecter à la base de donnée
- En table REGISTERED_SESSION, mettre le paramètre "is_admin" de la session créée à true


# Déploiement


## Etape 1: Création de la base de donnée   

Créez une base de donnée PostgreSQL, via le projet commun_database qui est également disponible sur mon Github.
Faites attention que la base de donnée devra être déployée en port 5432, avec l'utilisateur tora, et le mot de
passe password. Mot de passe que vous pourre changer après.

Ensuite lancez cette API en mode développement, qui est un mode autorisant la création et modification des tables
en base de donnée. Vous aurez alors une base de donnée, avec toutes les tables nécessaires, et il vous faudra
juste utiliser un peu le site internet, afin de remplir la base de donnée. 


## Etape 2: Création de l'image docker   

Une fois la base de donnée prête, vous pouvez ensuite construire l'image docker avec la commande suivante.

```sh
docker build -t app  .
```

## Etape 3: Execution de l'image docker   

Une fois l'image docker crée, vous pouvez maintenant la démarrer avec la commande suivante. Faites attention à changer
le mots de passe pour DATABASE_PASSWORD. Et à ne pas laisser celui-ci à "password". Si nécessaire, le projet commun_database
utilisé pour créer la base de donnée, mentionne comment changer le mot de passe de celle-ci.
 
DATABASE_PASSWORD : mot de passe utilisé par la base de donnée.   

```sh
docker run -d --name capp  -p 8081:8081 -e DATABASE_PASSWORD=password app
```

Vérifiez alors que vous puissez vous connecter au swagger en production. Actuellement, le swagger de production est configuré
pour démarrer en localhost. Vous pouvez donc vérifier le swagger via cet page internet.

http://localhost:8081/swagger-ui/index.html 
