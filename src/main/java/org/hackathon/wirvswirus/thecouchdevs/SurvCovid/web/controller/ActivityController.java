package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ActivityDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.ActivityDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activityDefinitions")
public class ActivityController {
	
	@Autowired ActivityDefinitionService activityDefinitionService;
	
	@GetMapping
	public List<ActivityDefinition>list(){		
		return activityDefinitionService.getAllActivitiesDefinitions();
		
	}
	
	@GetMapping
	@RequestMapping("{activityDefinitionId}")
	public Optional<ActivityDefinition>get(@PathVariable long activityDefinitionId){		
		
		Optional<ActivityDefinition> activityDefinition = activityDefinitionService.getActivityDefinitionById(activityDefinitionId);	
		
		return activityDefinition;		
	}
	
	@PostMapping
	public String create(@RequestBody final ActivityDefinition activityDefinition) {
		
		activityDefinitionService.saveActivityDefinition(activityDefinition);
		return Long.toString(activityDefinition.getActivityDefinitionId());		
	}
	
	@RequestMapping(value = "{activityId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long activityId) {	
		activityDefinitionService.deleteActivityDefinitionById(activityId);			
	}
	

}
