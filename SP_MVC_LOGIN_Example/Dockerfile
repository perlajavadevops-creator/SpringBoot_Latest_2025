# Use JDK 24 base image
FROM eclipse-temurin:24-jdk

# Metadata
LABEL maintainer="perla@example.com"

# Expose port for Spring Boot
EXPOSE 8080

# Copy the built JAR file
COPY target/SP_MVC_LOGIN_Example-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]