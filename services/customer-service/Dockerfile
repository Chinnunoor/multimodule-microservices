FROM maven:3.9.11-eclipse-temurin-21 AS build

ARG BUILD_TYPE
ARG VERSION

WORKDIR /workspace
COPY . .

RUN if [ "$BUILD_TYPE" = "release" ]; then \
        mvn versions:set -DnewVersion="${VERSION}" -DgenerateBackupPoms=false; \
    fi

RUN mvn -pl services/customer-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/services/customer-service/target/customer-service-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
