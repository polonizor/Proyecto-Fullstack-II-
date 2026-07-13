@echo off
setlocal enabledelayedexpansion

REM Establecer Maven Home
set MAVEN_HOME=C:\Users\Drago\apache-maven\apache-maven-3.9.6
set PATH=%MAVEN_HOME%\bin;%PATH%
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot

cd /d "C:\Users\Drago\copilot-worktrees\Proyecto-Fullstack-II-\grimnal-vigilant-bassoon"

echo ======================================
echo Iniciando API Gateway (Puerto 8080)
echo ======================================
cd api-gateway
call mvn spring-boot:run
