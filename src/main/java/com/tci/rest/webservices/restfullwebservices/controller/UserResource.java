package com.tci.rest.webservices.restfullwebservices.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.tci.rest.webservices.restfullwebservices.beans.User;
import com.tci.rest.webservices.restfullwebservices.exception.UserNotFoundException;
import com.tci.rest.webservices.restfullwebservices.service.UserDaoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	public final static Log LOG = LogFactory.getLog(UserResource.class);

	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if(user==null)
			throw new UserNotFoundException("id-"+id);
				
		Resource<User> resource = new Resource<User>(user);		
		
		ControllerLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		LOG.info("deleteUser: LinkTo: "+linkTo);
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		if(user==null)
			throw new UserNotFoundException("id-"+id);
		
		return user;
	}
	
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequestUri().
		path("/{id}")
		.buildAndExpand(savedUser.getId()).toUri();
		LOG.info("createUser: "+location);
		
		return ResponseEntity.created(location).build();
	}

}
