# Github Popular Repositories Backend Service

Backend service that fetches popular Github repositories using Github API.

---

## Dev setup

In order to run the application after cloning the repo you will need to:

1. Build the project
2. Run the project

From the terminal

```shell
./gradlew bootrun
```

---

### Architecture

The project was modeled using "Hexagonal Architecture"
These are the main components/packages:

- **application**: The "hexagon" that contains the business logic and use cases
- **adapter**: Contains all the adapters, for instance the web adapter (Spring controller) and Github client.
    - in: contains the incoming/drivers adapters
    - out: contains the outgoing/driven adapters
- **configuration**: Configuration to improve performance of RestTemplate. It allows to use a connection pool
  instead of creating new connections for every request.

### Testing

- [Controller test](src/test/java/com/shopapotheke/githubpoprepo/adapter/in/rest/GithubControllerTest.java)
- [Usecase tests](src/test/java/com/shopapotheke/githubpoprepo/application/usecase/GetGithubPopularRepositoriesUseCaseTest.java)
- [Adapter tests](src/test/java/com/shopapotheke/githubpoprepo/adapter)

## Endpoints

* Get popular repositories
  * GET [localhost:8080/api/pop](localhost:8080/api/pop)
  * QueryParams:
    * date: The date from which you fetch the data. Format: yyyy-MM-dd
    * language: The programming language
    * top: The number of possible results. Possible values: 10, 50, 100

Example:

```shell
      curl GET "localhost:8080/api/pop?date=2000-01-01&language=haskell&top=10" \
``` 

## Observations/Considerations

* Hexagonal architecture, allows to decouple the business logic from implementations details. It helps to enforce 
separation of concerns and allows to easily switch between different implementations(plugin architecture).

* When calling external services using a web client, such as RestTemplate, it is important to consider some optimizations.
In this case some custom configurations were implemented, such as defining a Connection pool for doing the rest calls.
This can significantly improve the performance of the service. See: [RestTemplate improvement](https://medium.com/@nitinvohra/how-to-improve-performance-of-spring-resttemplate-6af37e0a0f33)

* When dealing with external services, it is important to consider that lot of things can go wrong.
Network issues, service downtime, congestion  etc. Therefore, it is important to handle these situations.
For this `resilience4j` is used, using retries with exponential backoff and also using circuit breakers.






