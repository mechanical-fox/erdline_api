
FROM maven:4.0.0-rc-4-eclipse-temurin-25-noble

# Ajoute les fichiers/code source de "." à /app dans l'application
ADD  .  /app/
WORKDIR /app

# Construit les fichiers web à servir (index.html, css, js...)
RUN mvn package

# Indique les ports à publier par docker (fonction de documentation)
EXPOSE 8080

CMD ["java", "-jar", "target/app.jar", "--spring.profiles.active=prod"]
