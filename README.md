# Tech Test – Franchise / Branch / Product (Spring WebFlux + R2DBC + PostgreSQL)

Aplicación reactiva para gestionar **franquicias**, **sucursales** y **productos** usando **Spring Boot WebFlux**, **R2DBC** y **PostgreSQL**. Incluye un endpoint para mostrar el **producto con más stock por sucursal** de una franquicia. Documentación de API vía **Swagger/OpenAPI**.

---

## 🧱 Tecnologías
- Java 21 (Amazon Corretto recomendado)
- Spring Boot 3 (WebFlux, Validation)
- R2DBC PostgreSQL
- PostgreSQL 16 (Docker Compose)
- Swagger / springdoc-openapi
- Gradle o Maven (build local; el contenedor **no** compila)

> **Nota:** La app corre en host con JDK; la base de datos corre en Docker.


---

## ⚡️ TL;DR (pasos rápidos)
1) **Levanta la base de datos** (Docker):
```bash
docker compose up -d
```
- Postgres → `localhost:5432` (DB: `techtest`, user: `postgres`, pass: `postgres`)
- PgAdmin → `http://localhost:5050` (`admin@example.com` / `admin`)

2) **Configura variables de entorno** para la app (ver abajo).
3) **Compila** (local, con tu JDK 21):
    - Maven: `./mvnw clean package -DskipTests`
4) **Arranca la app**:
    - `java -jar target/*.jar` (Maven) 
5) **Abre Swagger**: `http://localhost:8080/swagger-ui/index.html`


---

## 🧰 Requisitos
- **JDK 21** (Amazon Corretto recomendado)
- **Docker** y **Docker Compose** para la BD
- **Maven o Gradle** instalados (para compilar localmente)
  >  el repo incluye wrappers (`mvnw`), puedes usarlos sin instalación global.


---

## 🗃 Base de datos en Docker y App
El repo trae un `docker-compose.yml` para **PostgreSQL + PgAdmin** y semillas en `db/init/*.sql`.

### Levantar / bajar
```bash
# Construccion del proyecto
./mvnw clean package -DskipTests

# Crear la imagen
docker build -t tecnica-app:latest .

# Arrancar BD en segundo plano
docker compose up -d

# Ver logs
docker compose logs -f postgres

# Parar servicios (sin borrar datos)
docker compose down

# Parar y borrar volúmenes (resetea datos y vuelve a cargar seeds)
docker compose down -v && docker compose up -d
```

**Credenciales por defecto**
- Host: `localhost`
- Puerto: `5432`
- DB: `techtest`
- Usuario: `postgres`
- Password: `postgres`

**PgAdmin**
- URL: `http://localhost:5050`
- Email: `admin@example.com`
- Pass:  `admin`

---

## 📚 Swagger / OpenAPI
- **UI**: http://localhost:8080/swagger-ui/index.html
- **JSON**: http://localhost:8080/v3/api-docs

> Si cambiaste el `server.port` o un `server.servlet.context-path`, ajusta la URL.


---
## 🧱 Estructura de carpetas (orientativa)
```
src/
  main/
    java/com/prueba/tecnica/
      application/         # casos de uso
      domain/              # entidades de dominio (DDD)
      infrastructure/      # adapters, repos R2DBC
      interfaces/rest/     # controllers + DTOs
    resources/
      application.yml
db/
  init/                    # scripts SQL iniciales (esquema + seeds)
docker-compose.yml
```