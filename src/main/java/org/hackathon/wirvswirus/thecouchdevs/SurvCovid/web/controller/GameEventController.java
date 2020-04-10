package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.GameEventManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventChoiceService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameEventController {
    
    @Autowired
    GameManager gameManager;
    
    GameEventManager gameEventManager;
    
    @Autowired 
    UserService userService;
    
    @Autowired
    GameEventService gameEventService;
    
    @Autowired 
    GameEventChoiceService gameEventChoiceService;
    
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
	
	@PutMapping("/next_event")
	public String respondToNextGameEvent(
	        @RequestParam(name="user_number", required = true) long userNumber,
	        @RequestParam(name="game_event_id", required=true) long gameEventId,
	        @RequestParam(name="choice_id", required = true) long choiceId) {
	    
	    GameEvent nextGameEvent = null;
	    GameEventChoice gameEventChoice = null;
	    
	    Optional<User> player;
	    
	    player=userService.getUserByNumber(userNumber);
	    
	    if (player.isEmpty()) {
	        return null;
	        // TODO: implement proper error handling
	    }
	    
	    nextGameEvent = gameEventService.getGameEventById(gameEventId);
	    gameEventChoice = gameEventChoiceService.getGameEventChoiceById(choiceId);
	    
	    // TODO: get the availbe choices for the given event id
	    
	    // TODO: validate whether user sent a valid choice
	    
	    // TODO: write a wrapper function that hides the implementation of receiving an event
	    nextGameEvent.setChosenChoice(gameEventChoice);
	    nextGameEvent.setDone(true);
	    gameEventService.saveGameEvent(nextGameEvent);
	    
	    return "Your choice was registered!";
	    
	}

}
