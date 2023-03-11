FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/spring-boot-web.jar
WORKDIR /opt/app
COPY ./src/main/resources/serviceAccountKey.json /opt/app/serviceAccountKey.json
COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]