package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEventDefinitionService {
	
	@Autowired 
	GameEventDefinitionRepository gameEventDefinitionRepository;
	
	@Autowired
	public GameEventDefinitionService(GameEventDefinitionRepository gameEventDefinitionRepository) {
		
		if (gameEventDefinitionRepository == null) {
			throw new NullPointerException("gameEventDefinitionRepository cannot be null");
		}
		
		this.gameEventDefinitionRepository = gameEventDefinitionRepository;
	}
	
	public GameEventDefinition saveGameEventDefinition(GameEventDefinition gameEventDefinition) {
		
		if (gameEventDefinition == null) {
			throw new NullPointerException("gameEventDefinition cannot be null");
		}
		this.gameEventDefinitionRepository.save(gameEventDefinition);
		
		return gameEventDefinition;
	}
	
    public GameEventDefinition getGameEventDefinitionById(long gameEventDefinitionId) {
        
        
        Optional<GameEventDefinition> gameEventDefinition = this.gameEventDefinitionRepository.findById(gameEventDefinitionId);
        
        if (gameEventDefinition.isEmpty()) {
            return null;
        }
        
        return gameEventDefinition.get();
        
    }
	
	

}
