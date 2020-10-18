FROM openjdk:11-jre-slim-sid

WORKDIR /app

COPY build/libs/*-SNAPSHOT.jar app.jar
COPY entrypoint.sh .

ENTRYPOINT ["./entrypoint.sh"]
