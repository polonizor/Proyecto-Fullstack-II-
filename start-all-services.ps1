$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
$env:MAVEN_HOME = "C:\Users\Drago\apache-maven\apache-maven-3.9.6"
$env:Path = "$env:JAVA_HOME\bin;$env:MAVEN_HOME\bin;$env:Path"

$projectRoot = "C:\Proyecto Fullstack II"

$serviceNames = @(
    "Eureka",
    "Gateway",
    "Auth",
    "Paciente",
    "Medico",
    "Especialidad",
    "Cita",
    "Examen",
    "Producto",
    "Inventario",
    "Factura",
    "Habitaciones"
)

function Test-Port {
    param(
        [Parameter(Mandatory)]
        [string]$ComputerName,

        [Parameter(Mandatory)]
        [int]$Port
    )

    try {
        $connection = Test-NetConnection `
            -ComputerName $ComputerName `
            -Port $Port `
            -WarningAction SilentlyContinue

        return $connection.TcpTestSucceeded
    }
    catch {
        return $false
    }
}

function Wait-ForPort {
    param(
        [Parameter(Mandatory)]
        [string]$ServiceName,

        [Parameter(Mandatory)]
        [int]$Port,

        [int]$TimeoutSeconds = 90
    )

    Write-Host "Esperando que $ServiceName responda en el puerto $Port..." -ForegroundColor Yellow

    $elapsed = 0

    while ($elapsed -lt $TimeoutSeconds) {
        if (Test-Port -ComputerName "localhost" -Port $Port) {
            Write-Host "$ServiceName disponible en el puerto $Port" -ForegroundColor Green
            return $true
        }

        Start-Sleep -Seconds 3
        $elapsed += 3

        $job = Get-Job -Name $ServiceName -ErrorAction SilentlyContinue

        if ($null -ne $job -and $job.State -in @("Failed", "Completed", "Stopped")) {
            Write-Host "$ServiceName dejo de ejecutarse." -ForegroundColor Red
            Write-Host "Log de $ServiceName" -ForegroundColor Yellow
            Receive-Job -Name $ServiceName -Keep
            return $false
        }
    }

    Write-Host "$ServiceName no respondio despues de $TimeoutSeconds segundos." -ForegroundColor Red
    return $false
}

function Start-Microservice {
    param(
        [Parameter(Mandatory)]
        [string]$ServiceName,

        [Parameter(Mandatory)]
        [string]$ServicePath,

        [Parameter(Mandatory)]
        [int]$Port
    )

    $fullPath = Join-Path $projectRoot $ServicePath
    $pomPath = Join-Path $fullPath "pom.xml"

    if (-not (Test-Path -LiteralPath $fullPath)) {
        Write-Host "ERROR: No existe la carpeta de $ServiceName" -ForegroundColor Red
        Write-Host "Ruta: $fullPath" -ForegroundColor Red
        return $false
    }

    if (-not (Test-Path -LiteralPath $pomPath)) {
        Write-Host "ERROR: No existe pom.xml en $ServiceName" -ForegroundColor Red
        Write-Host "Ruta: $pomPath" -ForegroundColor Red
        return $false
    }

    $existingJob = Get-Job -Name $ServiceName -ErrorAction SilentlyContinue

    if ($null -ne $existingJob) {
        $existingJob | Stop-Job -ErrorAction SilentlyContinue
        $existingJob | Remove-Job -Force -ErrorAction SilentlyContinue
    }

    $job = Start-Job `
        -Name $ServiceName `
        -ArgumentList `
            $fullPath,
            $ServiceName,
            $Port,
            $env:JAVA_HOME,
            $env:MAVEN_HOME `
        -ScriptBlock {

            param(
                $serviceDirectory,
                $name,
                $servicePort,
                $javaHome,
                $mavenHome
            )

            $env:JAVA_HOME = $javaHome
            $env:MAVEN_HOME = $mavenHome
            $env:Path = "$javaHome\bin;$mavenHome\bin;$env:Path"

            Set-Location -LiteralPath $serviceDirectory

            Write-Output "[$(Get-Date -Format 'HH:mm:ss')] Iniciando $name en puerto $servicePort"

            if (Test-Path -LiteralPath ".\mvnw.cmd") {
                & ".\mvnw.cmd" spring-boot:run
            }
            elseif (Test-Path -LiteralPath "$mavenHome\bin\mvn.cmd") {
                & "$mavenHome\bin\mvn.cmd" spring-boot:run
            }
            else {
                throw "No se encontro mvnw.cmd ni Maven global para $name"
            }

            if ($LASTEXITCODE -ne 0) {
                throw "$name termino con codigo de error $LASTEXITCODE"
            }
        }

    Write-Host "[$(Get-Date -Format 'HH:mm:ss')] $ServiceName iniciado como Job $($job.Id)" -ForegroundColor Cyan
    return $true
}

function Show-ServiceStatus {
    $jobs = Get-Job | Where-Object {
        $_.Name -in $serviceNames
    }

    Write-Host ""
    Write-Host "Estado de los servicios:" -ForegroundColor Cyan

    if ($null -eq $jobs) {
        Write-Host "No hay servicios ejecutandose." -ForegroundColor Yellow
        return
    }

    $jobs |
        Select-Object Id, Name, State, HasMoreData |
        Format-Table -AutoSize
}

function Stop-AllMicroservices {
    Write-Host "Deteniendo servicios..." -ForegroundColor Yellow

    Get-Job |
        Where-Object { $_.Name -in $serviceNames } |
        Stop-Job -ErrorAction SilentlyContinue

    Get-Job |
        Where-Object { $_.Name -in $serviceNames } |
        Remove-Job -Force -ErrorAction SilentlyContinue

    Write-Host "Servicios detenidos." -ForegroundColor Green
}

Clear-Host

Write-Host "======================================" -ForegroundColor Green
Write-Host "Iniciando todos los microservicios" -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Green
Write-Host ""

if (-not (Test-Path -LiteralPath $projectRoot)) {
    Write-Host "ERROR: No existe la ruta principal:" -ForegroundColor Red
    Write-Host $projectRoot -ForegroundColor Red
    exit 1
}

if (-not (Test-Path -LiteralPath $env:JAVA_HOME)) {
    Write-Host "ERROR: No existe JAVA_HOME:" -ForegroundColor Red
    Write-Host $env:JAVA_HOME -ForegroundColor Red
    exit 1
}

if (-not (Test-Path -LiteralPath "$env:MAVEN_HOME\bin\mvn.cmd")) {
    Write-Host "ADVERTENCIA: No se encontro Maven global:" -ForegroundColor Yellow
    Write-Host "$env:MAVEN_HOME\bin\mvn.cmd" -ForegroundColor Yellow
    Write-Host "Eureka y Gateway necesitan Maven global si no tienen mvnw.cmd." -ForegroundColor Yellow
    exit 1
}

if (-not (Test-Port -ComputerName "localhost" -Port 3306)) {
    Write-Host "ERROR: MySQL no responde en el puerto 3306." -ForegroundColor Red
    Write-Host "Abre Laragon y presiona Start All antes de ejecutar este script." -ForegroundColor Yellow
    exit 1
}

Write-Host "MySQL esta disponible en el puerto 3306." -ForegroundColor Green

Stop-AllMicroservices

Write-Host ""
Write-Host "[1/12] Eureka Server (8761)" -ForegroundColor Yellow

$eurekaStarted = Start-Microservice `
    -ServiceName "Eureka" `
    -ServicePath "eureka-server" `
    -Port 8761

if (-not $eurekaStarted) {
    exit 1
}

if (-not (Wait-ForPort -ServiceName "Eureka" -Port 8761 -TimeoutSeconds 120)) {
    exit 1
}

Write-Host ""
Write-Host "[2/12] API Gateway (8080)" -ForegroundColor Yellow

$gatewayStarted = Start-Microservice `
    -ServiceName "Gateway" `
    -ServicePath "api-gateway" `
    -Port 8080

if (-not $gatewayStarted) {
    exit 1
}

if (-not (Wait-ForPort -ServiceName "Gateway" -Port 8080 -TimeoutSeconds 120)) {
    exit 1
}

Write-Host ""
Write-Host "Iniciando los 10 microservicios..." -ForegroundColor Yellow

Start-Microservice `
    -ServiceName "Auth" `
    -ServicePath "auth-service" `
    -Port 8081 |
    Out-Null

Start-Microservice `
    -ServiceName "Paciente" `
    -ServicePath "paciente-service\paciente" `
    -Port 8082 |
    Out-Null

Start-Microservice `
    -ServiceName "Medico" `
    -ServicePath "medico-service\medico" `
    -Port 8083 |
    Out-Null

Start-Microservice `
    -ServiceName "Especialidad" `
    -ServicePath "especialidad-service\especialidad" `
    -Port 8084 |
    Out-Null

Start-Microservice `
    -ServiceName "Cita" `
    -ServicePath "cita-service\cita" `
    -Port 8085 |
    Out-Null

Start-Microservice `
    -ServiceName "Examen" `
    -ServicePath "examen-service\examen" `
    -Port 8086 |
    Out-Null

Start-Microservice `
    -ServiceName "Producto" `
    -ServicePath "producto-service\producto" `
    -Port 8087 |
    Out-Null

Start-Microservice `
    -ServiceName "Inventario" `
    -ServicePath "inventario-service\inventario" `
    -Port 8088 |
    Out-Null

Start-Microservice `
    -ServiceName "Factura" `
    -ServicePath "facturacion-service\factura" `
    -Port 8089 |
    Out-Null

Start-Microservice `
    -ServiceName "Habitaciones" `
    -ServicePath "habitacion-service\habitaciones" `
    -Port 8090 |
    Out-Null

Write-Host ""
Write-Host "======================================" -ForegroundColor Green
Write-Host "Servicios enviados a ejecucion" -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Green
Write-Host ""

Write-Host "Eureka Dashboard:" -ForegroundColor Cyan
Write-Host "http://localhost:8761"
Write-Host ""

Write-Host "API Gateway:" -ForegroundColor Cyan
Write-Host "http://localhost:8080"
Write-Host ""

Write-Host "Swagger Gateway:" -ForegroundColor Cyan
Write-Host "http://localhost:8080/swagger-ui.html"
Write-Host ""

Write-Host "Puertos:" -ForegroundColor Cyan
Write-Host "Eureka:        8761"
Write-Host "Gateway:       8080"
Write-Host "Auth:          8081"
Write-Host "Paciente:      8082"
Write-Host "Medico:        8083"
Write-Host "Especialidad:  8084"
Write-Host "Cita:          8085"
Write-Host "Examen:        8086"
Write-Host "Producto:      8087"
Write-Host "Inventario:    8088"
Write-Host "Factura:       8089"
Write-Host "Habitaciones:  8090"

Start-Sleep -Seconds 30

Show-ServiceStatus

Write-Host ""
Write-Host "Comandos utiles:" -ForegroundColor Cyan
Write-Host 'Ver trabajos:               Get-Job'
Write-Host 'Ver log de Medico:          Receive-Job -Name "Medico" -Keep'
Write-Host 'Ver log de Eureka:          Receive-Job -Name "Eureka" -Keep'
Write-Host 'Ver todos los logs:         Get-Job | Receive-Job -Keep'
Write-Host 'Detener los servicios:      Get-Job | Stop-Job'
Write-Host 'Eliminar los trabajos:      Get-Job | Remove-Job -Force'
Write-Host ""
Write-Host "Presiona Ctrl + C para salir del monitoreo." -ForegroundColor Yellow

try {
    while ($true) {
        Start-Sleep -Seconds 30

        $serviceJobs = Get-Job | Where-Object {
            $_.Name -in $serviceNames
        }

        $running = @(
            $serviceJobs |
                Where-Object { $_.State -eq "Running" }
        ).Count

        $failed = @(
            $serviceJobs |
                Where-Object { $_.State -eq "Failed" }
        ).Count

        $completed = @(
            $serviceJobs |
                Where-Object { $_.State -eq "Completed" }
        ).Count

        Write-Host ""
        Write-Host "[$(Get-Date -Format 'HH:mm:ss')] Activos: $running | Fallidos: $failed | Finalizados: $completed" -ForegroundColor Green

        $problemJobs = $serviceJobs | Where-Object {
            $_.State -in @("Failed", "Completed")
        }

        if ($null -ne $problemJobs) {
            Write-Host "Servicios que dejaron de ejecutarse:" -ForegroundColor Red

            $problemJobs |
                Select-Object Id, Name, State |
                Format-Table -AutoSize

            foreach ($problemJob in $problemJobs) {
                Write-Host "Ultimas lineas de $($problemJob.Name):" -ForegroundColor Yellow

                $output = Receive-Job `
                    -Id $problemJob.Id `
                    -Keep `
                    -ErrorAction SilentlyContinue

                if ($null -ne $output) {
                    $output | Select-Object -Last 20
                }
            }
        }
    }
}
finally {
    Write-Host ""
    Write-Host "El monitoreo fue detenido." -ForegroundColor Yellow
    Write-Host "Los servicios seguiran como Jobs mientras esta sesion de PowerShell permanezca abierta." -ForegroundColor Yellow
}