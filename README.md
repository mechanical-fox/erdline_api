

# Projet

API qui assure les fonctions suivantes:    
- Création d'une documentation API depuis la description des endpoints format json    
- Génération de la documentation API en format Html    
- Genere de documentations temporaires (Ex validité: 180s)   
- Est capable de conserver des exemples de documentation déjà généré   


Ce projet est lié au site internet Erdline, qui a pour but de permettre de créer une    
documentation API de façon graphique. Et non en apprenant à coder avec le langage    
OpenAI.    

Il est prévu dans le futur de pouvoir à la fois exporter en format OpenAI, et importer   
en format OpenAI. Cela permettra aussi aux utilisateurs de pouvoir ainsi sauvegarder   
les documentations d'API déjà crée.   


# Changement de Certificat SSL

Actuellement le certificat SSL utilisé à keystore/cert.p12 est un certificat auto-signé. Ce qui est utilisé    
en développement, car il s'agit de la seule possibilité pour lancer un serveur https localhost. Mais cela à    
le soucis de faire afficher des messages d'erreurs dans le navigateur client, et de forcer l'utilisateur à    
accepter le risque de sécurité.    

Donc ne pas oublier lors du déploiement de remplacer keystore/cert.p12 par un certificat valide.       
Pour les propriétés à utiliser pour le certificat voir les propriétés déclarés dans   
[src/main/resources/application.yml](./src/main/resources/application.yml)      


# Execution   

Avant de lancer l'application, veuillez définir le mot de passe à utiliser pour l'API pour protéger les fonctionnalités admin.    
Sinon au démarrage l'API affichera une erreur, en l'absence de la déclaration de variable d'environment adéquate.   

Version windows(powershell):

```sh
$Env:ERDLINE_PASSWORD="password"
```

Vous pouvez ensuite lancer l'application avec le profil default, ou le profil prod.   
Le profil default lance l'API en http, avec une base de donnée en localhost.    
Le profil prod lance l'API en https, avec la base de donnée de production.    

```sh
mvn spring-boot:run "-Dspring.profiles.active=default"
```

Vous pouvez ensuite vérifier que l'API fonctionne en vous connectant au swagger.    

**Lien http (profil default):** http://localhost:8080/swagger-ui/index.html  

**Lien https (profil prod):**  https://localhost:8080/swagger-ui/index.html   


# Tests unitaires    

Vous pouvez lancer les tests unitaire + vérifier le taux de coverage avec la commande suivante.   
La commande verify est configuré pour échoué si taux coverage < 70%.     

```sh
mvn verify
```

Après les tests, un rapport html avec la couverture de test sera alors crée à l'emplacement suivant    
**target/site/jacoco/index.html**    


# Déploiement Docker

Si vous souhaitez utiliser une image docker. Vous pouvez construire l'image docker avec la commande suivante.      
Faites attention à modifier le numéro de version, selon la version de votre ihm.       


```sh
docker build -t erdline_1_0  .
```

Le lancement en interactif pour tester avant déploiement se fait avec la commande suivante. En production, le flag     
-it devra être remplacé par -d, et --name. Faites attention à remplacer **password** par le mot de passe à     
utiliser pour les fonctionnalitées admin pour votre API.     

```sh
docker run -it -e ERDLINE_PASSWORD=password  -p 8080:8080 erdline_1_0
```

Vérifiez alors que vous puissez vous connecter au swagger

https://localhost:8080/swagger-ui/index.html


# Déploiement jar

Si vous utilisez un serveur VPS qui est limité en place, utiliser des jar sera plus indiqué que utiliser des   
images docker. Vous pouvez compter environ 900 Mo par image docker, contre 25Mo par jar. Sans oublier la     
taille des container docker, ainsi que le fait que l'on peut oublier de nettoyer le registre image.    

Voici donc comment réaliser un déployement avec un jar. 

**Ordinateur client:**

1. Créez le jar avec

```sh
mvn package
```

2. Dans api_1_0.zip mettre le jar + src/main/resources + keystore, qui sont utilisés par le programme

3. Envoyez le fichier package.zip en serveur 
Vous devrez remplacer 185.220.205.57 par l'ip de votre serveur.    
Vous devrez remplacer /root/app/database/Dockerfile par la destination voulu sur votre serveur.     

```sh
scp api_1_0.zip root@185.220.205.57:/root/app/api/api_1_0.zip
```

**Serveur:**  


1. Si cela n'est pas déjà fait, veuillez définir la variable ERDLINE_PASSWORD qui sera utilisé pour l'authentification       
aux fonctionnalités admin de l'API. Veuillez à changer le mot de passe, par le mot de passe choisit en production.    

```sh
export ERDLINE_PASSWORD="password" 
```

2. Dezippez le projet obtenu

```sh
cd /root/app/api
unzip api_1_0.zip -d api_1_0
```

3. Lancez le serveur avec la commande suivante. N'oubliez pas auparavant d'avoir éteint la précédente commande java.    
Donc du ss -tlnp, puis un kill. La commande java utilisée ici a pour but de s'executer en arrière plan, et d'envoyer    
les messages de l'application dans log.txt, qui pourra ensuite être servit avec Graphana.       

```sh
cd api_1_0
java -jar app.jar --spring.profiles.active=prod >>log.txt 2>>log.txt &
```

4. Vérifiez via un navigateur internet que l'API fonctionne en vous connectant au swagger.  
Voici un exemple d'url à vérifier, mais en remplacant --nom--domaine-- par votre nom de domaine.   
**https://--nom--domaine--:8080/swagger-ui/index.html**  

