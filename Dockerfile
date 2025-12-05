
FROM maven:4.0.0-rc-4-eclipse-temurin-25-noble

ADD  .  /app/
WORKDIR /app
EXPOSE 8080
ENV DATABASE_PASSWORD=$DATABASE_PASSWORD
ENV ERDLINE_PASSWORD=$ERDLINE_PASSWORD

RUN mvn package -DskipTests

CMD ["java", "-jar", "target/app.jar", "--spring.profiles.active=prod","--spring.datasource.password=${DATABASE_PASSWORD}"]
