@echo off
title Tailor Shop Management System
echo ==========================================
echo   Tailor Shop Management System
echo ==========================================
echo.

java -version
if errorlevel 1 (
  echo.
  echo ERROR: Java/JDK is not installed or is not in PATH.
  echo Install JDK 21, then reopen this window.
  pause
  exit /b 1
)

mvn -version
if errorlevel 1 (
  echo.
  echo ERROR: Maven is not installed or is not in PATH.
  echo Install Apache Maven 3.9+, then reopen this window.
  pause
  exit /b 1
)

echo.
echo Building project...
call mvn clean compile
if errorlevel 1 (
  echo.
  echo BUILD FAILED. Read the error above.
  pause
  exit /b 1
)

echo.
echo Starting application...
call mvn exec:java
pause
