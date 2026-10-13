# --- build stage ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# o nome application.jar importa: o jar extraído mantém esse nome
RUN cp target/*.jar application.jar \
 && java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# --- run stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# ordem: do que menos muda para o que mais muda
COPY --from=build /app/extracted/dependencies/ ./
COPY --from=build /app/extracted/spring-boot-loader/ ./
COPY --from=build /app/extracted/snapshot-dependencies/ ./
COPY --from=build /app/extracted/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]