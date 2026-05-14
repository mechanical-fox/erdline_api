
# TO DO

Voir partie IHM

# Projet

TO DO


# Changement de Certificat SSL

Actuellement le certificat SSL utilisé à keystore/cert.p12 est un certificat auto-signé. Ce qui est utilisé
en développement. Mais cela a le soucis de faire afficher des messages d'erreurs en navigateur client, et de
forcer l'utilisateur à accepter le risque de sécurité.

Donc ne pas oublier lors du déploiement de remplacer keystore/cert.p12 par un certificat valide. Pour les
propriétés à utiliser pour le certificat voir le fichier suivant
     
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



# Déploiement


## Etape 1: Initialiser la base de donnée   

Créez la structure de la base de donnée avec le script suivant. Celui ci créera les tables, et index
nécessaires.

[script/init.sql](./script/init.sql)


## Etape 2: Modifier le certificat SSL  

Actuellement le certificat SSL utilisé à keystore/cert.p12 est un certificat auto-signé. Ce qui est utilisé
en développement. Mais cela a le soucis de faire afficher des messages d'erreurs en navigateur client, et de
forcer l'utilisateur à accepter le risque de sécurité.

Donc avant de déployer, il va falloir remplacer keystore/cert.p12 par un certificat valide. Pour les 
propriétés à utiliser pour le certificat voir le fichier suivant
     
[src/main/resources/application.yml](./src/main/resources/application.yml)  


## Etape 3: Création de l'image docker   

Une fois le certificat SSL mis à jour, vous pouvez ensuite construire l'image docker avec la commande suivante.
Faites attention à modifier le numéro de version, selon la version de l'application.    


```sh
docker build -t app_1_1  .
```

## Etape 4: Execution de l'image docker   

Une fois l'image docker crée, vous pouvez maintenant la démarrer avec la commande suivante. Faites attention à changer
le mots de passe pour DATABASE_PASSWORD. Et à ne pas laisser celui-ci à "password".
 
DATABASE_PASSWORD : mot de passe utilisé par la base de donnée.   

```sh
docker run -d --name capp_1_1  -p 8081:8081 -e DATABASE_PASSWORD=password  app_1_1
```

Vérifiez alors que vous puissez vous connecter au swagger en production.

Pour un déploiement vers erdline.com comme actuellement, l'url du swagger est donc

https://erdline.com:8081/swagger-ui/index.html    
