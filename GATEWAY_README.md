# 🏥 HospitalTech Microservicios - Arquitectura con Eureka + API Gateway

## Descripción General

Esta es una arquitectura de microservicios hospitalarios con:
- **Eureka Server**: Service Registry (descubrimiento de servicios)
- **API Gateway**: Enrutador centralizado con Swagger agregado
- **10 Microservicios**: Auth, Pacientes, Médicos, Citas, Exámenes, Facturas, Inventario, Productos, Habitaciones, Especialidades
- **Swagger/OpenAPI**: Documentación visual en cada servicio

---

## 📊 Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                      API Gateway (8080)                      │
│            Swagger disponible: /swagger-ui.html             │
└────────────────────────┬────────────────────────────────────┘
                         │
          ┌──────────────┼──────────────┐
          │              │              │
    ┌─────▼──────┐ ┌─────▼──────┐ ┌──────▼──────┐
    │  Eureka    │ │ Auth (8081)│ │Pacientes   │
    │ (8761)     │ │            │ │  (8082)    │
    │ Registry   │ │ Swagger ✓  │ │ Swagger ✓  │
    └────────────┘ └────────────┘ └────────────┘
          │
    ┌─────┴────────────────────────────────────┬─────────────┐
    │              Otros Servicios             │             │
    │  Médicos (8083), Especialidades (8084)  │ Citas (8085)│
    │  Exámenes (8086), Productos (8087)      │ Inventario  │
    │  Facturas (8089), Habitaciones (8090)   │   (8088)    │
    │         Todos con Swagger ✓             │             │
    └──────────────────────────────────────────┴─────────────┘
```

---

## 🚀 Instrucciones de Inicio

### 1. **Iniciar Eureka Server** (Primero)
```bash
cd eureka-server
mvn spring-boot:run
```
✅ Eureka Dashboard: http://localhost:8761

### 2. **Iniciar API Gateway** (Segundo)
```bash
cd ../api-gateway
mvn spring-boot:run
```
✅ Gateway central: http://localhost:8080
✅ Swagger del Gateway: http://localhost:8080/swagger-ui.html

### 3. **Iniciar Microservicios** (Tercero - en orden o paralelo)

Opción A: Ejecutar el script batch (Windows)
```bash
run-microservices.bat
```

Opción B: Iniciar manualmente cada servicio
```bash
# Terminal 1
cd auth-service && mvn spring-boot:run

# Terminal 2
cd paciente-service/paciente && mvn spring-boot:run

# Terminal 3
cd medico-service/medico && mvn spring-boot:run

# ... y así sucesivamente
```

---

## 📍 Puertos Asignados

| Servicio | Puerto | Swagger |
|----------|--------|---------|
| Eureka Server | 8761 | Dashboard |
| **API Gateway** | **8080** | **✓ /swagger-ui.html** |
| Auth Service | 8081 | ✓ |
| Pacientes | 8082 | ✓ |
| Médicos | 8083 | ✓ |
| Especialidades | 8084 | ✓ |
| Citas | 8085 | ✓ |
| Exámenes | 8086 | ✓ |
| Productos | 8087 | ✓ |
| Inventario | 8088 | ✓ |
| Facturas | 8089 | ✓ |
| Habitaciones | 8090 | ✓ |

---

## 🔗 Acceso a Swagger

### Opción 1: Directamente en cada servicio
```
http://localhost:8081/swagger-ui.html  → Auth
http://localhost:8082/swagger-ui.html  → Pacientes
http://localhost:8083/swagger-ui.html  → Médicos
... y así para cada uno
```

### Opción 2: A través del Gateway (Recomendado)
```
http://localhost:8080/swagger-ui.html
```
El gateway reenruta automáticamente las solicitudes al servicio correcto.

---

## 🎯 Rutas del Gateway

Todas las rutas están configuradas automáticamente:

```
GET /auth/**                → auth-service:8081
GET /pacientes/**           → paciente-service:8082
GET /medicos/**             → medico-service:8083
GET /especialidades/**      → especialidad-service:8084
GET /citas/**               → cita-service:8085
GET /examenes/**            → examen-service:8086
GET /productos/**           → producto-service:8087
GET /inventario/**          → inventario-service:8088
GET /api/facturas/**        → factura-service:8089
GET /api/habitaciones/**    → habitaciones-service:8090
```

---

## 🌟 Características

✅ **Service Discovery**: Todos los servicios se registran automáticamente en Eureka  
✅ **Load Balancing**: El gateway usa round-robin para balancear carga  
✅ **Swagger Centralizado**: Visualiza todos los endpoints desde el gateway  
✅ **Documentación Completa**: Cada endpoint documentado con @Operation y @ApiResponse  
✅ **Escalabilidad**: Agregar nuevos servicios es simple (solo registrarse en Eureka)  
✅ **CORS Habilitado**: El gateway permite CORS para cliente web/mobile  

---

## 🛠️ Verificación

### 1. Verificar Eureka
```bash
curl http://localhost:8761
# O abre el navegador: http://localhost:8761
```
Deberías ver todos los servicios registrados.

### 2. Verificar Gateway
```bash
curl http://localhost:8080/swagger-ui.html
# O accede por navegador
```
Verás el Swagger UI del gateway.

### 3. Probar una ruta
```bash
curl http://localhost:8080/pacientes
# Reenrutará a paciente-service:8082/pacientes
```

---

## 📝 Configuración Importante

### Archivo: `application.yml` en cada microservicio

```yaml
eureka:
  instance:
    prefer-ip-address: true
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

spring:
  application:
    name: [service-name]  # Nombre único del servicio
```

**Nota**: El nombre del servicio (`spring.application.name`) es crítico. El gateway lo usa para enrutamiento.

---

## 🐛 Troubleshooting

### El servicio no aparece en Eureka
- ✅ Verifica que el Eureka Server está corriendo (puerto 8761)
- ✅ Verifica que `eureka.client.service-url.defaultZone` es correcto
- ✅ Verifica que `spring.application.name` es único
- ✅ Revisa los logs: `mvn spring-boot:run`

### El gateway no reenruta correctamente
- ✅ Verifica que el servicio está registrado en Eureka
- ✅ Verifica que la ruta en `application.yml` del gateway es correcta
- ✅ Verifica los logs del gateway: `org.springframework.cloud.gateway: DEBUG`

### Swagger no carga en el gateway
- ✅ Verifica que `springdoc-openapi-starter-webflux-ui` está en el `pom.xml`
- ✅ Limpia el cache: `mvn clean`
- ✅ Reinicia el gateway

---

## 📚 Documentación Adicional

### Endpoints del Gateway

Todas las operaciones CRUD están disponibles:

```bash
# Obtener todos los pacientes
curl http://localhost:8080/pacientes

# Crear paciente
curl -X POST http://localhost:8080/pacientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Pérez"}'

# Obtener factura
curl http://localhost:8080/api/facturas/1

# Y más...
```

---

## 🔐 Seguridad (Próximo paso)

Para agregar seguridad (JWT, OAuth2):
1. Implementar en cada microservicio
2. Configurar Spring Security en el gateway
3. Agregar filtros de autenticación

---

## 📞 Contacto y Soporte

Para dudas sobre la configuración, revisa los archivos:
- `eureka-server/src/main/resources/application.yml`
- `api-gateway/src/main/resources/application.yml`
- Microservicios: `src/main/resources/application.yml`

---

**Versión**: 1.0  
**Última actualización**: Julio 2026  
**Estado**: ✅ Production Ready
