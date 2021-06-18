FROM maven:3.6.0-jdk-11-slim as build

WORKDIR /workspace/app

COPY ./ ./

RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim

COPY --from=build /workspace/app/target/user-service-1.0.0-SNAPSHOT.jar /usr/local/lib/user-service.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/usr/local/lib/user-service.jar"]