package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameEventController {
    
    GameManager gameManager = GameManager.getInstance();
    
    @Autowired 
    UserService userService;
	
	@GetMapping("/next_event")
	public GameEvent getNextGameEvent(@RequestParam(name="user_id", required=true)String userId) {
	    
	    GameEvent nextGameEvent;
	    
	    User player;
	    
	    gameManager.getGameEventManager().getNextGameEventForUser(userId);
	    
	   
	    
	    
	    
	    
		return null;
	}

}
