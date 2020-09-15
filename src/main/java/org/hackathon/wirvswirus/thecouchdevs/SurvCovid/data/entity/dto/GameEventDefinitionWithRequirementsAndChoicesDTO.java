package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionRequirementType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;

import java.util.List;

public class GameEventDefinitionWithRequirementsAndChoicesDTO extends GameEventDefinitionDTO {

    private List<GameEventRequirementDTO> requirements;
    private List<GameEventChoiceDTO> choices;

    public GameEventDefinitionWithRequirementsAndChoicesDTO(String description, String shortTitle, GameEventDefinitionType gameEventDefinitionType) {
        super(description, shortTitle, gameEventDefinitionType);
    }

    public GameEventDefinitionWithRequirementsAndChoicesDTO(GameEventDefinitionDTO gameEventDefinitionDTO, List<GameEventRequirementDTO> requirements, List<GameEventChoiceDTO> choices) {
        super(gameEventDefinitionDTO.getDescription(), gameEventDefinitionDTO.getShortTitle(), gameEventDefinitionDTO.getGameEventDefinitionType());
        this.requirements = requirements;
        this.choices = choices;
    }

    public GameEventDefinitionWithRequirementsAndChoicesDTO(String description, String shortTitle, GameEventDefinitionType gameEventDefinitionType, List<GameEventRequirementDTO> requirements, List<GameEventChoiceDTO> choices) {
        super(description, shortTitle, gameEventDefinitionType);
        this.requirements = requirements;
        this.choices = choices;
    }

    public List<GameEventRequirementDTO> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<GameEventRequirementDTO> requirements) {
        this.requirements = requirements;
    }

    public List<GameEventChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<GameEventChoiceDTO> choices) {
        this.choices = choices;
    }
}
