package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventRequirement;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventChoiceDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventDefinitionDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventRequirementDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.GameEventDefinitionCreationRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEventDefinitionCreationService {

    @Autowired
    GameEventDefinitionService gameEventDefinitionService;

    @Autowired
    GameEventDefinitionRequirementService gameEventDefinitionRequirementService;

    @Autowired
    GameEventChoiceService gameEventChoiceService;

    @Autowired
    public GameEventDefinitionCreationService(/*GameEventDefinitionService gameEventDefinitionService*/) {

//        if (gameEventDefinitionService == null) {
//            throw new NullPointerException("gameEventDefinitionService cannot be null");
//        }
//        this.gameEventDefinitionService = gameEventDefinitionService;

    }

    /** Create game event definition and all its requirements in the database.
     * Whole method is wrapped inside a transaction ("all or nothing"),
     * so we either succeed completely or we do not save anything at all.
     *
     * @param gameEventDefinitionCreationRequest
     */
    @Transactional
    public void createGameEventDefinitionWithRequirements(GameEventDefinitionCreationRequest gameEventDefinitionCreationRequest) {
        // Create a full GameEventDefinition entity object from the DTO passed in the request
        GameEventDefinition gameEventDefinition = GameEventDefinition.fromDTO(gameEventDefinitionCreationRequest.getGameEventDefinitionDTO());

        // Create full GameEventRequirement entity objects from DTOs passed in the request
        List<GameEventRequirementDTO> requirementDTOList = gameEventDefinitionCreationRequest.getRequirementDTOList();

        // Create full GameEventChoice entity objects from DTOs passed in the request
        List<GameEventChoiceDTO> choicesDTOList = gameEventDefinitionCreationRequest.getChoicesDTOs();

        List<GameEventChoice> choices = new ArrayList<>();
        for(GameEventChoiceDTO choiceDTO: choicesDTOList) {
            GameEventChoice choice = null;
            if(choiceDTO.getId() == -1) {
                choice = new GameEventChoice(choiceDTO.getDescription(), choiceDTO.getData());
                choice = gameEventChoiceService.saveGameEventChoice(choice);
            }
            else {
                choice = gameEventChoiceService.getGameEventChoiceById(choiceDTO.getId());
            }

            choices.add(choice);
        }
        gameEventDefinition.setGameEventChoices(choices);

        // TODO: Check/sanitize input

        // Add game event definition to database
        gameEventDefinition = gameEventDefinitionService.saveGameEventDefinition(gameEventDefinition);

        for(GameEventRequirementDTO requirementDTO: requirementDTOList) {
            // TODO: Check if requiredGameEventDefinition of requirement exists
            // TODO: Check if optionally provided required choice is valid for the requiredGameEventDefinition

            GameEventRequirement requirement = new GameEventRequirement(
                    // Use newly generated id of created game event definition
                    gameEventDefinition,
                    // Fetch existing game event definition to depend on from database by id
                    gameEventDefinitionService.getGameEventDefinitionById(requirementDTO.getRequiredGameEventDefinitionId()),
                    // Fetch existing game event choice to use from database by id
                    gameEventChoiceService.getGameEventChoiceById(requirementDTO.getGameEventChoiceId()),
                    // Use type passed via the API
                    requirementDTO.getType());
            gameEventDefinitionRequirementService.saveGameEventRequirement(requirement);
        }

        System.out.println("--");
    }

//    public GameEventRequirement saveGameEventRequirement(GameEventRequirement gameEventRequirement) {
//
//        if (gameEventRequirement == null) {
//            throw new NullPointerException("gameEventRequirement cannot be null");
//        }
//        this.gameEventDefinitionCreationService.save(gameEventRequirement);
//
//        return gameEventRequirement;
//    }

//    public List<GameEventRequirement> getRequirementsForGameEventDefinition(GameEventDefinition gameEventDefinition) {
//        return this.gameEventRequirementRepository.findByGameEventDefinition(gameEventDefinition);
//    }
}