package spring.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//annotation is used to create RESTful web services 
@RestController
@RequestMapping(value = "/user")
public class IndexController {
	
	/* Restfull service */
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init(@RequestParam(value = "nome", required = false) String nome) {
		System.out.println("Paramentro: "+ nome);
		return new ResponseEntity("Hello User : "+ nome, HttpStatus.OK);
	}

}
