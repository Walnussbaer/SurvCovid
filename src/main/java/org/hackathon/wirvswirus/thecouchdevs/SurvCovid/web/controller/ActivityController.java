package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Activity;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
	
	@Autowired ActivityService activityService;
	
	@GetMapping
	public List<Activity>list(){		
		return activityService.getAllActivities();
		
	}
	
	@GetMapping
	@RequestMapping("{activityId}")
	public Optional<Activity>get(@PathVariable long activityId){		
		
		Optional<Activity> activity = activityService.getActivityById(activityId);	
		
		return activity;		
	}
	
	@PostMapping
	public String create(@RequestBody final Activity activity) {
		
		activityService.saveActivity(activity);
		return Long.toString(activity.getActivityId());		
	}
	
	@RequestMapping(value = "{activityId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long activityId) {	
		activityService.deleteActivityById(activityId);			
	}
	

}
