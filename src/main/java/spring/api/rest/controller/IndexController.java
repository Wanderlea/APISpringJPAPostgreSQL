package spring.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.api.rest.model.Users;
import spring.api.rest.repository.UserRepository;

//annotation is used to create RESTful web services 
@RestController
@RequestMapping(value = "/user")
public class IndexController {
	
	//This annotation allows Spring to resolve and inject collaborating beans into your bean.
	@Autowired
	private UserRepository userRepository;
	
	/* Restfull service */
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Users> init(@PathVariable (value = "id") Long id) {
		
		Optional<Users> users = userRepository.findById(id);
		
		return new ResponseEntity<Users>(users.get(), HttpStatus.OK);
	}
	
	//Returns all users
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Users>> user(){
		
		List<Users> list = (List<Users>) userRepository.findAll();
		
		return new ResponseEntity<List<Users>>(list, (HttpStatus.OK));
	}
	

}
