package com.raeltecnologia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raeltecnologia.model.Book;
import com.raeltecnologia.proxy.CambioProxy;
import com.raeltecnologia.repository.BookRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book service endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {
	
	@Autowired
	private BookRepository repo;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CambioProxy proxy;
	
	@Operation(summary = "Finf a Specific book by your id")
	@GetMapping(value="/{id}/{currency}")
	public Book findBook(
				@PathVariable("id") Long id,
				@PathVariable("currency") String currency) {
		
		
		var book = repo.getById(id);
		if(book == null) throw new RuntimeException("Book not found");

		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		
		var port = env.getProperty("local.server.port");
		book.setEnvironment("Book port: " + port + "Cambio port: "+ cambio.getEnvironment());
		book.setPrice(cambio.getConvertedValue());
		return book;
		
		
		
		
		
	}
//	@GetMapping(value="/{id}/{currency}")
//	public Book findBook(
//			@PathVariable("id") Long id,
//			@PathVariable("currency") String currency) {
//		
//		
//		var book = repo.getById(id);
//		if(book == null) throw new RuntimeException("Book not found");
//		
//		HashMap<String, String> params = new HashMap<>();
//		params.put("amount", book.getPrice().toString());
//		params.put("from", "USD");
//		params.put("to", currency);
//		
//		var response = new RestTemplate().
//				getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
//						Cambio.class,
//						params );
//		var cambio = response.getBody();
//		
//		var port = env.getProperty("local.server.port");
//		book.setEnvironment(port);
//		book.setPrice(cambio.getConvertedValue());
//		return book;
	}


