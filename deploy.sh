#!/bin/bash

# Deployment script for Daily News App

echo "🚀 Starting deployment process..."

# Build the application
echo "📦 Building application..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
else
    echo "❌ Build failed!"
    exit 1
fi

# Check if Docker is available
if command -v docker &> /dev/null; then
    echo "🐳 Building Docker image..."
    docker build -t daily-news-app .
    
    echo "🚀 Starting application with Docker..."
    docker run -d -p 8080:8080 --name daily-news-app -e NEWS_API_KEY=$NEWS_API_KEY daily-news-app
    
    echo "✅ Application deployed successfully!"
    echo "🌐 Access your app at: http://localhost:8080"
else
    echo "📝 Docker not found. Running with Java directly..."
    java -jar target/daily-news-app-1.0.0.jar
fi
