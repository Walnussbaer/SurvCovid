package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	
	@GetMapping("/")
	public String welcome(@RequestParam(name="name", required =false,defaultValue="World") String name, Model model) {
		
		return "Hello User";
		
	}

}
