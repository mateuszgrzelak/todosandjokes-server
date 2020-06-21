# TodosAndJokes

Server side project of web application deployed on heroku.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development purposes.

### Prerequisites

All you need to have are [JDK](https://www.oracle.com/pl/java/technologies/javase-downloads.html) (minimum version 1.8, recommended 11), [Maven](https://maven.apache.org/index.html) and [PostgreSQL](https://www.postgresql.org/download/). All steps required to install software are listed on official websites.

## Build and run

In order to install all required dependencies and build this project you need to clone this repository and in Command Line Interpreter enter:

```
mvn package
java -jar target/api.jar
```

## Endpoints

Application supports following endpoints:

| Name              | Method  | Description                                  | Authorization   |
|:------------------|:--------|:---------------------------------------------|:---------------:|
| /login            | POST    | return jwt if login and password are correct | ✗               |
| /registration     | POST    | create user account                          | ✗               |
| /todos            | GET     | get all todos                                | ✓               |
| /todos/{index}    | GET     | get todo by {index}                          | ✓               |
| /todos/{index}    | DELETE  | delete todo by {index}                       | ✓               |
| /todos/{index}    | PUT     | update todo by {index}                       | ✓               |
| /todos            | POST    | create new todo                              | ✓               |
| /jokes            | GET     | get 10 jokes                                 | ✓               |
