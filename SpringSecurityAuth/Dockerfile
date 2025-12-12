# Use Eclipse Temurin OpenJDK 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom files
COPY SpringSecurityAuth/mvnw .
COPY SpringSecurityAuth/pom.xml .
COPY SpringSecurityAuth/.mvn .mvn

# Copy source code
COPY SpringSecurityAuth/src ./src

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Build the Spring Boot app (skip tests)
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot port
EXPOSE 8888

# Run the app
CMD ["java", "-jar", "target/SpringSecurityAuth-0.0.1-SNAPSHOT.jar"]
