package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.List;
import java.util.Optional;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ActivityDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.ActivityDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityDefinitionService {
	
	@Autowired
	private ActivityDefinitionRepository activityDefinitionRepository;
	
	@Autowired
	public ActivityDefinitionService(ActivityDefinitionRepository activityRepository) {
		if (activityRepository == null) {
			throw new NullPointerException("activityRepository cannot be null");
		}
		this.activityDefinitionRepository = activityRepository;
	}
	
	public ActivityDefinition saveActivityDefinition(ActivityDefinition activityDefinition) {
		
		if (activityDefinition == null) {
			throw new NullPointerException("ActivityDefition cannot be null");
		}
		this.activityDefinitionRepository.save(activityDefinition);
		
		return activityDefinition;
	}
	
	public List<ActivityDefinition> getAllActivitiesDefinitions(){	
		List<ActivityDefinition> activityDefinitions = (List<ActivityDefinition>) this.activityDefinitionRepository.findAll(); 
		
		return activityDefinitions;
	}
	
	public Optional<ActivityDefinition> getActivityDefinitionById(long id) {
		
		Optional<ActivityDefinition> activityDefinition = this.activityDefinitionRepository.findById(id);
		if(activityDefinition.isEmpty()) {
			throw new NullPointerException("Activityactivity Definition ID does not exist");			
		}		
		
		return activityDefinition;		
	}	

	public void deleteActivityDefinitionById(long id){
		
		Optional<ActivityDefinition> activityDefinition = this.activityDefinitionRepository.findById(id);
		if( activityDefinition.isEmpty()) {
			throw new NullPointerException("Activity Definition id does not exist");			
		}
				
		this.activityDefinitionRepository.deleteById(id);		
	}
	


}
