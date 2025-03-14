# Gradle을 사용해 프로젝트를 빌드하기 위한 빌더 이미지
FROM gradle:7.5-jdk17 as builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle wrapper 및 프로젝트 파일들을 복사
COPY --chown=gradle:gradle . .

# Gradle로 프로젝트 빌드 (모든 모듈을 빌드)
RUN gradle build --no-daemon

# OpenJDK 17을 사용하여 최종 이미지를 만듦
FROM openjdk:17-jdk-slim

# 컨테이너 내에서 작업 디렉토리 설정
WORKDIR /app

# issue 모듈에서 생성된 JAR 파일 복사
COPY --from=builder /app/issue/build/libs/*.jar /app/issue.jar

# module-common 모듈에서 생성된 JAR 파일 복사
COPY --from=builder /app/module-common/build/libs/*.jar /app/module-common.jar

# Spring Boot가 사용하는 포트 노출
EXPOSE 8080

# 애플리케이션 실행 (여기서 하나의 JAR 파일을 실행할 수 있음)
ENTRYPOINT ["java", "-jar", "/app/issue.jar"]
