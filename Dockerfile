FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY gradlew .
COPY gradle/ ./gradle/
COPY build.gradle .
COPY settings.gradle .

COPY src ./src

RUN chmod +x ./gradlew
RUN ./gradlew build --no-daemon
RUN ls -l build/libs

COPY build/libs/*.jar /app/aggregator.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "aggregator.jar"]