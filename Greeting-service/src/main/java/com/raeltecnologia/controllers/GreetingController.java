package com.raeltecnologia.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raeltecnologia.configuration.GreetingConfiguration;
import com.raeltecnologia.model.Greeting;

@RestController
public class GreetingController {

	private static final String template = "%s, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	
	@Autowired
	private GreetingConfiguration conf;
	
	
	@RequestMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value="name",
			defaultValue = "") String name) {
		
		if(name.isEmpty()) name= conf.getDefaultValue();
		
		return new Greeting(
					counter.incrementAndGet(),
					String.format(template, conf.getGreeting(), name));
	}
}
