
## Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

## Set the working directory inside the container
WORKDIR /app

## Copy the application JAR file into the container
COPY target/url-scrapping.jar app.jar

## Expose the port your application will run on
EXPOSE 8080

## Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]