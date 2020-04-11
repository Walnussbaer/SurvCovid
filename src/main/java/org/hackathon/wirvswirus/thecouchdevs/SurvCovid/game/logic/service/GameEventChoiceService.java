package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEventChoiceService {
    
    @Autowired
    GameEventChoiceRepository gameEventChoiceRepository;
    
    @Autowired
    public GameEventChoiceService(GameEventChoiceRepository gameEventChoiceRepository) {
        
        if (gameEventChoiceRepository == null) {
            throw new NullPointerException("gameEventChoiceRepository cannot be null");
        }
        this.gameEventChoiceRepository = gameEventChoiceRepository;
        
    }
    
    public GameEventChoice saveGameEventChoice(GameEventChoice gameEventChoice) {
        
        if (gameEventChoice == null) {
            throw new NullPointerException("gameEventChoice cannot be null");
        }
        
        gameEventChoice = this.gameEventChoiceRepository.save(gameEventChoice);
        
        return gameEventChoice;
        
    }
    
    public GameEventChoice getGameEventChoiceById(long gameEventChoiceId) {
        
        
        Optional<GameEventChoice> gameEventChoice = this.gameEventChoiceRepository.findById(gameEventChoiceId);
        
        if (gameEventChoice.isEmpty()) {
            return null;
        }
        
        return gameEventChoice.get();
        
    }

}
