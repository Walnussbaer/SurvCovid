package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
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
	
	public GameEvent getNextGameEventForUser(User player){
		
	    GameEvent gameEvent = null;
	     
	    gameEvent = gameEventService.findNextGameEventForUser(player);
		
		return gameEvent;
	}
	
	public void sayHello() {
	    System.out.println("Hello from GameEventManager!");
	}

}
