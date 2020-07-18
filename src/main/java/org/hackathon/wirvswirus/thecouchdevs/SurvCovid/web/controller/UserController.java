package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides CRUD functionality to work with ressources of the type  {@link User}. 
 * @author volke
 *
 */
@CrossOrigin(origins = "*", maxAge=3600)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired UserService userService;
	
	// because we use @EnableGlobalMethodSecurity(prePostEnabled=true) in WebSecurityConfig, we can now secure methods in our APIs with @PreAuthorize

	/**
	 * Returns all available {@link User} instances in the database inside an HTTP response
	 * 
	 * @return a list of available users
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')") // automatically appends ROLE_ to ADMIN
	public List<User> list(){
		
		return userService.getAllUsers();
		
	}

	/**
	 * Accepts a user id as an input and returns corresponding {@link User} object inside an HTTP response if the user is available. 
	 * 
	 * @param userId - the id of the user which shall be retrieved
	 * 
	 * @return an HTTP reponse
	 */
	@GetMapping
	@RequestMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> get(@PathVariable long userId){
		
		User user;
		
		try {
			user = userService.getUserById(userId);
		} 
		catch (UserNotExistingException unee) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unee.getMessage());
		}
		
		return ResponseEntity.ok(user);		
	}
	
	/**
	 * Takes in a {@link User} object and tries to save this object to the database. 
	 * 
	 * @param user - the {@link User} object that shell be persisted
	 * @return the created user inside an HTTP response if the request was successful, else an error message
	 */
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid final User user) {
	    
	    long userId;
	    
	    User createdUser;
	    
	    try {
	        createdUser = userService.saveUser(user);
	    }
	    catch (NoValidUserException nvue) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The given user object is not valid!");
	    }
	    
	    return ResponseEntity.status(HttpStatus.OK).body(createdUser);
	}
	
	/**
	 * Takes in a userid and tries to delete the corresponding user in the database. 
	 * 
	 * @param userId - the id of the user that shall be deleted
	 * @return an HTTP response with the result of the operation
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long userId) {
		try {
			userService.deleteUserById(userId);
		} catch (UserNotExistingException unee) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unee.getMessage());
		}
		
		return ResponseEntity.ok("The user has been deleted!");
	}
}
