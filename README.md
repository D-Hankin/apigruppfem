# README: How to Clone, Open, and Run the Quarkus API using Visual Studio Code

## Overview

This README provides simplified instructions on how to clone, open, and run a Quarkus API from the "apigruppfem" repository on GitHub, created by user "D-Hankin", using Visual Studio Code (VS Code). The API contains information about cities, including reviews and ratings.

### Developers

- **D-Hankin**
- **Kribbz0r**
- **Code-Cadde**
- **codejac95**

### API Description

The API contains information about cities, including reviews and ratings. Developers have the capability to perform the following actions:

- **Add Cities:** Developers can add new cities to the API, providing information such as city name, description, and associated reviews and ratings.

- **Edit Cities:** Existing city information, reviews, and ratings can be modified by developers to keep the data up-to-date.

- **Remove Cities:** Developers can remove cities from the API, along with their associated reviews and ratings.

- **Add Reviews:** Developers can add reviews for specific cities, including details such as comments and ratings.

- **Edit Reviews:** Existing reviews can be edited by developers to reflect changes or updates.

- **Remove Reviews:** Developers have the ability to remove reviews associated with cities.

### Prerequisites

Before you begin, make sure you have the following installed on your system:

- Git: [https://git-scm.com/](https://git-scm.com/)
- Docker: [https://www.docker.com/get-started](https://www.docker.com/get-started)
- Visual Studio Code: [https://code.visualstudio.com/](https://code.visualstudio.com/)

### Clone the Repository

1. Open a terminal window.
2. Navigate to the directory where you want to clone the repository.
3. Run the following command to clone the repository: `git clone https://github.com/D-Hankin/apigruppfem.git`

### Open the Project in Visual Studio Code

1. Open Visual Studio Code.
2. Click on the "File" menu and select "Open Folder."
3. Navigate to the folder where you cloned the "apigruppfem" repository and select it.

### Build and Run the Quarkus API

1. **Ensure Docker is running on your machine before proceeding.**
2. Open the terminal in VS Code.
3. Navigate to the project directory using the following command: `cd apigruppfem`
4. Run the Quarkus application: `./mvnw compile quarkus:dev` (This command starts the Quarkus development mode.)
5. The Quarkus API should now be running.

Now you have successfully cloned, opened, and run the Quarkus API from the "apigruppfem" repository using Visual Studio Code. Quarkus handles the Docker build step automatically, simplifying the process for you.


*****Allt här under skapas automatiskt från Quarkus****** 

# apigruppfem

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/apigruppfem-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- Narayana JTA - Transaction manager ([guide](https://quarkus.io/guides/transaction)): Offer JTA transaction support (included in Hibernate ORM)
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, Jakarta Persistence)
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing Jakarta REST and more
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC
