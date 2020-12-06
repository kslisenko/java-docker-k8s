FROM openjdk:8-jdk-alpine
ENV STARTUP_DELAY 1000
WORKDIR /opt
COPY target/springboot-app-1.0-SNAPSHOT.jar /opt
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/springboot-app-1.0-SNAPSHOT.jar"]
EXPOSE 8080