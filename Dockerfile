FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/TaskManagementSystem-0.0.1-SNAPSHOT.jar /app/TaskManagementSystem.jar

ENTRYPOINT ["java", "-jar", "/app/TaskManagementSystem.jar"]