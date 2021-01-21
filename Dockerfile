FROM openjdk:11-jre-slim-sid

WORKDIR /app

COPY build/libs/*-SNAPSHOT.jar app.jar
COPY entrypoint.sh .

ENV CACHE_ON_STARTUP=true

ENTRYPOINT ["./entrypoint.sh"]
