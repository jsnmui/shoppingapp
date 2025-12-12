# Use Eclipse Temurin OpenJDK 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom files from the subfolder
COPY shopping-app-content/mvnw .
COPY shopping-app-content/pom.xml .
COPY shopping-app-content/.mvn .mvn

# Copy source code
COPY shopping-app-content/src ./src

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Build the Spring Boot app (skip tests)
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot default port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/shopping-app-0.0.1-SNAPSHOT.jar"]
