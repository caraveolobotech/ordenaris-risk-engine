# ---------- BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copiar pom.xml para aprovechar la caché de dependencias
COPY pom.xml .

# Resolver dependencias
RUN mvn -B dependency:resolve dependency:resolve-plugins

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar (sin ejecutar tests para acelerar)
RUN mvn -B -DskipTests package

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:17-jre
ARG APP_USER=appuser
RUN adduser --system --group --quiet $APP_USER || true

WORKDIR /app

# Copiar el jar construido
COPY --from=builder /workspace/target/*.jar app.jar

# Ajustar permisos y ejecutar como usuario no-root
RUN chown $APP_USER:$APP_USER /app/app.jar
USER $APP_USER

EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx1024m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar" ]
