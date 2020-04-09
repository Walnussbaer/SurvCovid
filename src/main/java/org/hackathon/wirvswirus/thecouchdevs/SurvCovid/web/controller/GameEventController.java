package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.GameEventManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameEventController {
    
    @Autowired
    GameManager gameManager;
    
    @Autowired 
    UserService userService;
    
    GameEventManager gameEventManager;
	
	@GetMapping("/next_event")
	public GameEvent getNextGameEvent(@RequestParam(name="user_number", required=true)long userNumber) {
	    
	    gameEventManager = gameManager.getGameEventManager();
	    
	    if (gameEventManager == null) {
	        return null;
	        // TODO: implement proper error handling        
	    }
	    
	    GameEvent nextGameEvent = null;
	    
	    Optional<User> player;
	    
	    player = userService.getUserByNumber(userNumber);
	    
	    if (player.isEmpty()) {
	        return null;
	        // TODO: implement proper error handling
	    }
	    
	    nextGameEvent = gameEventManager.getNextGameEventForUser(player.get());
	    
	    if (nextGameEvent == null) {
	        return null;
	        // TODO implement proper error handling
	    }
	   
		return nextGameEvent;
	}

}
