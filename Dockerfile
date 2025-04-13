FROM maven:3.9-eclipse-temurin-23-alpine as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /opt/build/app-custom
COPY pom.xml /opt/build/app-custom/
COPY src /opt/build/app-custom/src/
RUN mvn -f /opt/build/app-custom/pom.xml clean package -B -DskipTests


FROM eclipse-temurin:23-jdk-alpine
WORKDIR /opt/app/app-custom
COPY --from=BUILDER /opt/build/app-custom/target/*.jar /opt/app/app-custom/app-custom.jar

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar", "/opt/app/app-custom/app-custom.jar"]