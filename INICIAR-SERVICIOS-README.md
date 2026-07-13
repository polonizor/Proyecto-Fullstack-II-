# 🚀 GUÍA RÁPIDA - Iniciar Microservicios

## ⚡ FORMA MÁS FÁCIL (Recomendado)

**Doble-click en**: `INICIAR-SERVICIOS.bat`

Eso es todo. Este script abre todas las ventanas (minimizadas) y las gestiona automáticamente.

---

## 📊 Acceso a los Dashboards

### Eureka Server (Service Registry)
```
http://localhost:8761
```
Aquí verás todos los microservicios registrados automáticamente.

### API Gateway + Swagger
```
http://localhost:8080/swagger-ui.html
```
Documentación centralizada de todos los endpoints. Aquí puedes probar todas las APIs.

---

## 🔌 Puertos de los Servicios

| Servicio | Puerto |
|----------|--------|
| **Eureka Server** | **8761** |
| **API Gateway** | **8080** |
| Auth Service | 8081 |
| Paciente Service | 8082 |
| Médico Service | 8083 |
| Especialidad Service | 8084 |
| Cita Service | 8085 |
| Examen Service | 8086 |
| Producto Service | 8087 |
| Inventario Service | 8088 |
| Factura Service | 8089 |
| Habitaciones Service | 8090 |

---

## 🛠️ Métodos Alternativos

### Opción 2: Ejecutar en CMD (Windows)
```bash
cd C:\Users\Drago\copilot-worktrees\Proyecto-Fullstack-II-\grimnal-vigilant-bassoon
INICIAR-SERVICIOS.bat
```

### Opción 3: Ejecutar en PowerShell
```powershell
cd 'C:\Users\Drago\copilot-worktrees\Proyecto-Fullstack-II-\grimnal-vigilant-bassoon'
.\INICIAR-SERVICIOS.bat
```

---

## ⏱️ Tiempo de Inicio

- **Primera vez**: 2-5 minutos (Maven descarga dependencias)
- **Siguientes veces**: 1-2 minutos

Puedes ver el progreso en las ventanas minimizadas en la barra de tareas.

---

## 📝 Comandos Útiles

### Ver todos los servicios registrados en Eureka
```
Abre: http://localhost:8761
Verás una lista de todos los servicios "UP"
```

### Probar un endpoint a través del Gateway
```bash
curl http://localhost:8080/pacientes
```

### Ver logs en tiempo real
Haz click en la ventana minimizada en la barra de tareas para ver los logs de ese servicio.

---

## 🛑 Detener los Servicios

### Opción 1: Cerrar las ventanas una por una
Busca las ventanas en la barra de tareas y ciérralas.

### Opción 2: Comando rápido
```bash
taskkill /F /IM java.exe
```

---

## ✅ Verificar que todo funciona

1. Abre: http://localhost:8761
   - Deberías ver todos los servicios en estado "UP" (verde)

2. Abre: http://localhost:8080/swagger-ui.html
   - Deberías ver el Swagger con todos los endpoints

3. Intenta una petición:
   ```bash
   curl http://localhost:8080/pacientes
   ```

---

## 🆘 Troubleshooting

### Los servicios no aparecen en Eureka
- Espera 30-60 segundos más (la compilación puede tardar)
- Recarga el navegador (F5)
- Verifica que las ventanas cmd no muestren errores

### Antivirus bloquea la ejecución
- Agrega Maven a la lista blanca: `C:\Users\Drago\apache-maven\`
- Agrega Java: `C:\Program Files\Eclipse Adoptium\`

### "mvn not found"
- Maven está en: `C:\Users\Drago\apache-maven\apache-maven-3.9.6\bin\`
- El PATH se configura automáticamente en los scripts

---

## 📚 Documentación Completa

Ver: `GATEWAY_README.md` para más detalles sobre arquitectura, configuración y desarrollo.

