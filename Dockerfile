# Gradle 빌드 이미지를 사용해 애플리케이션을 빌드
FROM gradle:7.5.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

# OpenJDK 17 이미지로 전환하고 JAR 파일 실행
FROM openjdk:17
WORKDIR /app
COPY build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

# 애플리케이션의 기본 포트(8080)를 노출
EXPOSE 8080