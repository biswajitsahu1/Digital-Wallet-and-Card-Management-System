FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/discovery-0.0.1-SNAPSHOT.jar discovery-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/discovery-0.0.1-SNAPSHOT.jar"]