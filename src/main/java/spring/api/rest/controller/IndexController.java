package spring.api.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.api.rest.model.Users;

//annotation is used to create RESTful web services 
@RestController
@RequestMapping(value = "/user")
public class IndexController {
	
	/* Restfull service */
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<Users> init() {
		
		Users user = new Users();
		user.setId(1L);
		user.setLogin("Admin");
		user.setName("Administrator");
		user.setPassword("123");
		
		Users user1 = new Users();
		user1.setId(2L);
		user1.setLogin("Admin1");
		user1.setName("Administrator1");
		user1.setPassword("1234");
		
		List<Users> users = new ArrayList<Users>();
		users.add(user);
		users.add(user1);

		return new ResponseEntity(users, HttpStatus.OK);
	}

}
