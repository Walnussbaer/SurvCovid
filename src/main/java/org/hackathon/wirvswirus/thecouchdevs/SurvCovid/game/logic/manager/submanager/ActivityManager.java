package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ActivityDefinitionCondition.ActivityDefinitionConditionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.InventoryRepository;
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
	
	private InventoryRepository inventoryRepository;
	
	public boolean userCanDoActivity(User user, ActivityDefinition activityDefinition ) {
	
		if(activityDefinition.hasActivtyConditions() == false) return true;
		
		List <ActivityDefinitionCondition> activityDefinitionConditions =
				activityDefinition.getActivityDefinitionConditions();
		
		ActivityDefinitionCondition activityDefinitionCondition;
		
		for(var i = 0; i<activityDefinitionConditions.size(); i++) {
			activityDefinitionCondition = activityDefinitionConditions.get(i);
			if(this._checkActivityDefinitionCondition(user, activityDefinitionCondition)==false) {
				return false;		
			}
		}	
		return true;
	}
	
	
	private boolean _checkActivityDefinitionCondition
	(User user, ActivityDefinitionCondition activityDefinitionCondition) {
		
		ActivityDefinitionConditionType conditionType = activityDefinitionCondition.getActivityDefinitionConditionType();
		long conditionTypeId = activityDefinitionCondition.getActivityDefinitionConditionTypeID();
		int conditonTypeValue = activityDefinitionCondition.getActivityDefinitionConditionValue();
		
		if(conditionType.equals(ActivityDefinitionConditionType.INVENTORY_ITEM)) {
			
			InventoryService inventoryService = new InventoryService(inventoryRepository);
			List<InventoryItem> inventoryList = inventoryService.getInventory(user);
			
			for(var i = 0; i<inventoryList.size(); i++) {
				InventoryItem inventoryItem = inventoryList.get(i);
				long itemid = inventoryItem.getItemTypeId();
				
				if(itemid != conditionTypeId) continue;
				int itemCount = inventoryItem.getItemCount();
				
				if(itemCount < conditonTypeValue ) return false;
				else return true;				
			}
		}		
		
		return true;
		
	}
		
		
	

}


