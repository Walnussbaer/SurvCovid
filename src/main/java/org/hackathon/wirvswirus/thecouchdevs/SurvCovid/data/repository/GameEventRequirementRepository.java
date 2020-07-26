package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventRequirement;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionRequirementType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameEventRequirementRepository extends CrudRepository<GameEventRequirement, Long> {

//    List<GameEventRequirement> findByGameEventDefinition(GameEventDefinition gameEventDefinition);
//    List<GameEventRequirement> findByGameEventDefinitionAndType(GameEventDefinition gameEventDefinition,
//                                                                GameEventDefinitionRequirementType gameEventDefinitionRequirementType);

}

