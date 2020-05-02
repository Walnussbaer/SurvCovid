package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class ActivityManager {	
	
	@Autowired
	ShopService activityService;
	
	@Autowired
	ActivityDefinitionService activityDefinitionService;	

	@Autowired
	UserService userService;

	@Autowired
	InventoryService inventoryService;
	
	public boolean userCanDoActivity(User user, ActivityDefinition activityDefinition ) {
	
	if(activityDefinition.hasActivtyConditions() == false) return true;
	
	List <ActivityDefinitionCondition> activityDefinitionConditions =
			activityDefinition.getActivityDefinitionConditions();
	
	ActivityDefinitionCondition activityDefinitionCondition;
	
	for(var i = 0; i<activityDefinitionConditions.size(); i++) {
		activityDefinitionCondition = activityDefinitionConditions.get(i);
				
	}
		
		
		return true;
	}

}


