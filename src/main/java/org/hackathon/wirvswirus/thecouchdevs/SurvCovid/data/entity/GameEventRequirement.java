package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionRequirementType;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="GAME_EVENT_REQUIREMENTS")
public class GameEventRequirement {
    /** Table representing the requirements for GameEventDefinitions
     *
     *  Defines what type of requirement this entity represents
     *
     *  Examples:
     *  - Event X has happened
     *  - Event X has happened and the choice was 1
     *  - Event Y has not happened yet
     *  - Event Y has happened and the choice was not 2
     *
     */

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

//    // The ID of the GameEventDefinition this is a requirement for
//    @ManyToOne
//    @JoinColumn(name = "TARGET_GAME_EVENT_DEFINITION_ID")
//    @JsonManagedReference
//    private GameEventDefinition targetGameEventDefinition;
    @Column(name="TARGET_GAME_EVENT_DEFINITION_ID")
    public Long targetGameEventDefinitionId;

//    // The ID of the GameEventDefinition which must have happened/must not have happened
////    @ManyToOne
////    @JoinColumn(name = "REQUIRED_GAME_EVENT_DEFINITION_ID")
////    @JsonManagedReference
////    private GameEventDefinition requiredGameEventDefinition;
    @Column(name="REQUIRED_GAME_EVENT_DEFINITION_ID")
    public Long requiredGameEventDefinitionId;

    @ManyToOne
    @JoinColumn(name = "GAME_EVENT_CHOICE_ID")
    @JsonManagedReference
    private GameEventChoice gameEventChoice;

    // What type of requirement is this?
    // Does this entity define something which has happend, something which has not happened, ...?
    @Column(name="REQUIREMENT_TYPE")
    private GameEventDefinitionRequirementType type;

    /*
        Possible combinations:

              // Event X has happened and the player chose option A
            - type:                     HAS_HAPPENED
              gameEventDefinition:      Event X
              gameEventChoice:          null

              // Event X has happened and the player chose option A
            - type:                     HAS_HAPPENED
              gameEventDefinition:      Event X
              gameEventChoice:          Event X - Choice A

              // Event Y has not happened yet
            - type:                     HAS_NOT_HAPPENED
              gameEventDefinition:      Event Y
              gameEventChoice:          null

              // If Event Y has happened, the player did not choose option B
            - type:                     HAS_NOT_HAPPENED
              gameEventDefinition:      Event Y
              gameEventChoice:          Event Y - Choice B

     */

    public GameEventRequirement() {

    }

    public GameEventRequirement(GameEventDefinition targetGameEventDefinition,
                                GameEventDefinition requiredGameEventDefinition,
                                GameEventChoice choice,
                                GameEventDefinitionRequirementType requirementType) {
        this.targetGameEventDefinitionId = targetGameEventDefinition.getId();
        this.requiredGameEventDefinitionId = requiredGameEventDefinition.getId();
        this.gameEventChoice = choice;
        this.type = requirementType;
    }

    public long getId() {
        return id;
    }

//    public GameEventDefinition getRequirementsForGameEventDefinition() {
//        return requirementsForGameEventDefinition;
//    }

//    public GameEventDefinition getRequiredGameEventDefinition() {
//        return requiredGameEventDefinition;
//    }

    public GameEventChoice getGameEventChoice() {
        return gameEventChoice;
    }

    public GameEventDefinitionRequirementType getType() {
        return type;
    }
}
