package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventRequirement;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionRequirementType;

/** Data transfer object for GameEventRequirements
 *
 * Does not contain id of target GameEventDefinition since it is passed to the API
 * before the GameEventDefinition is created and therefore the id is not known at that time.
 *
 * Does not contain fields which are not needed when creating a new game event definition.
 *
 */
public class GameEventRequirementDTO {

    private Long requiredGameEventDefinitionId;
    private String requiredGameEventDefinitionShortTitle;
    private String requiredGameEventDefinitionDescription;
    private Long gameEventChoiceId;
    private String gameEventChoiceDescription;
    private GameEventDefinitionRequirementType type;

    public GameEventRequirementDTO(Long requiredGameEventDefinitionId,
                                String requiredGameEventDefinitionShortTitle,
                                String requiredGameEventDefinitionDescription,
                                Long gameEventChoiceId,
                                String gameEventChoiceDescription,
                                GameEventDefinitionRequirementType requirementType) {
        this.requiredGameEventDefinitionId = requiredGameEventDefinitionId;
        this.requiredGameEventDefinitionShortTitle = requiredGameEventDefinitionShortTitle;
        this.requiredGameEventDefinitionDescription = requiredGameEventDefinitionDescription;
        this.gameEventChoiceId = gameEventChoiceId;
        this.gameEventChoiceDescription = gameEventChoiceDescription;
        this.type = requirementType;
    }

    public Long getRequiredGameEventDefinitionId() {
        return requiredGameEventDefinitionId;
    }

    public void setRequiredGameEventDefinitionId(Long requiredGameEventDefinitionId) {
        this.requiredGameEventDefinitionId = requiredGameEventDefinitionId;
    }

    public String getRequiredGameEventDefinitionShortTitle() {
        return requiredGameEventDefinitionShortTitle;
    }

    public void setRequiredGameEventDefinitionShortTitle(String requiredGameEventDefinitionShortTitle) {
        this.requiredGameEventDefinitionShortTitle = requiredGameEventDefinitionShortTitle;
    }

    public String getRequiredGameEventDefinitionDescription() {
        return requiredGameEventDefinitionDescription;
    }

    public void setRequiredGameEventDefinitionDescription(String requiredGameEventDefinitionDescription) {
        this.requiredGameEventDefinitionDescription = requiredGameEventDefinitionDescription;
    }

    public Long getGameEventChoiceId() {
        return gameEventChoiceId;
    }

    public void setGameEventChoiceId(Long gameEventChoiceId) {
        this.gameEventChoiceId = gameEventChoiceId;
    }

    public String getGameEventChoiceDescription() {
        return gameEventChoiceDescription;
    }

    public void setGameEventChoiceDescription(String gameEventChoiceDescription) {
        this.gameEventChoiceDescription = gameEventChoiceDescription;
    }

    public GameEventDefinitionRequirementType getType() {
        return type;
    }

    public void setType(GameEventDefinitionRequirementType type) {
        this.type = type;
    }
}
