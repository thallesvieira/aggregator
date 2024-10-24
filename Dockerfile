FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew build --no-daemon

#COPY build/libs/*.jar /app/aggregator.jar
COPY build/libs/aggregator-0.0.1-SNAPSHOT.jar /app/aggregator.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/aggregator.jar"]