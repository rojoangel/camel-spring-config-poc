Apache Camel & Spring Cloud Config Proof of Concept
===================================================

The project consists in two modules:

## config-service
It is an spring-boot application serving properties using spring cloud config server

To start the service use

    make run

The service 
- will be available at port 8888
- will serve properties from the *.properties files in the root folder
- can be tested in the following urls 
  - http://localhost:8888/configurable-camel/default
  - http://localhost:8888/configurable-camel/prod
    
## configurable-camel
It is an apache camel spring boot application consuming acting as client of the config-service 

To start the application with the `default` profile active use

    make run

To start the application with the `prod` profile active use

    make run-prod

The application
- will respond to GET requests to http://localhost:8080/health
- will refresh its properties via POST to http://localhost:9999/actuator/refresh

and
- responds to GET requests to http://localhost:9999/actuator/health
- responds to GET requests to http://localhost:9999/actuator/env

To refresh the application properties use

    curl localhost:9999/actuator/refresh -d {} -H "Content-Type: application/json"

The application exposes via HTTP all the management (actuator/*) endpoints - they can be discovered at http://localhost:9999/actuator/

Here the full list can be found: https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html

E.g. to change un runtime the logging level use

    curl -i -X POST -H 'Content-Type: application/json' -d '{"configuredLevel": "INFO"}' http://localhost:9999/actuator/loggers/ROOT