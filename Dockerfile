# --- STAGE 1: Build Stage ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- STAGE 2: Run Stage ---
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/arka_backend-0.0.1-SNAPSHOT.jar arka.jar

# Dynamic port binding for Render
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "arka.jar"]