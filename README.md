

# Projet

API qui assure les fonctions suivantes:     
- Création de documentation API    
- Génération de la documentation API en format Html    
- Enregistrement des exemples de documentation déjà généré   

Cette API est liée au site internet Erdline, qui a pour but de permettre de créer une    
documentation API de façon graphique. Et non en apprenant à coder avec le langage    
OpenAI.    


# Changement de Certificat SSL

Actuellement le certificat SSL utilisé à keystore/cert.p12 est un certificat auto-signé.    
Ce qui est utilisé en développement. Mais cela a le soucis de faire afficher des messages     
d'erreurs en navigateur client, et de forcer l'utilisateur à accepter le risque de sécurité.    

Donc ne pas oublier lors du déploiement de remplacer keystore/cert.p12 par un certificat      
valide. Pour les propriétés à utiliser pour le certificat voir le fichier suivant    
     
[src/main/resources/application.yml](./src/main/resources/application.yml)      


# Execution   

Avant de lancer l'application, veuillez définir le mot de passe à utiliser pour l'API      
pour protéger les fonctionnalités admin. Sinon au démarrage le programme affichera une     
erreur, en l'absence de la déclaration de variable d'environment adéquate.    

Version windows(powershell):    

```sh
$Env:ERDLINE_PASSWORD="password"
```

Vous pouvez ensuite lancer l'API en http en utilisant une base de donnée localhost avec    
le profil defaut. Une base de donnée devra tourner sur votre ordinateur en port 5432, ou   
bien le programme s'arretera avec une erreur de connexion.   

```sh
mvn spring-boot:run
```

Vous pouvez aussi lancer l'API en https en utilisant la base de donnée de production avec    
le profil prod. Vous devrez remplacer password par le mot de passe utilisé par la base de    
donnée en production. A défaut, le programme s'arretera avec une erreur de connexion.   

```sh
mvn spring-boot:run "-Dspring.profiles.active=prod" "-Dspring.datasource.password=password"
```

Vous pouvez ensuite vérifier que l'API fonctionne en vous connectant au swagger.    

**Lien http:** http://localhost:8080/swagger-ui/index.html  

**Lien https:**  https://localhost:8080/swagger-ui/index.html   


# Tests unitaires    

Vous pouvez lancer les tests unitaire + vérifier le taux de coverage avec la commande suivante.   
La commande verify est configuré pour échoué si taux coverage < 70%.     

```sh
mvn verify
```

Après les tests, un rapport html avec la couverture de test sera alors crée à l'emplacement suivant    
**target/site/jacoco/index.html**    


# Déploiement

Si vous souhaitez réaliser un déploiement. La première chose est sur le serveur servant au build de définir    
les variables d'environnement ERDLINE_PASSWORD et DATABASE_PASSWORD. Et cela avant le build. En n'oubliant     
pas de remplacer les mots de passe, par ceux choisis.   

ERDLINE_PASSWORD définit un mot de passe demandé par l'API pour les opérations sensibles.   
DATABASE_PASSWORD correspond au mot de passe utilisé par la base de donnée.   

Version Linux:

```sh
export ERDLINE_PASSWORD=first_password
export DATABASE_PASSWORD=second_password
```

Une fois les mots de passe déclarés, vous pouvez ensuite construire l'image docker avec la commande suivante.      
Faites attention à modifier le numéro de version, selon la version de l'application.       


```sh
docker build -t app_1_0  .
```

Et voici maintenant la commande pour démarrer le serveur.   

```sh
docker run -d --name capp_1_0  -p 8080:8080 app_1_0
```

Vérifiez alors que vous puissez vous connecter au swagger en production.    
Pour le swagger, lorsqu'une url necessite un mot de passe, il s'agira du mot de passe ERDLINE_PASSWORD    
que vous avez dû définir durant le build de l'image docker.   

Pour un déploiement vers www.erdline.com comme actuellement, l'url du swagger est donc    
https://www.erdline.com:8080/swagger-ui/index.html    
