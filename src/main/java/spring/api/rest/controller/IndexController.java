package spring.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.api.rest.model.Users;
import spring.api.rest.repository.UserRepository;

//access permissions
@CrossOrigin
//annotation is used to create RESTful web services 
@RestController 
@RequestMapping(value = "/user")
public class IndexController {
	
	//This annotation allows Spring to resolve and inject collaborating beans into your bean.
	@Autowired
	private UserRepository userRepository;
	
	/*** Example  GET ***/
	
	/* Restful service */
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Users> find(@PathVariable (value = "id") Long id) {
		
		Optional<Users> users = userRepository.findById(id);
		
		return new ResponseEntity<Users>(users.get(), HttpStatus.OK);
	}
	
	//Returns all users
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Users>> user(){
		
		List<Users> list = (List<Users>) userRepository.findAll();
		
		return new ResponseEntity<List<Users>>(list, (HttpStatus.OK));
	}
	
	/*** Example  POST ***/
	
	//access permissions only for local machine
	@CrossOrigin(origins = "localhost:8080")
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Users> register(@RequestBody Users users){
	
		/*
		for(int pos = 0; pos < users.getTelephones().size(); pos ++) {
			users.getTelephones().get(pos).setUsers(users);
		} 
		*/
		
		//associate with phone
		users.getTelephones().forEach(t -> t.setUsers(users));
		
		Users userSalve = userRepository.save(users);
		return new ResponseEntity<Users>(userSalve, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/{iduser}/idsale/{idsale}", produces = "application/json")
	public ResponseEntity<Users> registerSale(@PathVariable Long iduser, @PathVariable Long idsale){
		
		return new ResponseEntity("id user :" + iduser + " idvenda "+ idsale, HttpStatus.OK);
		
	}
	
	/*** Example  PUT ***/
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Users> update(@RequestBody Users users){
		
		//associate with phone
		users.getTelephones().forEach(t -> t.setUsers(users));
		Users userSalve = userRepository.save(users);
		return new ResponseEntity<Users>(userSalve, HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/{iduser}/idsale/{idsale}", produces = "application/json")
	public ResponseEntity<Users> updateSale(@PathVariable Long iduser, @PathVariable Long idsale){
		
		return new ResponseEntity("Update Venda "+ idsale, HttpStatus.OK);
		
	}
	
	/*** Example  DELETE ***/
		
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public String delete(@PathVariable (value = "id") Long id) {
		
		userRepository.deleteById(id);
		
		return "OK";
	}
	
	@DeleteMapping(value = "/{id}/sale", produces = "application/text")
	public String deleteSale(@PathVariable (value = "id") Long id) {
		
		userRepository.deleteById(id);
		
		return "OK";
	}
}
