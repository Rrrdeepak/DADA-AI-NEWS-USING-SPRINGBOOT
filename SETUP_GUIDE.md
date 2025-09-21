# Daily News App - Setup Guide for Different IDEs/Compilers

## 🚀 Quick Start

This Spring Boot application can be run in various IDEs and environments. Here's how to set it up:

## 📋 Prerequisites

- **Java 17 or higher** (JDK)
- **Maven 3.6+** (for dependency management)
- **Internet connection** (for News API)
- **News API Key** (free from https://newsapi.org/)

## 🛠️ IDE Setup Instructions

### 1. **IntelliJ IDEA**

#### Setup Steps:
1. **Open Project:**
   - File → Open → Select project folder
   - Choose "Import project from external model" → Maven
   - Click Next → Finish

2. **Configure JDK:**
   - File → Project Structure → Project
   - Set Project SDK to Java 17+
   - Set Project language level to 17

3. **Run Configuration:**
   - Right-click `DailyNewsAppApplication.java`
   - Select "Run 'DailyNewsAppApplication'"
   - Or use Run → Run 'DailyNewsAppApplication'

4. **Environment Variables:**
   - Run → Edit Configurations
   - Add Environment Variables:
     - `NEWS_API_KEY=your_api_key_here`
     - `NEWS_API_URL=https://newsapi.org/v2/top-headlines`

### 2. **Eclipse IDE**

#### Setup Steps:
1. **Import Project:**
   - File → Import → Existing Maven Projects
   - Browse to project folder → Finish

2. **Configure JDK:**
   - Project → Properties → Java Build Path
   - Set Modulepath/Classpath to Java 17+

3. **Run Configuration:**
   - Right-click project → Run As → Java Application
   - Select `DailyNewsAppApplication`

4. **Environment Variables:**
   - Run → Run Configurations
   - Environment tab → Add:
     - `NEWS_API_KEY=your_api_key_here`
     - `NEWS_API_URL=https://newsapi.org/v2/top-headlines`

### 3. **Visual Studio Code**

#### Setup Steps:
1. **Install Extensions:**
   - Extension Pack for Java (Microsoft)
   - Spring Boot Extension Pack

2. **Open Project:**
   - File → Open Folder → Select project folder
   - VS Code will auto-detect Maven project

3. **Configure Java:**
   - Ctrl+Shift+P → "Java: Configure Java Runtime"
   - Select Java 17+ installation

4. **Run Application:**
   - Open `DailyNewsAppApplication.java`
   - Click "Run" button above main method
   - Or use Ctrl+F5

5. **Environment Variables:**
   - Create `.vscode/launch.json`:
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "Launch DailyNewsAppApplication",
         "request": "launch",
         "mainClass": "com.example.dailynewsapp.DailyNewsAppApplication",
         "env": {
           "NEWS_API_KEY": "your_api_key_here",
           "NEWS_API_URL": "https://newsapi.org/v2/top-headlines"
         }
       }
     ]
   }
   ```

### 4. **NetBeans**

#### Setup Steps:
1. **Open Project:**
   - File → Open Project → Select project folder
   - NetBeans will recognize Maven project

2. **Configure JDK:**
   - Tools → Java Platforms
   - Add Platform → Select Java 17+ installation

3. **Run Application:**
   - Right-click project → Run
   - Or press F6

4. **Environment Variables:**
   - Project Properties → Run → VM Options
   - Add: `-DNEWS_API_KEY=your_api_key_here`

### 5. **Command Line (Terminal/CMD)**

#### Setup Steps:
1. **Navigate to project directory:**
   ```bash
   cd "path/to/your/project"
   ```

2. **Build the project:**
   ```bash
   mvn clean compile
   ```

3. **Run the application:**
   ```bash
   # Option 1: Using Maven
   mvn spring-boot:run -Dspring-boot.run.arguments="--news.api.key=your_api_key_here"
   
   # Option 2: Using JAR file
   mvn clean package
   java -jar target/daily-news-app-1.0.0.jar --news.api.key=your_api_key_here
   ```

## 🔧 Configuration Options

### Method 1: Application Properties (Recommended)
Edit `src/main/resources/application.properties`:
```properties
news.api.key=your_actual_api_key_here
news.api.url=https://newsapi.org/v2/top-headlines
```

### Method 2: Environment Variables
Set these environment variables:
- `NEWS_API_KEY=your_api_key_here`
- `NEWS_API_URL=https://newsapi.org/v2/top-headlines`

### Method 3: Command Line Arguments
```bash
java -jar daily-news-app-1.0.0.jar --news.api.key=your_api_key_here
```

## 🐳 Docker Setup

### Prerequisites:
- Docker Desktop installed
- Docker Compose installed

### Run with Docker:
```bash
# Set environment variables
export NEWS_API_KEY=your_api_key_here

# Run with Docker Compose
docker-compose up -d --build

# Or with modern Docker Compose syntax
docker compose up -d --build
```

## 🔍 Troubleshooting

### Common Issues:

1. **"Connection reset" Error:**
   - Check your internet connection
   - Verify News API key is valid
   - Ensure API key has proper permissions

2. **Java Version Issues:**
   - Ensure Java 17+ is installed
   - Check JAVA_HOME environment variable

3. **Maven Issues:**
   - Run `mvn clean install` to refresh dependencies
   - Check Maven settings.xml for proxy issues

4. **Port Already in Use:**
   - Change port in `application.properties`: `server.port=8081`
   - Or kill process using port 8080

### Getting News API Key:
1. Go to https://newsapi.org/
2. Sign up for free account
3. Get your API key from dashboard
4. Replace `your_api_key_here` with actual key

## 📱 Accessing the Application

Once running, open your browser and go to:
- **Local:** http://localhost:8080
- **Network:** http://your-ip:8080

## 🎯 Features Available

- **All News:** General headlines
- **Business:** Business news
- **Technology:** Tech news  
- **Sports:** Sports news
- **Health:** Health news
- **Science:** Science news

## 📞 Support

If you encounter issues:
1. Check the troubleshooting section above
2. Verify all prerequisites are installed
3. Ensure News API key is valid and active
4. Check application logs for detailed error messages
