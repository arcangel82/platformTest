# ITX Comercial API

This project is a Spring Boot application that provides an API for retrieving products sorted by a weighted combination
of sales and stock criteria.

## Architecture

The project follows a hexagonal architecture (also known as ports and adapters) to achieve maximum decoupling between
the different layers of the application. This architecture allows for better testability, maintainability, and
flexibility.

### Layers

- **Domain Layer**: Contains the core business logic and domain models.
    - `domain/model`: Domain entities
    - `domain/service`: Domain services that implement core business logic

- **Application Layer**: Contains the use cases and ports.
    - `application/port/in`: Input ports (interfaces for use cases)
    - `application/port/out`: Output ports (interfaces for external systems)
    - `application/service`: Use case implementations

- **Infrastructure Layer**: Contains the adapters for external systems.
    - `infrastructure/adapter/in/web`: Web adapters (controllers)
    - `infrastructure/adapter/out/persistence`: Persistence adapters (repositories)

### API First Approach

The project follows an API-first approach, using OpenAPI to define the API contract. The OpenAPI specification is
located at `src/main/resources/openapi/servers/api-product-openapi.json`. The controller interfaces and DTOs are
generated from this specification using the OpenAPI Generator Maven plugin.

## Technologies

- Java 17
- Spring Boot 3.4.1
- Spring Data JPA
- H2 Database
- MapStruct
- Lombok
- JUnit 5
- Mockito
- Docker

## Building and Running

### Prerequisites

- Java 17 or higher
- Maven
- Docker (optional)

### Building

To build the application, run:

```bash
./mvnw clean package
```

### Running

#### Using Maven

```bash
./mvnw spring-boot:run
```

#### Using Java

```bash
java -jar target/pruebaITXComercial-0.0.1-SNAPSHOT.jar
```

#### Using Docker

```bash
docker build -t itx-comercial-api .
docker run -p 8081:8081 itx-comercial-api
```

#### Using Docker Compose

```bash
docker-compose up
```

### Refreshing the Docker Image

When you make changes to the application code and want to refresh the Docker image with the new version, you can use the
provided script:

```bash
./refresh-docker-image.sh
```

This script will:

1. Build the application with Maven
2. Stop any running containers
3. Rebuild the Docker image without using cache
4. Start the containers with the new image

Alternatively, you can manually perform these steps:

```bash
# Build the application
./mvnw clean package -DskipTests

# Stop running containers
docker-compose down

# Rebuild the image
docker-compose build --no-cache

# Start containers with the new image
docker-compose up -d
```

## API Endpoints

### Get Products Sorted (using headers)

```
GET /product
```

#### Headers

- `saleFactor` (required): Weight for sales criterion
- `stockFactor` (required): Weight for stock criterion

#### Response

```json
{
  "data": [
    {
      "id": 5,
      "name": "CONTRASTING LACE T-SHIRT",
      "sales": 650,
      "stock": {
        "sizeS": 0,
        "sizeM": 1,
        "sizeL": 0
      }
    },
    {
      "id": 1,
      "name": "V-NECH BASIC SHIRT",
      "sales": 100,
      "stock": {
        "sizeS": 4,
        "sizeM": 9,
        "sizeL": 0
      }
    }
  ]
}
```

### Get Products Sorted (using request body)

```
POST /product
```

#### Request Body

```json
{
  "criteria": [
    {
      "id": "salesUnits",
      "weight": 10
    },
    {
      "id": "stockRatio",
      "weight": 5
    }
  ]
}
```

#### Response

Same as GET /product endpoint.

### Get Available Sorting Criteria

```
GET /product/criteria
```

#### Response

```json
{
  "data": [
    {
      "id": "salesUnits",
      "name": "Sales Units",
      "description": "Scores products based on number of units sold"
    },
    {
      "id": "stockRatio",
      "name": "Stock Ratio",
      "description": "Scores products based on available sizes with stock"
    }
  ]
}
```

## Testing

### Running Tests

```bash
./mvnw test
```

### Postman Collection

A Postman collection is included in the project for testing the API. Import the `postman_collection.json` file into
Postman to use it.

## URLs

- API: http://localhost:8081/product
- Swagger UI: http://localhost:8081/swagger-ui.html
- H2 Console: http://localhost:8081/h2-console (JDBC URL: jdbc:h2:mem:ItxTecnica, Username: sa, Password: passsword)

## Design Decisions

### Sorting Algorithm

The sorting algorithm uses a weighted combination of criteria:

1. Sales Units: The number of units sold
2. Stock Ratio: The number of sizes that have stock (0-3)

The formula for calculating the score is:

```
score = sum(criterionValue * criterionWeight) for each criterion
```

Products are sorted in descending order by this score.

### Strategy Pattern for Sorting

The project uses the Strategy pattern to implement different sorting criteria. This design pattern allows for:

- Encapsulation of different sorting algorithms in separate classes
- Easy addition of new sorting criteria without modifying existing code
- Runtime selection of sorting strategies based on user input

The implementation consists of:

- A `SortingCriterion` interface that defines the strategy contract
- Concrete implementations for each criterion (e.g., `SalesUnitsCriterion`, `StockRatioCriterion`)
- A `ProductSortingService` that uses these strategies to calculate weighted scores

This approach makes the codebase more maintainable and extensible, as new sorting criteria can be added by simply
creating a new class that implements the `SortingCriterion` interface.

### Multiple API Endpoints

The API provides multiple endpoints for flexibility:

1. GET /product with header parameters (original endpoint for backward compatibility)
2. POST /product with request body (new endpoint for more flexible criteria specification)
3. GET /product/criteria to discover available sorting criteria

This allows clients to choose the most appropriate way to interact with the API based on their needs.

### Hexagonal Architecture

The hexagonal architecture was chosen to achieve maximum decoupling between the different layers of the application.
This allows for:

- Better testability: Each layer can be tested in isolation
- Better maintainability: Changes in one layer don't affect other layers
- Better flexibility: Implementations can be swapped without affecting the core business logic

### API First Approach

The API-first approach was chosen to ensure that the API contract is well-defined and documented. This allows for:

- Better collaboration between frontend and backend teams
- Better documentation
- Code generation for controllers and DTOs

### MapStruct

MapStruct was chosen for object mapping to reduce boilerplate code and improve maintainability. It generates efficient
mapping code at compile time, avoiding reflection and improving performance.

### Docker

Docker was chosen for containerization to ensure that the application runs consistently across different environments.
The multi-stage build process in the Dockerfile minimizes the final image size.

## Future Improvements

- Add pagination to the API
- Add filtering options
- Add caching
- Add authentication and authorization
- Add more comprehensive error handling
- Add more comprehensive logging
- Add more comprehensive monitoring
- Add support for additional sorting criteria beyond sales and stock
