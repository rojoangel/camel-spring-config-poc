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
