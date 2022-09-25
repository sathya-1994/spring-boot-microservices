package com.in28minutes.microservices.currencyexachangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBrakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBrakerController.class);

	@GetMapping("/sample-api")
//	@Retry(name="sample-api", fallbackMethod = "hardcodedResponse")
	@RateLimiter(name = "default")
	@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
	public String sampleApi() {
		logger.info("Some API call recived");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8000/some-dummy-url",
				String.class);
		return forEntity.getBody();
	}

	public String hardcodedResponse(Exception e) {
		return "fallback-response";
	}
}
