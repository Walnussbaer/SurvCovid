package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;

/** Data transfer object for GameEventDefinitions
 *
 * Does not contain fields which are not needed when creating a new game event definition.
 *
 */
public class GameEventDefinitionDTO {
    private String description;
    private String shortTitle;
    private GameEventDefinitionType gameEventDefinitionType;

    public GameEventDefinitionDTO(String description, String shortTitle, GameEventDefinitionType gameEventDefinitionType) {
        this.description = description;
        this.shortTitle = shortTitle;
        this.gameEventDefinitionType = gameEventDefinitionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public GameEventDefinitionType getGameEventDefinitionType() {
        return gameEventDefinitionType;
    }

    public void setGameEventDefinitionType(GameEventDefinitionType gameEventDefinitionType) {
        this.gameEventDefinitionType = gameEventDefinitionType;
    }

    public static GameEventDefinitionDTO fromGameEventDefinition(GameEventDefinition gameEventDefinition) {
        return new GameEventDefinitionDTO(
                gameEventDefinition.getDescription(),
                gameEventDefinition.getShortTitle(),
                gameEventDefinition.getGameEventDefinitionType());
    }
}
