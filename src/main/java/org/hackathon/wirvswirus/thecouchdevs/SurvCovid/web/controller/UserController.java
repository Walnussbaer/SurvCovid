package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;

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

	@GetMapping("/register")
	public String viewRegistrationPage() {
		return "On this page, you can register yourself to the game!";
	}

	@PostMapping("/register")
	public String register(@RequestParam(name="userName", required=true)String userName) {

		User user = new User(userName);

		return user.getUserId();

	}

}
