Apache Camel & Spring Cloud Config Proof of Concept
===================================================

The project consists in two modules:

## config-service
It is an spring-boot application serving properties using spring cloud config server

To start the service use

    make run

The service 
- will be available at port 8888
- will serve properties from the `*.properties files in the root folder
- can be tested in the following urls 
  - [/configurable-camel/default](http://localhost:8888/configurable-camel/default)
  - [/configurable-camel/prod](http://localhost:8888/configurable-camel/prod)
    
## configurable-camel
It is an apache camel spring boot application consuming acting as client of the config-service 

To start the application with the `default` profile active use

    make run

To start the application with the `prod` profile active use

    make run-prod

The application
- responds to GET requests to 
  - [/health-property](http://localhost:8080/health-property) - where a property value is directly used in the camel route, and
  - [/health-bean](http://localhost:8080/health-bean) - where a property value is used to initialize a bean and the retrieved in the camel route 

and on the management port - 9999
- responds to GET requests to [/actuator/health](http://localhost:9999/actuator/health)
- responds to GET requests to [/actuator/env](http://localhost:9999/actuator/env)
- returns application information - **including version** - at [/actuator/info](http://localhost:9999/actuator/info)
- refreshes its properties via POST requests to [/actuator/refresh](http://localhost:9999/actuator/refresh) 

To refresh the application properties use

    curl localhost:9999/actuator/refresh -d {} -H "Content-Type: application/json"

The application exposes via HTTP all the management (actuator/*) endpoints - they can be discovered at [/actuator/](http://localhost:9999/actuator/)

Here the full list can be found at [Spring Boot Actuator Endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)

E.g. to change un runtime the logging level use

    curl -i -X POST -H 'Content-Type: application/json' -d '{"configuredLevel": "INFO"}' http://localhost:9999/actuator/loggers/ROOT

## Notes
To have the property values injected to a bean working the following needed to be done:
- Add the bean to the `refresh` scope
- Configure the bean with `aop:scoped-proxy` - more details in [Spring Config Client XML equivalent of @RefreshScope](https://stackoverflow.com/questions/41018511/spring-config-client-xml-equivalent-of-refreshscope).
- Resolve property values using the `@Value` annotation instead of injecting them via constructor or setter - more details in [Refreshed property gets overridden with XML bean definitions](https://github.com/spring-cloud/spring-cloud-commons/issues/207)