# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/arka_backend-0.0.1-SNAPSHOT.jar arka.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "arka.jar"]