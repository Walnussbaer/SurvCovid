package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventRequirement;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.GameEventChoiceRepository;
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
    public GameEventDefinitionRequirementService(GameEventRequirementRepository gameEventRequirementRepository) {

        if (gameEventRequirementRepository == null) {
            throw new NullPointerException("gameEventRequirementRepository cannot be null");
        }
        this.gameEventRequirementRepository = gameEventRequirementRepository;

    }



//    public List<GameEventRequirement> getRequirementsForGameEventDefinition(GameEventDefinition gameEventDefinition) {
//        return this.gameEventRequirementRepository.findByGameEventDefinition(gameEventDefinition);
//    }
}