FROM maven:3.6-jdk-8-alpine as builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

FROM openjdk:8-jdk-alpine
ENV STARTUP_DELAY 1000
WORKDIR /opt
COPY --from=builder /app/target/springboot-app-1.0-SNAPSHOT.jar .
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/springboot-app-1.0-SNAPSHOT.jar"]
EXPOSE 8080