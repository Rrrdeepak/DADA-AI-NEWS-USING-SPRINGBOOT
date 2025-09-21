# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/daily-news-app-1.0.0.jar app.jar

# Expose port 8080
EXPOSE 8080

# Set environment variables
ENV NEWS_API_KEY=${NEWS_API_KEY}
ENV NEWS_API_URL=https://newsapi.org/v2/top-headlines

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
