FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY . .

RUN mvn -pl services/order-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/services/order-service/target/order-service-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
