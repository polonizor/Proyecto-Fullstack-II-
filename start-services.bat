@echo off
REM Establecer Maven Home
set MAVEN_HOME=C:\Users\Drago\apache-maven\apache-maven-3.9.6
set PATH=%MAVEN_HOME%\bin;%PATH%

REM Cambiar a directorio del proyecto
cd /d "C:\Users\Drago\copilot-worktrees\Proyecto-Fullstack-II-\grimnal-vigilant-bassoon"

REM Iniciar Eureka Server
echo Iniciando Eureka Server en puerto 8761...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"

REM Esperar a que Eureka inicie
echo Esperando que Eureka inicie (15 segundos)...
timeout /t 15 /nobreak

REM Iniciar API Gateway
echo Iniciando API Gateway en puerto 8080...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"

REM Esperar a que Gateway inicie
echo Esperando que Gateway inicie (10 segundos)...
timeout /t 10 /nobreak

REM Iniciar Microservicios
echo Iniciando Auth Service (8081)...
start "Auth Service" cmd /k "cd auth-service && mvn spring-boot:run"

echo Iniciando Paciente Service (8082)...
start "Paciente Service" cmd /k "cd paciente-service\paciente && mvn spring-boot:run"

echo Iniciando Medico Service (8083)...
start "Medico Service" cmd /k "cd medico-service\medico && mvn spring-boot:run"

echo Iniciando Especialidad Service (8084)...
start "Especialidad Service" cmd /k "cd especialidad-service\especialidad && mvn spring-boot:run"

echo Iniciando Cita Service (8085)...
start "Cita Service" cmd /k "cd cita-service\cita && mvn spring-boot:run"

echo Iniciando Examen Service (8086)...
start "Examen Service" cmd /k "cd examen-service\examen && mvn spring-boot:run"

echo Iniciando Producto Service (8087)...
start "Producto Service" cmd /k "cd producto-service\producto && mvn spring-boot:run"

echo Iniciando Inventario Service (8088)...
start "Inventario Service" cmd /k "cd inventario-service\inventario && mvn spring-boot:run"

echo Iniciando Factura Service (8089)...
start "Factura Service" cmd /k "cd facturacion-service\factura && mvn spring-boot:run"

echo Iniciando Habitaciones Service (8090)...
start "Habitaciones Service" cmd /k "cd habitacion-service\habitaciones && mvn spring-boot:run"

echo.
echo ======================================
echo Todos los servicios han sido iniciados
echo ======================================
echo.
echo Eureka Dashboard: http://localhost:8761
echo API Gateway Swagger: http://localhost:8080/swagger-ui.html
echo.
pause
