package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventChoiceDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventDefinitionDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventRequirementDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionRequirementType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.GameEventDefinitionCreationRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventChoiceService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionCreationService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@RunWith(SpringRunner.class)
public class GameEventDefinitionCreationServiceTest {

    @Autowired
    private GameEventDefinitionCreationService gameEventDefinitionCreationService;

    @Autowired
    private GameEventDefinitionService gameEventDefinitionService;

    @Autowired
    private GameEventChoiceService gameEventChoiceService;

    @Test
    public void testGameEventDefinitionCreation() {

        // Create first game event which we later depend on
        GameEventDefinition gameEventDefinitionRequired1 = new GameEventDefinition(
                "GameEventDefinition (required) 01",
                "GED required 01",
                GameEventDefinitionType.STORY_EVENT);
        gameEventDefinitionRequired1 = gameEventDefinitionService.saveGameEventDefinition(gameEventDefinitionRequired1);

        // Save possible choice for this event
        GameEventChoice gameEventDefinitionRequired1Choice1 = new GameEventChoice("Choice for first GameEventDefinition");
        gameEventDefinitionRequired1Choice1 = gameEventChoiceService.saveGameEventChoice(gameEventDefinitionRequired1Choice1);

        // Create first game event which we later depend on
        GameEventDefinition gameEventDefinitionRequired2 = new GameEventDefinition(
                "GameEventDefinition (required) 02",
                "GED required 02",
                GameEventDefinitionType.STORY_EVENT);
        gameEventDefinitionRequired2 = gameEventDefinitionService.saveGameEventDefinition(gameEventDefinitionRequired2);

        // Save possible choice for this event
        GameEventChoice gameEventDefinitionRequired2Choice1 = new GameEventChoice("Choice for second GameEventDefinition");
        gameEventDefinitionRequired2Choice1 = gameEventChoiceService.saveGameEventChoice(gameEventDefinitionRequired2Choice1);


        // Prepare target game event definition we want to create using the creation service
        GameEventDefinitionDTO gameEventDefinitionTarget = new GameEventDefinitionDTO(
                1001L,
                "GameEventDefinition Target",
                "GED-Target",
                GameEventDefinitionType.STORY_EVENT);

        // Prepare list of requirements for target game event definition
        List<GameEventRequirementDTO> requirementList = new ArrayList<>();

        GameEventRequirementDTO requirement1 = new GameEventRequirementDTO(gameEventDefinitionRequired1.getId(),
                gameEventDefinitionRequired1.getShortTitle(),
                gameEventDefinitionRequired1.getDescription(),
                gameEventDefinitionRequired1Choice1.getId(),
                gameEventDefinitionRequired1Choice1.getDescription(),
                GameEventDefinitionRequirementType.HAS_HAPPENED);
        requirementList.add(requirement1);

        GameEventRequirementDTO requirement2 = new GameEventRequirementDTO(gameEventDefinitionRequired2.getId(),
                gameEventDefinitionRequired2.getShortTitle(),
                gameEventDefinitionRequired2.getDescription(),
                gameEventDefinitionRequired2Choice1.getId(),
                gameEventDefinitionRequired2Choice1.getDescription(),
                GameEventDefinitionRequirementType.HAS_NOT_HAPPENED);
        requirementList.add(requirement2);

//        // Create first requirement for new game event definition
//        GameEventRequirement requirement1 = new GameEventRequirement(
//                gameEventDefinitionTarget,
//                gameEventDefinitionRequired1,
//                gameEventDefinitionRequired1Choice1,
//                GameEventDefinitionRequirementType.HAS_HAPPENED);
//        requirementList.add(requirement1);
//
//        // Create second requirement for new game event definition
//        GameEventRequirement requirement2 = new GameEventRequirement(
//                gameEventDefinitionTarget,
//                gameEventDefinitionRequired2,
//                gameEventDefinitionRequired2Choice1,
//                GameEventDefinitionRequirementType.HAS_NOT_HAPPENED);
//        requirementList.add(requirement2);

        // Create list of choices for new game event definition
        List<GameEventChoiceDTO> choices = new ArrayList<>();
        choices.add(new GameEventChoiceDTO("GameEventDefinition Target - Choice 1", "some data string"));
        choices.add(new GameEventChoiceDTO("GameEventDefinition Target - Choice 2", "some other data string"));

        GameEventDefinitionCreationRequest gameEventDefinitionCreationRequest = new GameEventDefinitionCreationRequest(
                gameEventDefinitionTarget,
                choices,
                requirementList);

        gameEventDefinitionCreationService.createGameEventDefinitionWithRequirements(gameEventDefinitionCreationRequest);

        System.out.println("--");
    }
}
