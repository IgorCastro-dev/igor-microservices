package com.igor.calculadora.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calc")
public class CalcController {
	
	@GetMapping(value = "/sum/{numberOne}/{numberTwo}")
	public Double somar(@PathVariable("numberOne") Double numberOne, @PathVariable("numberTwo") Double numberTwo) {
		Double sum = numberOne + numberTwo;
		return sum;
	}
}
