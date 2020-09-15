package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventChoiceDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventDefinitionDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventRequirementDTO;

import java.util.List;

public class GameEventDefinitionCreationRequest {
    private GameEventDefinitionDTO gameEventDefinitionDTO;

    private List<GameEventChoiceDTO> choicesDTOs;

    private List<GameEventRequirementDTO> requirementDTOList;

    public GameEventDefinitionCreationRequest(GameEventDefinitionDTO gameEventDefinitionDTO, List<GameEventChoiceDTO> choicesDTOs, List<GameEventRequirementDTO> requirementDTOList) {
        this.gameEventDefinitionDTO = gameEventDefinitionDTO;
        this.choicesDTOs = choicesDTOs;
        this.requirementDTOList = requirementDTOList;
    }

    public GameEventDefinitionDTO getGameEventDefinitionDTO() {
        return gameEventDefinitionDTO;
    }

    public void setGameEventDefinitionDTO(GameEventDefinitionDTO gameEventDefinitionDTO) {
        this.gameEventDefinitionDTO = gameEventDefinitionDTO;
    }

    public List<GameEventChoiceDTO> getChoicesDTOs() {
        return choicesDTOs;
    }

    public void setChoicesDTOs(List<GameEventChoiceDTO> choicesDTOs) {
        this.choicesDTOs = choicesDTOs;
    }

    public List<GameEventRequirementDTO> getRequirementDTOList() {
        return requirementDTOList;
    }

    public void setRequirementDTOList(List<GameEventRequirementDTO> requirementDTOList) {
        this.requirementDTOList = requirementDTOList;
    }
}
