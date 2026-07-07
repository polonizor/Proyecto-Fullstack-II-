@echo off
rem Batch file to start auth, medico and paciente microservices using JDK 21.
rem Run this file from the repository root: "c:\Proyecto Fullstack II"

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

cd /d "%~dp0"
echo Using JAVA_HOME=%JAVA_HOME%
echo Starting services in the current console...

echo --- Auth service (port 8081) ---
start /b "Auth Service" cmd /k "cd /d "%~dp0auth-service" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8081"

echo --- Medico service (port 8083) ---
start /b "Medico Service" cmd /k "cd /d "%~dp0medico-service\medico" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8083"

echo --- Paciente service (port 8082) ---
start /b "Paciente Service" cmd /k "cd /d "%~dp0paciente-service\paciente" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8082"

echo All services have been started in the current window.
echo Output from all services will appear interleaved here.
echo To stop everything, close this terminal or use Ctrl+C and then close the window.
pause
