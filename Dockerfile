FROM openjdk:21-jdk-slim

# 시간대 설정
ENV TZ=Asia/Seoul
RUN apt-get update && apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    apt-get clean

VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

COPY src/main/resources/templates /app/resources/templates

ENTRYPOINT ["java", "-jar", "/app.jar"]