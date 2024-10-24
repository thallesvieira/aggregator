FROM gradle:7.5.1-jdk17 as builder
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/aggregator-0.0.1-SNAPSHOT.jar /app/aggregator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/aggregator.jar"]