package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventRequirement;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventRequirementDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventChoiceRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventDefinitionRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEventDefinitionRequirementService {

    @Autowired
    GameEventRequirementRepository gameEventRequirementRepository;

    @Autowired
    GameEventDefinitionRepository gameEventDefinitionRepository;

    @Autowired
    GameEventChoiceRepository gameEventChoiceRepository;

    @Autowired
    public GameEventDefinitionRequirementService(GameEventRequirementRepository gameEventRequirementRepository) {

        if (gameEventRequirementRepository == null) {
            throw new NullPointerException("gameEventRequirementRepository cannot be null");
        }
        this.gameEventRequirementRepository = gameEventRequirementRepository;

    }

    public GameEventRequirement saveGameEventRequirement(GameEventRequirement gameEventRequirement) {

        if (gameEventRequirement == null) {
            throw new NullPointerException("gameEventRequirement cannot be null");
        }
        this.gameEventRequirementRepository.save(gameEventRequirement);

        return gameEventRequirement;
    }

    public List<GameEventRequirement> getRequirementsForGameEventDefinition(GameEventDefinition gameEventDefinition) {
        return this.gameEventRequirementRepository.findByTargetGameEventDefinitionId(gameEventDefinition.getId());
    }
    public List<GameEventRequirementDTO> getRequirementDTOsForGameEventDefinition(GameEventDefinition gameEventDefinition) {
        List<GameEventRequirementDTO> gameEventRequirementDTOS = new ArrayList<>();

        for(GameEventRequirement requirement: this.gameEventRequirementRepository.findByTargetGameEventDefinitionId(gameEventDefinition.getId())) {
            gameEventRequirementDTOS.add(
                    new GameEventRequirementDTO(
                            requirement.getRequiredGameEventDefinitionId(),
                            gameEventDefinitionRepository.findById(requirement.getRequiredGameEventDefinitionId()).get().getShortTitle(),
                            gameEventDefinitionRepository.findById(requirement.getRequiredGameEventDefinitionId()).get().getDescription(),
                            requirement.getGameEventChoice().getId(),
                            gameEventChoiceRepository.findById(requirement.getGameEventChoice().getId()).get().getDescription(),
                            requirement.getType()
                    ));
        }

        return gameEventRequirementDTOS;
    }
}