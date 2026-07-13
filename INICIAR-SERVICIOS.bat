@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"
set "BASE=C:\Users\Drago\copilot-worktrees\Proyecto-Fullstack-II-\grimnal-vigilant-bassoon"

cd /d "%BASE%"

cls
echo ===============================================
echo INICIAR TODOS LOS MICROSERVICIOS
echo ===============================================
echo.
echo Presiona ENTER para continuar...
pause >nul

echo Iniciando Eureka Server...
start "Eureka Server (8761)" /MIN cmd /k "cd /d "%BASE%\eureka-server" && call mvnw.cmd spring-boot:run"

echo Esperando 15 segundos...
timeout /t 15 /nobreak >nul

echo Iniciando API Gateway...
start "API Gateway (8080)" /MIN cmd /k "cd /d "%BASE%\api-gateway" && call mvnw.cmd spring-boot:run"

echo Esperando 10 segundos...
timeout /t 10 /nobreak >nul

echo Iniciando microservicios...

start "Auth Service (8081)" /MIN cmd /k "cd /d "%BASE%\auth-service" && call mvnw.cmd spring-boot:run"

start "Paciente Service (8082)" /MIN cmd /k "cd /d "%BASE%\paciente-service\paciente" && call mvnw.cmd spring-boot:run"

start "Medico Service (8083)" /MIN cmd /k "cd /d "%BASE%\medico-service\medico" && call mvnw.cmd spring-boot:run"

start "Especialidad Service (8084)" /MIN cmd /k "cd /d "%BASE%\especialidad-service\especialidad" && call mvnw.cmd spring-boot:run"

start "Cita Service (8085)" /MIN cmd /k "cd /d "%BASE%\cita-service\cita" && call mvnw.cmd spring-boot:run"

start "Examen Service (8086)" /MIN cmd /k "cd /d "%BASE%\examen-service\examen" && call mvnw.cmd spring-boot:run"

start "Producto Service (8087)" /MIN cmd /k "cd /d "%BASE%\producto-service\producto" && call mvnw.cmd spring-boot:run"

start "Inventario Service (8088)" /MIN cmd /k "cd /d "%BASE%\inventario-service\inventario" && call mvnw.cmd spring-boot:run"

start "Factura Service (8089)" /MIN cmd /k "cd /d "%BASE%\facturacion-service\factura" && call mvnw.cmd spring-boot:run"

start "Habitaciones Service (8090)" /MIN cmd /k "cd /d "%BASE%\habitacion-service\habitaciones" && call mvnw.cmd spring-boot:run"

cls
echo ===============================================
echo SERVICIOS EN PROCESO DE INICIO
echo ===============================================
echo.
echo Eureka:
echo http://localhost:8761
echo.
echo API Gateway:
echo http://localhost:8080
echo.
echo Swagger Gateway:
echo http://localhost:8080/swagger-ui.html
echo.
echo Microservicios:
echo Auth:          http://localhost:8081
echo Paciente:      http://localhost:8082
echo Medico:        http://localhost:8083
echo Especialidad:  http://localhost:8084
echo Cita:          http://localhost:8085
echo Examen:        http://localhost:8086
echo Producto:      http://localhost:8087
echo Inventario:    http://localhost:8088
echo Factura:       http://localhost:8089
echo Habitaciones:  http://localhost:8090
echo.
echo Las consolas quedan abiertas si ocurre algun error.
echo.
pause