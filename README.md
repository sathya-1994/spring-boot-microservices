# spring-boot-microservices
Microservices using Spring Boot

## Installing Tools

##### Recommendations

- Use **latest version** of Java
- Use **latest version** of "Eclipse IDE for Enterprise Java Developers"
- Remember: Spring Boot 3+ works only with Java 17+

### Introduction
Spring Boot has a lot of magic going for it. Developing applications with it is cool and fun.

Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can “just run”. Most Spring Boot applications need very little Spring configuration.

This repository contains cool things about Spring boot and microservices developed using Spring Boot (4 projects).
- currency-conversion-service  and currency-conversion-service are microservices(currency-conversion-service depends on currency-exachange-service)
- api-gateway which will expose above microservices
- naming-server is a Eureka naming serve

## Topics covered
- Spring Boot 2.4.x+ & Spring Cloud 2020.x+
  - Service Registry using Eureka Naming Server
  - Load Balancing with Spring Cloud LoadBalancer (replacing Ribbon)
  - API Gateway with Spring Cloud Gateway (instead of Zuul)
  - Circuit Breaker with Resilience4j (instead of Hystrix)
  - Distributed Tracing with Zipkin

## Running Examples
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the right project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application
- You are all Set

#### URLs
- naming-server:
     - Java main method: com.in28minutes.microservices.namingserver.NamingServerApplication.main(String[])
 
    
- currency-exachange-service: Before currency exchange service, we have to start naming-server
    - Java main method:com.in28minutes.microservices.currencyexachangeservice.CurrencyExachangeServiceApplication.main(String[])
    - http://localhost:8000/currency-exchange/from/USD/to/INR
    - http://localhost:8000/currency-exchange/from/INR/to/USD
    - http://localhost:8000/currency-exchange/from/INR/to/EUR

   
   
- currency-conversion-service: Before currency conversion service, we have to start naming-server(at least one instance of currency-exachange-service) 
     - Java main method: com.in28minutes.microservices.currencyconversionservice.CurrencyConversionServiceApplication.main(String[])
     - http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
     
     
- api-gateway: We have to start naming-server then currency-exchange-service (also we can launch multiple instances of currency-exchange-service in different ports) and then currency-conversion-service. Finally we have to start api-gateway
     - Java main method: com.in28minutes.microservices.apigateway.ApiGatewayApplication.main(String[])
     - http://localhost:8765/currency-exchange/from/USD/to/INR
     - http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
     - http://localhost:8765/currency-conversion/from/INR/to/USD/quantity/10
     - http://localhost:8765/currency-conversion/from/INR/to/EUR/quantity/10

- Sample Responses: 
     - http://localhost:8000/currency-exchange/from/USD/to/INR 
       ```
       [
          {
				"id":10003,
				"from":"INR",
				"to":"EUR",
				"quantity":10,
				"convertionMultiple":10.00,
				"totalCalculatedAmount":100.00,
				"environment":"8000 rest template"
		     }
		]
       ```
       
     - http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
     ```
     [
	     {
			"id":10001,
			"from":"USD",
			"to":"INR",
			"quantity":10,
			"convertionMultiple":65.00,
			"totalCalculatedAmount":650.00,
			"environment":"8000 rest template"
		  }
     ]
     ```
