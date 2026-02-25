# --- STAGE 1: Build Stage ---
# Use Maven with Java 21 to compile the application
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY . .

# Build the application and skip tests to speed up the deployment
# This generates the .jar file in the /app/target directory
RUN mvn clean package -DskipTests

# --- STAGE 2: Run Stage ---
# Use a slim Java 21 Runtime image for the final container
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy only the compiled .jar file from the build stage
# Ensure the filename matches the artifactId and version in your pom.xml
COPY --from=build /app/target/arka_backend-0.0.1-SNAPSHOT.jar arka.jar

# While Render ignores EXPOSE, it's good practice for documentation
EXPOSE 8080

# The Exec Form ENTRYPOINT is critical for Render
# It allows Java to pick up the dynamic $PORT environment variable
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "arka.jar"]