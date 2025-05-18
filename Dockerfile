FROM openjdk:22-jdk-slim
ARG JAR_FILE=target/*.jar
ENV AWS_REGION=sa-east-1
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]