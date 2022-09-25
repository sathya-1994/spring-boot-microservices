package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConvertionController {

	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConvertion calculateCurrencyConvertion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		HashMap<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConvertion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConvertion.class, uriVariables);

		CurrencyConvertion currencyConverionValue = responseEntity.getBody();
		return new CurrencyConvertion(currencyConverionValue.getId(), currencyConverionValue.getFrom(),
				currencyConverionValue.getTo(), quantity, currencyConverionValue.getConvertionMultiple(),
				quantity.multiply(currencyConverionValue.getConvertionMultiple()),
				currencyConverionValue.getEnvironment() + " rest template");
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConvertion calculateCurrencyConvertionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		CurrencyConvertion currencyConverionValue = proxy.retriveExchangeValue(from, to);
		return new CurrencyConvertion(currencyConverionValue.getId(), currencyConverionValue.getFrom(),
				currencyConverionValue.getTo(), quantity, currencyConverionValue.getConvertionMultiple(),
				quantity.multiply(currencyConverionValue.getConvertionMultiple()),
				currencyConverionValue.getEnvironment() + " feign");
	}
}
