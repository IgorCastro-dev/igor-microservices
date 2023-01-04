package com.igor.bookservice.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.igor.bookservice.model.Book;
import com.igor.bookservice.proxy.CambioProxy;
import com.igor.bookservice.repositories.BookRepository;


@RestController
@RequestMapping("book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CambioProxy cambioProxy;
	
	
	
	//http://localhost:8100/book-service/14/BRL
	@GetMapping("/{id}/{currency}")
	public Book findBook(
			@PathVariable("id") Long id,
			@PathVariable("currency") String currency
			){
		var bookOptional = bookRepository.findById(id);
		var book = bookOptional.get();
		if (book == null) throw new RuntimeException("Book Notfound");
		
		var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);
		
		var port = environment.getProperty("server.port" + "FEING");
		book.setEnviroment(port);
		book.setPrice(cambio.getConvertedValue());
		return book;
		
	}
	
	
	
	/**
	//http://localhost:8100/book-service/14/BRL
	@GetMapping("/{id}/{currency}")
	public Book findBook(
			@PathVariable("id") Long id,
			@PathVariable("currency") String currency
			){
		var bookOptional = bookRepository.findById(id);
		var book = bookOptional.get();
		System.out.println(book);
		if (book == null) throw new RuntimeException("Book Notfound");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
		
		var response = new RestTemplate()
		.getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
				Cambio.class,
				params);
		
		var cambio = response.getBody();
		
		var port = environment.getProperty("server.port");
		book.setEnviroment(port);
		book.setPrice(cambio.getConvertedValue());
		return book;
		
	}
	
	**/
	
}
