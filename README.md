# Maven Multi-Module Microservices Demo

A beginner-friendly project containing:

- 3 Spring Boot microservices
  - `customer-service` — port `8081`
  - `order-service` — port `8082`
  - `notification-service` — port `8083`
- 2 reusable Maven libraries
  - `common-models`
  - `common-utils`
- One parent/aggregator Maven `pom.xml`
- One Dockerfile per microservice
- `docker-compose.yml` for running all three services

## Project structure

```text
microservices-platform/
├── pom.xml
├── libraries/
│   ├── common-models/
│   │   ├── pom.xml
│   │   └── src/main/java/...
│   └── common-utils/
│       ├── pom.xml
│       └── src/main/java/...
├── services/
│   ├── customer-service/
│   │   ├── pom.xml
│   │   ├── Dockerfile
│   │   └── src/main/...
│   ├── order-service/
│   │   ├── pom.xml
│   │   ├── Dockerfile
│   │   └── src/main/...
│   └── notification-service/
│       ├── pom.xml
│       ├── Dockerfile
│       └── src/main/...
└── docker-compose.yml
```

## Dependency picture

```text
                    Parent POM
                       |
       +---------------+---------------+
       |               |               |
 common-models    common-utils        services
       |               |               |
       +--------+------+---------------+
                |
     +----------+-----------+
     |          |           |
 customer    order      notification
 service     service       service
                |
                | HTTP
                +--------> customer-service
                |
                | HTTP
                +--------> notification-service
```

The shared libraries are Maven JAR modules. They are **not microservices** and do not run by themselves.

## Build everything

Requires Java 21 and Maven.

```bash
mvn clean package
```

Because the root POM lists all modules, Maven builds the libraries before the services that depend on them.

## Run without Docker

Open three terminals.

### Terminal 1

```bash
java -jar services/customer-service/target/customer-service-1.0.0-SNAPSHOT.jar
```

### Terminal 2

```bash
java -jar services/notification-service/target/notification-service-1.0.0-SNAPSHOT.jar
```

### Terminal 3

```bash
java -jar services/order-service/target/order-service-1.0.0-SNAPSHOT.jar
```

## Test locally

A demo customer with ID `c1` is created automatically.

Get the customer:

```bash
curl http://localhost:8081/customers/c1
```

Create an order:

```bash
curl -X POST \
  "http://localhost:8082/orders?customerId=c1&product=Laptop&quantity=1"
```

What happens:

1. You call `order-service`.
2. `order-service` calls `customer-service`.
3. `customer-service` returns customer information.
4. `order-service` creates the order.
5. `order-service` calls `notification-service`.
6. `notification-service` prints a notification message.
7. `order-service` returns the created order.

## Run with Docker Compose

From the project root:

```bash
docker compose up --build
```

Then test:

```bash
curl http://localhost:8081/customers/c1
```

```bash
curl -X POST \
  "http://localhost:8082/orders?customerId=c1&product=Phone&quantity=2"
```

Stop everything:

```bash
docker compose down
```

## Why the Docker build context is the project root

Each service uses shared Maven modules.

For example, `order-service` needs:

- `common-models`
- `common-utils`

Therefore Docker must be able to see the parent POM and the shared library source while building the service.

That is why Compose uses:

```yaml
build:
  context: .
  dockerfile: services/order-service/Dockerfile
```

instead of using only the service directory as the Docker build context.

## Maven idea to remember

The root POM has:

```xml
<packaging>pom</packaging>
```

and:

```xml
<modules>
    ...
</modules>
```

That means:

> "These modules belong to one Maven build."

Inside a service POM, a normal Maven `<dependency>` means:

> "This service needs code from this particular library."

So:

- `<modules>` = project/build organization
- `<dependency>` = code dependency
