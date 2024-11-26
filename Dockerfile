FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/monitor-sensors-1.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
