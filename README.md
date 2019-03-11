# hello-world-spring-kotlin-akka
Hello world to show akka + kotlin + spring

* run application mvnw spring-boot:run
* open http://localhost:8080/abc
* expected outcome: Hello abc !

Some interesting aspects:
* Endpoint is reactive (returns Mono)
* An Actor is created and destroyed per request with Mono.using
  
