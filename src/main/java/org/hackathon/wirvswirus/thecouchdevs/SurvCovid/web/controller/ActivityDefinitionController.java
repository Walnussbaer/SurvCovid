package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ActivityDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.ActivityDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activityDefinitions")
public class ActivityDefinitionController {
	
	@Autowired ActivityDefinitionService activityDefinitionService;
	
	@GetMapping
	public List<ActivityDefinition>list(){

		return activityDefinitionService.getAllActivitiesDefinitions();

	}
	
	@GetMapping
	@RequestMapping("{activityDefinitionId}")
	public ActivityDefinition get(@PathVariable long activityDefinitionId){
		
		Optional<ActivityDefinition> activityDefinition = activityDefinitionService.getActivityDefinitionById(activityDefinitionId);

		if (activityDefinition.isEmpty()) {
			return null;
		}
		
		return activityDefinition.get();
	}
	
	@PostMapping
	public ActivityDefinition create(@RequestBody final ActivityDefinition activityDefinition) {

		ActivityDefinition createdActivityDefinition;

		createdActivityDefinition = activityDefinitionService.saveActivityDefinition(activityDefinition);

		return createdActivityDefinition;
	}
	
	@RequestMapping(value = "{activityDefinitionId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long activityDefinitionId) {

		activityDefinitionService.deleteActivityDefinitionById(activityDefinitionId);			
	}

	@PutMapping
	public ActivityDefinition update(@RequestBody final ActivityDefinition activityDefinition) {

		ActivityDefinition updatedActivityDefinition;

		updatedActivityDefinition = activityDefinitionService.saveActivityDefinition(activityDefinition);

		return updatedActivityDefinition;

	}
}
