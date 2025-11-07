# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy wrapper and metadata first for better layer caching
COPY .mvn/ .mvn/
COPY mvnw mvnw.cmd pom.xml ./

# Pre-fetch dependencies (offline) to speed up subsequent builds
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copy sources last
COPY src ./src

# Build the jar (tests can run here; change to -DskipTests if you prefer)
RUN ./mvnw -q package

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar (artifact name is matched with a wildcard)
COPY --from=build /app/target/tictactoe-*.jar /app/app.jar

# Default command: run the CLI main class
CMD ["java", "-cp", "/app/app.jar", "dev.bhim.tictactoe.cli.Main"]
