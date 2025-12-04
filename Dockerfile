
FROM maven:4.0.0-rc-4-eclipse-temurin-25-noble

ADD  .  /app/
WORKDIR /app
EXPOSE 8080

RUN mvn package

# delete the maven cache : to have docker image with little size in Mo
RUN mvn dependency:purge-local-repository 

CMD ["java", "-jar", "target/app.jar", "--spring.profiles.active=prod"]
