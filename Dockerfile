FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon
COPY src/ ./src/
RUN ./gradlew clean build --no-daemon

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY build/libs/bxbatuz-0.0.1-SNAPSHOT.jar bxbatuz.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bxbatuz.jar"]