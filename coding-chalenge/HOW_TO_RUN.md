# How to Run

## Requirements

- Java 21
- No need to install Gradle. Use the Gradle wrapper (`./gradlew`).

## Run the tests

```bash
./gradlew test
```

## Start the application

```bash
./gradlew bootRun
```

Or build a jar and run it:

```bash
./gradlew build
java -jar build/libs/range-kata.jar
```

The application starts on port `8080`.

## Use the API

`POST /api/range/contains` checks if a range contains a value.

Request body:

| Field   | Description                                    |
|---------|------------------------------------------------|
| `type`  | `INTEGER`, `DECIMAL`, `DATE` or `STRING`       |
| `range` | The range, in the same format as `Range#toString()` |
| `value` | The value to check                             |

Example:

```bash
curl -X POST http://localhost:8080/api/range/contains \
  -H 'Content-Type: application/json' \
  -d '{"type": "INTEGER", "range": "[1, 5)", "value": "3"}'
```

Response:

```json
{"contains": true}
```

More examples:

| `type`    | `range`                    | `value`      |
|-----------|----------------------------|--------------|
| `INTEGER` | `[Infinity, 100)`          | `-9000`      |
| `DECIMAL` | `(1.5, 2.5]`               | `2.50`       |
| `DATE`    | `[2020-01-01, 2020-12-31]` | `2020-06-15` |
| `STRING`  | `(abc, xyz)`               | `mno`        |

If the request is not valid, the API returns `400 Bad Request` with the reason:

```json
{"type": "about:blank", "title": "Bad Request", "status": 400, "detail": "Invalid range format: [1, 5", "instance": "/api/range/contains"}
```

## API documentation

When the application is running:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI specification: http://localhost:8080/v3/api-docs
