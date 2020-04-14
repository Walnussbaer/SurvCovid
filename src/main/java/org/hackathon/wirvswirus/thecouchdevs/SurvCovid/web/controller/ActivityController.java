package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Activity;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {
	
	@Autowired ActivityService activityService;
	
	@GetMapping("/activities")
	public List<Activity> sendActivitys(){		
		return activityService.getAllActivities();
		
	}
	
	@GetMapping("/activity")
	public Optional<Activity> sendActivity(@RequestParam(name="activityNumber", required=true)long activityNumber){		
		
		Optional<Activity> activity = activityService.getActivityById(activityNumber);	
		
		return activity;		
	}
	
	@PostMapping("/activity")
	public String registerActivity(@RequestParam(name="activityName", required=true)String activityName,
			@RequestParam(name="activityDescription", required=true)String activityDescription,
			@RequestParam(name="activityEffort", required=true)Integer activityEffort) {
		
		Activity activity = new Activity(activityName, activityDescription, activityEffort);		
		
		return Long.toString(activity.getActivityId());		
	}
	
	@DeleteMapping("/activity")
	public void deleteActivity(@RequestParam(name="activityNumber", required=true)long activityNumber) {	
		activityService.deleteActivityById(activityNumber);			
	}
	

}
