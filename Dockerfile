# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy pom.xml first → excellent layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source
COPY src ./src

# Package (skip tests for faster local iteration)
RUN mvn clean package -DskipTests

# Stage 2: Runtime (Ubuntu-based JRE – supports arm64 natively)
FROM eclipse-temurin:17-jre

# Create non-root user
RUN addgroup --system spring && adduser --system --group spring

WORKDIR /app

# Copy JAR from builder
COPY --from=builder /build/target/*.jar app.jar

# Permissions
RUN chown -R spring:spring /app

USER spring

EXPOSE 8080

# Container-friendly JVM flags
ENV JAVA_OPTS="\
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:+UseG1GC \
    -Djava.security.egd=file:/dev/./urandom"

# Health check (using your endpoint)
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/v1/load-optimizer/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]