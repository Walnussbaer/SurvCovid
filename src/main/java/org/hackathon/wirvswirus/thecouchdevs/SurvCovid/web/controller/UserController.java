package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired UserService userService;

	@GetMapping
	public List<User> list(){
		
		return userService.getAllUsers();
		
	}

	@GetMapping
	@RequestMapping("{userId}")
	public Optional<User> get(@PathVariable long userId){
		
		Optional<User> user = userService.getUserById(userId);
		
		return user;		
	}
	
	@PostMapping
	public String create(@RequestBody final User user) {

		userService.saveUser(user);
		
		return Long.toString(user.getUserId());		
	}
	
	//@DeleteMapping
	//@RequestMapping("{userId}")
	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long userId) {
		userService.deleteUserById(userId);
	}
	
	//@PutMapping
	//@RequestMapping("{userId}")
	@RequestMapping(value = "{userId}", method = RequestMethod.PUT)
	public void update(@PathVariable long userId, @RequestBody User user) {
		userService.saveUser(user);
	}

}
