# Social timeline

## Running the project

### Prerequisites

- [Docker](https://docs.docker.com/desktop/?_gl=1*ah4slm*_ga*MTYzNTIzOTQ5Mi4xNzAzMjkyOTU3*_ga_XJWPQMJYHQ*MTcwMzM2OTc2My4zLjEuMTcwMzM2OTc2OC41NS4wLjA.)
- [Docker Compose](https://docs.docker.com/compose/install/)

To run this project go to its root directory and them execute the following steps:

1. Start docker containers
    - `docker-compose up`
    - Go to http://127.0.0.1:7474/browser/ to check if it's up
2. If this is the first time you're running the project. Configure your database
    - `./scripts/configure_db.sh`
3. Start web server
    - `./gradlew run`

## Needed improvements

- Create .env/config.yml file to keep all configurations (e.g: Neo4J username and password)
- Spring Neo4J Data doesn't work with OffsetDateTime but with LocalDateTime. So we're storing date time without
  timezones for now
- Improve Neo4J error handling. Return custom error type
- Use [liquigraph](https://www.liquigraph.org/) to create Neo4J constrains
- Use @Async at Neo4J repositories
- Implement pagination, sort and filters
- Implement authentication and authorization with KeyCloak and Spring Security
- Unit tests