FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY . .

RUN mvn -pl services/notification-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/services/notification-service/target/notification-service-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
