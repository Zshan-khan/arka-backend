package com.arka.arka_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArkaBackendApplication {

    public static void main(String[] args) {

        // Load .env file
        Dotenv dotenv = Dotenv.load();

        // Set DB environment variables
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        // Set AWS environment variables
        System.setProperty("AWS_ACCESS_KEY", dotenv.get("AWS_ACCESS_KEY"));
        System.setProperty("AWS_SECRET_KEY", dotenv.get("AWS_SECRET_KEY"));
        System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
        System.setProperty("AWS_BUCKET", dotenv.get("AWS_BUCKET"));

        // Start Spring Boot
        SpringApplication.run(ArkaBackendApplication.class, args);
    }
}