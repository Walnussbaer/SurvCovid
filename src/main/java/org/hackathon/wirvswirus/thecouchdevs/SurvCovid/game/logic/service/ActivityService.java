package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.List;
import java.util.Optional;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Activity;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	public ActivityService(ActivityRepository activityRepository) {
		if (activityRepository == null) {
			throw new NullPointerException("activityRepository cannot be null");
		}
		this.activityRepository = activityRepository;
	}
	
	public Activity saveActivity(Activity activity) {
		
		if (activity == null) {
			throw new NullPointerException("Activity cannot be null");
		}
		this.activityRepository.save(activity);		
		return activity;
	}
	
	public List<Activity> getAllActivities(){	
		List<Activity> activities = (List<Activity>) this.activityRepository.findAll(); 
		
		return activities;
	}
	
	public Optional<Activity> getActivityById(long id) {
		
		Optional<Activity> activity = this.activityRepository.findById(id);
		if(activity.isEmpty()) {
			throw new NullPointerException("Activityactivity  ID does not exist");			
		}		
		
		return activity;		
	}	

	public void deleteActivityById(long id){
		
		Optional<Activity> activity = this.activityRepository.findById(id);
		if( activity.isEmpty()) {
			throw new NullPointerException("Activity id does not exist");			
		}
				
		this.activityRepository.deleteById(id);		
	}
	


}
