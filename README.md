### *Steps to Build and Run the Application*

1. *Build the Spring Boot Application*:
    - Ensure your Spring Boot application is built and the JAR file is generated. Run the following command in your project directory:
      mvn clean install

    - This will generate a JAR file in the target directory.

2. *Build and Run with Docker Compose*:
    - Navigate to the directory containing the Dockerfile and docker-compose.yml.
    - Run the following command to build and start the services:
      
      docker-compose up --build

    - This will build the Docker image for your Spring Boot application and start the PostgreSQL and Redis containers.

3. *Access the Application*:
    - Once the containers are running, your Spring Boot application will be accessible at http://localhost:8080.
    - PostgreSQL will be available at localhost:5432, and Redis will be available at localhost:6379.

4. *Stop the Services*:
    - To stop the services, run:
      docker-compose down
5.  * swagger endpoint
    - localhost:8080/swagger-ui.html
    - http://localhost:8080/v3/api-docs


---

### *Explanation of the Configuration*

- *Spring Boot Application (app)*:
    - The Dockerfile builds the application image.
    - The app service in docker-compose.yml exposes port 8080 and connects to the PostgreSQL and Redis services.
    - Environment variables are used to configure the database and Redis connections.

- *PostgreSQL (db)*:
    - Uses the official PostgreSQL image.
    - Configures the database name, username, and password.
    - Persists data using a Docker volume (postgres-data).

- *Redis (redis)*:
    - Uses the official Redis image.
    - Persists data using a Docker volume (redis-data).

- *Volumes*:
    - Volumes are used to persist data for PostgreSQL and Redis, ensuring data is not lost when containers are restarted.
      Redis is Not Running
      Run the following command to check if Redis is running:

Redis is Not Running (troubleshooting)
redis-cli ping
If it says Could not connect to Redis, Redis is not running.

Start Redis using:
redis-server

If using Docker, restart Redis:
docker restart redis-server