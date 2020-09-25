package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;

import java.util.List;

public class GameEventDefinitionWithRequirementsAndChoicesDTO extends GameEventDefinitionDTO {

    private List<GameEventRequirementDTO> requirements;
    private List<GameEventChoiceDTO> gameEventChoices;

    public GameEventDefinitionWithRequirementsAndChoicesDTO(Long id, String description, String shortTitle, GameEventDefinitionType gameEventDefinitionType) {
        super(id, description, shortTitle, gameEventDefinitionType);
    }

    public GameEventDefinitionWithRequirementsAndChoicesDTO(GameEventDefinitionDTO gameEventDefinitionDTO, List<GameEventRequirementDTO> requirements, List<GameEventChoiceDTO> gameEventChoices) {
        super(gameEventDefinitionDTO.getId(), gameEventDefinitionDTO.getDescription(), gameEventDefinitionDTO.getShortTitle(), gameEventDefinitionDTO.getGameEventDefinitionType());
        this.requirements = requirements;
        this.gameEventChoices = gameEventChoices;
    }

    public GameEventDefinitionWithRequirementsAndChoicesDTO(Long id, String description, String shortTitle, GameEventDefinitionType gameEventDefinitionType, List<GameEventRequirementDTO> requirements, List<GameEventChoiceDTO> gameEventChoices) {
        super(id, description, shortTitle, gameEventDefinitionType);
        this.requirements = requirements;
        this.gameEventChoices = gameEventChoices;
    }

    public List<GameEventRequirementDTO> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<GameEventRequirementDTO> requirements) {
        this.requirements = requirements;
    }

    public List<GameEventChoiceDTO> getGameEventChoices() {
        return gameEventChoices;
    }

    public void setGameEventChoices(List<GameEventChoiceDTO> gameEventChoices) {
        this.gameEventChoices = gameEventChoices;
    }
}
