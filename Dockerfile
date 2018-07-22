FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.yml application.yml
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom --spring.config.location=file:application.properties","-jar","/app.jar","--server.port=8081"]

