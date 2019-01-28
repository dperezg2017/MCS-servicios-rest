package com.tci.rest.webservices.restfullwebservices.controller;

import com.tci.rest.webservices.restfullwebservices.beans.Post;
import com.tci.rest.webservices.restfullwebservices.beans.User;
import com.tci.rest.webservices.restfullwebservices.exception.UserNotFoundException;
import com.tci.rest.webservices.restfullwebservices.repository.PostRepository;
import com.tci.rest.webservices.restfullwebservices.repository.UserRepository;
import com.tci.rest.webservices.restfullwebservices.service.UserDaoService;
import javafx.geometry.Pos;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAResource {
	
	public final static Log LOG = LogFactory.getLog(UserJPAResource.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<User> retrieveAllJPAUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public Resource<User> retrieveJPAUser(@PathVariable int id) {

		Optional <User> user = userRepository.findById(id);

		if(!user.isPresent())
			throw new UserNotFoundException("id-"+id);
				
		Resource<User> resource = new Resource<User>(user.get());
		
		ControllerLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllJPAUsers());
		LOG.info("deleteUser: LinkTo: "+linkTo);
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteJPAUser(@PathVariable int id) {
		 userRepository.deleteById(id);
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
//		.fromCurrentRequestUri()|
		.path("/{id}")
		.buildAndExpand(savedUser.getId()).toUri();
		LOG.info("createUser: "+location);
		
		return ResponseEntity.created(location).build();
	}


	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveJPAAllUser(@PathVariable int id) {

		Optional <User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent())
			throw new UserNotFoundException("id-"+id);

		return userOptional.get().getPosts();
	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createJPAUser(@PathVariable int id,  @RequestBody Post post) {

		Optional <User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent())
			throw new UserNotFoundException("id-"+id);

		User user = userOptional.get();

		post.setUser(user);

		postRepository.save(post);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri().
						path("/{id}")
				.buildAndExpand(post.getId()).toUri();
		LOG.info("createUser: "+location);

		return ResponseEntity.created(location).build();
	}


}
