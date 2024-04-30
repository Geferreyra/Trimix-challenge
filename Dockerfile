FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/trimix-backend-0.0.1-SNAPSHOT
COPY ${JAR_FILE} trimix_backend.jar
ENTRYPOINT ["java","-jar","trimix_backend.jar"]