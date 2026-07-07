@echo off
rem Batch file to start all microservices using JDK 21.
rem Services: Auth (8081), Paciente (8082), Medico (8083), Especialidad (8084), Cita (8085), Examen (8086), Producto (8087)
rem Run this file from the repository root: "c:\Proyecto Fullstack II"

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

cd /d "%~dp0"
echo Using JAVA_HOME=%JAVA_HOME%
echo Starting all microservices...

echo --- Auth service (port 8081) ---
start /b "Auth Service" cmd /k "cd /d "%~dp0auth-service" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8081"

echo --- Paciente service (port 8082) ---
start /b "Paciente Service" cmd /k "cd /d "%~dp0paciente-service\paciente" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8082"

echo --- Medico service (port 8083) ---
start /b "Medico Service" cmd /k "cd /d "%~dp0medico-service\medico" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8083"

echo --- Especialidad service (port 8084) ---
start /b "Especialidad Service" cmd /k "cd /d "%~dp0especialidad-service\especialidad" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8084"

echo --- Cita service (port 8085) ---
start /b "Cita Service" cmd /k "cd /d "%~dp0cita-service\cita" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8085"

echo --- Examen service (port 8086) ---
start /b "Examen Service" cmd /k "cd /d "%~dp0examen-service\examen" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8086"

echo --- Producto service (port 8087) ---
start /b "Producto Service" cmd /k "cd /d "%~dp0producto-service\producto" && .\mvnw.cmd -DskipTests spring-boot:run -Dserver.port=8087"

echo.
echo All services have been started in separate terminal windows.
echo Each service will display its own output.
echo To stop all services, close each terminal window individually or use Ctrl+C in each window.
pause
