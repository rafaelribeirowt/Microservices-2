package com.raeltecnologia.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raeltecnologia.model.Cambio;
import com.raeltecnologia.repository.CambioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio Service endpoint")
@RestController
@RequestMapping("cambio-service")
public class CambioController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CambioRepository repo;
	
	@Operation(summary = "Convesion", description = "currency")
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(
							@PathVariable("amount")BigDecimal amount,
							@PathVariable("from")String from,
							@PathVariable("to")String to
							) {
		
		var cambio = repo.findByFromAndTo(from, to);
		if(cambio == null) throw new RuntimeException("Currency Unsupported");
		
		var port = env.getProperty("local.server.port");
		BigDecimal comversionFactor = cambio.getConversionFactor();
		BigDecimal conversionValue = comversionFactor.multiply(amount);
		cambio.setConvertedValue(conversionValue.setScale(2, RoundingMode.CEILING));
		cambio.setEnvironment(port);
		
		return cambio;
	}
	

	
	
	
}
