FROM openjdk
ARG JAR_FILE=out/artifacts/javaapi_jar/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080:8080