# Ordenaris Risk Engine

Microservicio en Java 23 con Spring Boot que evalúa riesgo financiero de empresas.

## Características
- Motor de reglas configurable
- Persistencia H2 para pruebas
- Swagger / OpenAPI
- Dockerizado con multi-stage build
- Pruebas unitarias con JUnit 5 + Mockito

## Requisitos
- Docker Desktop
- Java 23 y Maven 3.9.x
- Git

## Quick Start

### Clonar
```bash
git clone <URL_DEL_REPOSITORIO>
cd ordenaris-risk-engine

#Docker
docker build -t ordenaris-risk-engine:latest .
docker run --rm -p 8080:8080 ordenaris-risk-engine:latest

#Maven local
mvn clean package -DskipTests
java -jar target/ordenaris-risk-engine-0.0.1-SNAPSHOT.jar

#Swagger
http://localhost:8080/swagger-ui.html

#Tests
mvn test
