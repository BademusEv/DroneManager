# Drone Manager Service Guide

## Required software:

- openjdk 17.0.4+
- Docker 23.0.5+
- docker and java needs to be on your path

## How To:

- for Windows use `mvnw.cmd` instead './mvnw'

### How to run the app

- ./mvnw spring-boot:run

### How to run the app's tests

- ./mvnw clean test

### How to build *jar*

- ./mvnw clean package -DskipTests

### How to connect to DB manually

- mongosh --host "localhost:63680" -u 'root' -p 'secret'
- use DronesDB

## URI's:

- Swagger available by the [url](http://localhost:8080/)

## Additional Info:

- MongoDB starts via docker using Spring Boot 3. Configuration stores in the compose.yaml file.
- MongoDB starts inside a docker container on the port 27017, the port is forwarding to 63680.
- By default, the app uses demo profile, that allows to load initial data to the MongoDB if the DB
  is empty.
- Demo profile allows to run DroneBatteryController that mimics the drone API,
  providing a battery check by sending a REST request.