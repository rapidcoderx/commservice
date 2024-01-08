# Define a base image
FROM openjdk:17-slim

# Directory in which application will be placed
WORKDIR /app

# Copy the application artifact to the container
COPY ./target/my-application.jar /app

# Command to run the application
CMD ["java", "-jar", "/app/my-application.jar"]