# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

COPY target/users-0.0.1-SNAPSHOT.jar app.jar
# Copy the project’s jar file into the container at /app
RUN useradd -ms /bin/bash appuser
USER appuser
# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]