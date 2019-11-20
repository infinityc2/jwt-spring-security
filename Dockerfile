FROM openjdk:8-jdk-slim
VOLUME /tmp
ARG JAR_FILE=/build/libs/secure-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /opt/app.jar
WORKDIR /opt
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/opt/app.jar"]