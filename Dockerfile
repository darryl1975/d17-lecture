
FROM maven:3-eclipse-temurin-21

# working and target directory or app will be in
WORKDIR /app

# Copy all the required to /app
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# Build the app
RUN mvn package -Dmaven.test.skip=true

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/d17-lecture-0.0.1-SNAPSHOT.jar