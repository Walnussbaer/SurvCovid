package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;


import java.time.LocalDateTime;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameEventManager {
	
	@Autowired
	UserService userService;
	
	@Autowired
	GameEventService gameEventService;
	
	@Autowired
	GameEventDefinitionService gameEventDefinitionService;
	
	public GameEvent serveGameEvent(User player){
		
	    GameEvent gameEvent = null;
	     
	    gameEvent = gameEventService.findNextGameEventForUser(player);
		
		return gameEvent;
	}
	
	public GameEvent initiateNewGameEvent(User player) {
	    
	    long gameEventDefinitionId = 1;
	    
	    GameEvent gameEvent = null;
	    
	    LocalDateTime currentTime = LocalDateTime.now();
	    
	    GameEventDefinition gameEventDefinition = gameEventDefinitionService.getGameEventDefinitionById(gameEventDefinitionId);
	    
	    boolean isDone = false;
	    
	    gameEvent = new GameEvent(currentTime, player, gameEventDefinition, isDone);
	    gameEventService.saveGameEvent(gameEvent);
	    
	    return gameEvent;
	}
	
	public void sayHello() {
	    System.out.println("Hello from GameEventManager!");
	}
	
	

}
