@echo off
REM Deployment script for Daily News App (Windows)

echo 🚀 Starting deployment process...

REM Build the application
echo 📦 Building application...
mvn clean package -DskipTests

if %errorlevel% equ 0 (
    echo ✅ Build successful!
) else (
    echo ❌ Build failed!
    exit /b 1
)

REM Check if Docker is available
docker --version >nul 2>&1
if %errorlevel% equ 0 (
    echo 🐳 Building Docker image...
    docker build -t daily-news-app .
    
    echo 🚀 Starting application with Docker...
    docker run -d -p 8080:8080 --name daily-news-app -e NEWS_API_KEY=%NEWS_API_KEY% daily-news-app
    
    echo ✅ Application deployed successfully!
    echo 🌐 Access your app at: http://localhost:8080
) else (
    echo 📝 Docker not found. Running with Java directly...
    java -jar target/daily-news-app-1.0.0.jar
)
