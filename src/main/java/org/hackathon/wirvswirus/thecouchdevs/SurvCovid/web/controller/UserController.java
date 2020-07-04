package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge=3600)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired UserService userService;


	// because we use @EnableGlobalMethodSecurity(prePostEnabled=true) in WebSecurityConfig, we can now secure methods in our APIs with @PreAuthorize

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')") // automatically appends ROLE_ to ADMIN
	public List<User> list(){
		
		return userService.getAllUsers();
		
	}

	@GetMapping
	@RequestMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<User> get(@PathVariable long userId){
		
		Optional<User> user = userService.getUserById(userId);
		
		return user;		
	}
	
	@PostMapping
	public String create(@RequestBody final User user) {

		// persist the new user
		userService.saveUser(user);

		return Long.toString(user.getUserId());		
	}
	
	//@DeleteMapping
	//@RequestMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long userId) {
		userService.deleteUserById(userId);
	}
	
	//@PutMapping
	//@RequestMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "{userId}", method = RequestMethod.PUT)
	public void update(@PathVariable long userId, @RequestBody User user) {
		userService.saveUser(user);
	}

}
