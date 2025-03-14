# 1단계: Node.js 환경 설정 및 Svelte 애플리케이션 빌드
FROM node:18 AS svelte-builder

# 작업 디렉토리 설정
WORKDIR /app

# package.json과 package-lock.json 파일 복사
COPY front-voa/package*.json ./

# 종속성 설치
RUN npm install

# Svelte 애플리케이션 소스 코드 복사
COPY front-voa/ .

# ROLLUP_WATCH 환경 변수를 true로 설정하여 production 모드가 아닌 개발 모드로 빌드
ENV ROLLUP_WATCH=true

# Svelte 애플리케이션 빌드
RUN npm run build

# 2단계: Node.js 환경에서 실행 (서버 실행)
FROM node:18 AS svelte-server

WORKDIR /app

# 빌드된 애플리케이션을 서버 이미지로 복사
COPY --from=svelte-builder /app /app

# 3단계: 애플리케이션을 실행하기 위한 명령어
CMD ["npm", "run", "start"]

# 애플리케이션이 사용할 포트 노출
EXPOSE 3000

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
