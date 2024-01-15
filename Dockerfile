# Docker Build Maven Stage
FROM maven:3-openjdk-17 AS build
WORKDIR /opt/app
COPY ./ /opt/app
RUN mvn clean install -DskipTests

# Run spring boot in Docker
FROM openjdk:17-jdk
COPY --from=build /opt/app/target/laundryAPI-0.0.1-SNAPSHOT.jar laundryapi.jar
ENV PORT 8086
EXPOSE $PORT
ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","laundryapi.jar"]
