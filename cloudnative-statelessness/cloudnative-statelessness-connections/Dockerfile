FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/cloudnative-statelessness-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar
