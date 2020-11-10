package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoActionRequiredException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.PasswordChangeRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.UserUpdateRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.UserControllerResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
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
	public ResponseEntity<UserControllerResponse> list(){
		
		UserControllerResponse response = new UserControllerResponse();
		
		response.setMessage("Retrieval was successful!");
		response.setData(userService.getAllUsers());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	/**
	 * Accepts a user id as an input and returns the corresponding {@link User} object inside an HTTP response if the user is available. 
	 * 
	 * @param userId - the id of the user which shall be retrieved
	 * 
	 * @return an HTTP reponse which includes the desired user if present
	 */
	@GetMapping
	@RequestMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserControllerResponse> get(@PathVariable long userId){
		
		User user;
		UserControllerResponse response = new UserControllerResponse();
		
		List<User> users = new ArrayList<User>();
		
		try {
			user = userService.getUserById(userId);
			users.add(user);
		} 
		catch (UserNotExistingException unee) {
			response.setMessage(unee.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		response.setData(users);
		response.setMessage("Retrieval was successful!");
		
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	
	/**
	 * Takes in a {@link User} object and tries to save this object to the database. 
	 * 
	 * @param user - the {@link User} object that shell be persisted
	 * 
	 * @return the created user inside an HTTP response if the request was successful, else an error message
	 */
	@PostMapping
	public ResponseEntity<UserControllerResponse> create(@RequestBody @Valid final User user, BindingResult bindingResult) {
	       
	    User createdUser;
	    UserControllerResponse response = new UserControllerResponse();
	    
	    List<User> users = new ArrayList<User>();
	    
	    try {
	        createdUser = userService.saveUser(user,bindingResult);
	        users.add(createdUser);
	    }
	    catch (NoValidUserException nvue) {
	    	response.setMessage("The given user object is not valid!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	   
	    response.setData(users);
	    response.setMessage("Creation was successful!");
	    
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * Takes in a userid and tries to delete the corresponding user in the database. 
	 * 
	 * @param userId - the id of the user that shall be deleted
	 * 
	 * @return an HTTP response with the result of the operation
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<UserControllerResponse> delete(@PathVariable long userId) {
		
		UserControllerResponse response = new UserControllerResponse();
		
		try {
			userService.deleteUserById(userId);
		} catch (UserNotExistingException unee) {
			response.setMessage(unee.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		response.setMessage("The user has been deleted!");
		
		return ResponseEntity.ok(response);
	}
	

	/**
	 * Takes in a user object from the HTTP request and updates the user in the database if the user is existing - else returns an error message. 
	 * 
	 * @param user - the user which shall be updated
	 * 
	 * @return an HTTP reponse with the updatet user or an error message if the operation was not successfull
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<UserControllerResponse> update(@RequestBody @Valid UserUpdateRequest userUpdate, BindingResult bindingResult) {
		
		User userInDatbase;
		
		UserControllerResponse response = new UserControllerResponse();
		
		List<User> users = new ArrayList<User>();
					
		try {
			userInDatbase = userService.updateUser(userUpdate, bindingResult);
			users.add(userInDatbase);
		} 
		catch (NoValidUserException nvue) {
			response.setMessage(nvue.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} 
		catch (UserNotExistingException e) {
			response.setMessage("This is not a possible update!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		response.setData(users);
		response.setMessage("Update was successful!");
		
		return ResponseEntity.ok(response);
	}
}
