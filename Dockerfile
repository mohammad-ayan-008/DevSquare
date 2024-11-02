FROM openjdk:21-jdk-alpine
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
