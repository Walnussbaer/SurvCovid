package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.GameEventManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventChoiceService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event/")
public class GameEventController {
    
    @Autowired
    GameManager gameManager;

	@Autowired
    GameEventManager gameEventManager;
    
    @Autowired 
    UserService userService;
    
    @Autowired
    GameEventService gameEventService;
    
    @Autowired 
    GameEventChoiceService gameEventChoiceService;
    
	@GetMapping("/next")
	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public GameEvent getNext(@RequestParam(name="user_id", required=true)long userId) {
	    
	    
	    gameEventManager = gameManager.getGameEventManager();
	    
	    if (gameEventManager == null) {
	        return null;
	        // TODO: implement proper error handling        
	    }
	    
	    GameEvent nextGameEvent = null;
	    
	    Optional<User> player;
	    
	    player = userService.getUserById(userId);
	    
	    if (player.isEmpty()) {
	        return null;
	        // TODO: implement proper error handling
	    }
	    
	    nextGameEvent = gameEventManager.serveGameEvent(player.get());
	    
	    if (nextGameEvent == null) {
	        return null;
	        // TODO implement proper error handling
	    }
	   
		return nextGameEvent;
	}
	
	@PutMapping("/next")
	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String respondNext(
	        @RequestParam(name="user_id", required = true) long userId,
	        @RequestParam(name="game_event_id", required=true) long gameEventId,
	        @RequestParam(name="choice_id", required = true) long choiceId) {
	    
	    GameEvent nextGameEvent = null;
	    GameEventChoice gameEventChoice = null;
	    gameEventManager = gameManager.getGameEventManager();
	    
	    Optional<User> player;
	    
	    player=userService.getUserById(userId);
	    
	    if (player.isEmpty()) {
	        return null;
	        // TODO: implement proper error handling
	    }
	    
	    nextGameEvent = gameEventService.getGameEventById(gameEventId);
	    gameEventChoice = gameEventChoiceService.getGameEventChoiceById(choiceId);
	    
	    // TODO: get the availabe choices for the given event id
	    
	    // TODO: validate whether user sent a valid choice
	    
	    // TODO: write a wrapper function that hides the implementation of receiving an event
	    
	    // TODO: validate whether user responded to his/her next event in the database
	    
	    nextGameEvent.setChosenChoice(gameEventChoice);
	    nextGameEvent.setDone(true);
	    gameEventService.saveGameEvent(nextGameEvent);
	    
	    gameEventManager.initiateNewGameEvent(player.get());
	    
	    return "Your choice was registered!";
	    
	}

	@GetMapping("/possiblenext")
	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	// Test: http://localhost:8080/api/v1/event/possiblenext?user_id=12
	public List<GameEventDefinition> getPossibleNextEventsForUser(@RequestParam(name="user_id", required=true)long userId) {
		return gameEventManager.getPossibleNextEventsForUser(userId);
	}


}
