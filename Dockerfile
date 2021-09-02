FROM openjdk:11
VOLUME /main-app
ADD app/build/libs/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]