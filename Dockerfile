FROM openjdk:21-jdk-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

COPY src/main/resources/templates /app/resources/templates

ENTRYPOINT ["java", "-jar", "/app.jar"]