FROM openjdk:21-slim
EXPOSE 8080
RUN mkdir /home/app
WORKDIR /home/app
COPY target/bpmn.modul-1.0.0-SNAPSHOT.jar .
COPY resources/docker/application.properties .

CMD ["java", "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED", "-Dspring.profiles.active=prod", "-Dspring.config.location=application.properties", "-jar", "bpmn.modul-1.0.0-SNAPSHOT.jar"]
