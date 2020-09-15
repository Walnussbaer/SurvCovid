package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventChoiceDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventDefinitionDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventDefinitionWithRequirementsAndChoicesDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventRequirementDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventChoiceRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEventDefinitionService {
	
	@Autowired 
	GameEventDefinitionRepository gameEventDefinitionRepository;

	@Autowired
	GameEventDefinitionRequirementService gameEventDefinitionRequirementService;

	@Autowired
	GameEventChoiceService gameEventChoiceService;
	
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

	public List<GameEventDefinition> getFinishedGameEventDefinitionsForUser(User user) {
		// TODO: Filter out the events the user has not already finished
		//this.gameEventDefinitionRepository.
		return (List<GameEventDefinition>) this.gameEventDefinitionRepository.findAll();

	}

    public List<GameEventDefinition> getUnfinishedGameEventDefinitionsForUser(User user) {
		// TODO: Filter out the events the user has already finished
		return (List<GameEventDefinition>) this.gameEventDefinitionRepository.findAll();

	}

	public List<GameEventDefinition> getPossibleNextEventsForUser(Long userId) {
		return this.gameEventDefinitionRepository.findPossibleEventsForPlayer(userId);
	}

	public List<GameEventDefinition> getAll() {
		return Lists.newArrayList(gameEventDefinitionRepository.findAll());
	}

	public List<GameEventDefinitionWithRequirementsAndChoicesDTO> getAllWithRequirementsDTOs() {
		// Native definitions
		List<GameEventDefinition> gameEventDefinitions = Lists.newArrayList(gameEventDefinitionRepository.findAll());

		// Create custom DTO list to be able to attache the requirements
		List<GameEventDefinitionWithRequirementsAndChoicesDTO> gameEventDefinitionWithRequirementsAndChoicesDTOS = new ArrayList<>();

		for(GameEventDefinition gameEventDefinition: gameEventDefinitions) {

			// Fetch list of requirementDTOs (contains normal requirement data plus shortTitle of required event and description of choice)
			List<GameEventRequirementDTO> requirementDTOS = gameEventDefinitionRequirementService.getRequirementDTOsForGameEventDefinition(gameEventDefinition);

			// Fetch list of choicesDTOs
			List<GameEventChoiceDTO> choicesDTOs = gameEventChoiceService.getGameEventChoiceDTOsByGameEventDefinition(gameEventDefinition);

			// Create GameEventDefinitionWithRequirementsAndChoicesDTO
			gameEventDefinitionWithRequirementsAndChoicesDTOS.add(
					new GameEventDefinitionWithRequirementsAndChoicesDTO(
							GameEventDefinitionDTO.fromGameEventDefinition(gameEventDefinition),
							requirementDTOS,
							choicesDTOs));
		}

		return gameEventDefinitionWithRequirementsAndChoicesDTOS;
	}

}
