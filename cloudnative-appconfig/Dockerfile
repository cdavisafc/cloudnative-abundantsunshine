FROM openjdk:8-jdk-alpine
ARG jar_file
VOLUME /tmp
ADD VERSION .
ADD $jar_file app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar
