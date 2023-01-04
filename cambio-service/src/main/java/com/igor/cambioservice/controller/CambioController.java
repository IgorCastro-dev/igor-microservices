package com.igor.cambioservice.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igor.cambioservice.model.Cambio;
import com.igor.cambioservice.repositories.CambioRepository;

@RestController
@RequestMapping("cambio-service")
public class CambioController {
	
	@Autowired
	private Environment enviroment;
	
	@Autowired
	private CambioRepository cambioRepository;
	
	//http://localhost:8000/cambio-service/5/USD/BRL
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(
			@PathVariable("amount") BigDecimal amount,
			@PathVariable("from") String from,
			@PathVariable("to") String to
			){
		var cambio = cambioRepository.findByFromAndTo(from, to);
		if (cambio == null) throw new RuntimeException("currency Unsuported");
		
		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);
		
		var port = enviroment.getProperty("local.server.port");
		
		cambio.setConvertedValue(convertedValue.setScale(2,RoundingMode.CEILING));
		cambio.setEnviroment(port);
		return cambio;
	}
	
}
