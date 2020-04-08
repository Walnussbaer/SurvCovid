package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class GameEventManager {
	
	@Autowired
	UserService userService;
	
	public GameEvent getNextGameEventForUser(String playerId){
		
	    GameEvent gameEvent = null;
	    
	    
		
		return gameEvent;
	}
	
	public void sayHello() {
	    System.out.println("Hello from GameEventManager!");
	}

}
