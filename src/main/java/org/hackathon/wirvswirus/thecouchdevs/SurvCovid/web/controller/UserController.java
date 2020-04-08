package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired UserService userService;
	
	@GetMapping("/users")
	public List<User> sendUsers(){
		
		return userService.getAllUsers();
		
	}
	
	@GetMapping("/user")
	public Optional<User> sendUser(@RequestParam(name="userNumber", required=true)long userNumber){		
		
		Optional<User> user = userService.getUserByNumber(userNumber);		
		
		return user;		
	}
	
	@GetMapping("/register")
	public String viewRegistrationPage() {
		return "On this page, you can register yourself to the game!";
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestParam(name="userName", required=true)String userName) {
		
		User user = new User(userName);		
		
		return user.getUserId();		
	}
	
	@PostMapping("/delete")
	public void deleteUser(@RequestParam(name="userNumber", required=true)long userNumber) {	
		userService.deleteUserByNumber(userNumber);			
	}
	
	@PostMapping("/modify")
	public void modifyUserName(@RequestParam(name="userNumber", required=true)long userNumber,
			@RequestParam(name="userName", required=true)String userName) {
		userService.changeUserNameByNumber(userNumber, userName);
		//this.sendUser(userNumber);
				
	}

}
