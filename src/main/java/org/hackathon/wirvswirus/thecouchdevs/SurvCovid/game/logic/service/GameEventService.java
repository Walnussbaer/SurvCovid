package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEventService {
	
	@Autowired
	private GameEventRepository gameEventRepository;
	
	@Autowired
	public GameEventService(GameEventRepository gameEventRepository) {
		if (gameEventRepository == null) {
			throw new NullPointerException("gameEventRepository cannot be null");
		}
		this.gameEventRepository = gameEventRepository;
	}
	
	public GameEventService() {
	    
	}
	
	public GameEvent saveGameEvent(GameEvent gameEvent) {
		
		if (gameEvent == null) {
			throw new NullPointerException("gameEvent cannot be null");
		}
		gameEvent = this.gameEventRepository.save(gameEvent);
		
		return gameEvent;
	}
	
	public GameEvent findNextGameEventForUser(User player) {
	    
	    GameEvent nextGameEvent = null;
	     
	    if (player == null) {
	        throw new NullPointerException("player cannot be null");
	    }
	    
	    nextGameEvent = this.gameEventRepository.findByIsDoneAndPlayer(false,player);
	    
	    return nextGameEvent;
	        
	}
	
	public GameEvent getGameEventById(long gameEventId) {
	    
	    
	    Optional<GameEvent> gameEvent = this.gameEventRepository.findById(gameEventId);
	    
	    if (gameEvent.isEmpty()) {
	        return null;
	    }
	    
	    return gameEvent.get();
	    
	}
	
}
