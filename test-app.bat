@echo off
echo Testing Daily News App...
echo.

REM Check if application is running
curl -s http://localhost:8080 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Application is running on http://localhost:8080
    echo.
    echo Testing endpoints:
    echo.
    
    echo 📰 Testing home page...
    curl -s -o nul -w "Status: %%{http_code}\n" http://localhost:8080/
    
    echo 🏢 Testing business category...
    curl -s -o nul -w "Status: %%{http_code}\n" http://localhost:8080/category?category=business
    
    echo 💻 Testing technology category...
    curl -s -o nul -w "Status: %%{http_code}\n" http://localhost:8080/category?category=technology
    
    echo ⚽ Testing sports category...
    curl -s -o nul -w "Status: %%{http_code}\n" http://localhost:8080/category?category=sports
    
    echo 🏥 Testing health category...
    curl -s -o nul -w "Status: %%{http_code}\n" http://localhost:8080/category?category=health
    
    echo.
    echo ✅ All tests completed!
    echo 🌐 Open http://localhost:8080 in your browser to see the app
) else (
    echo ❌ Application is not running on port 8080
    echo Please start the application first using run.bat
)

pause
