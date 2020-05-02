package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="ACTIVITY_CONDITION")
public class ActivityDefinitionCondition {
	
	@Id
	@Column(name="ACTIVITY_DEFINITION_CONDITION_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityDefinitionConditionId;
	
	@Enumerated(EnumType.ORDINAL)
	private ActivityDefinitionConditionType activityDefinitionConditionType;
	
	@Column(name="ACTIVITY_DEFINITION_CONDITION_TYPE_ID")
	private long activityDefinitionTypeID;	
	
	@Column(name="ACTIVITY_DEFINITION_CONDITION_VALUE")
	private int activityDefinitionConditionValue;	
	
	@ManyToMany(mappedBy = "activityDefinitionConditions")
	@JsonBackReference
	private List<ActivityDefinition> activityDefinitions; 
	
	public enum ActivityDefinitionConditionType{
		INVENTORY_ITEM
	}
	
	public ActivityDefinitionCondition(ActivityDefinitionConditionType activityDefinitionConditionType
			, long activityDefinitionConditionTypeID
			, int activityDefinitionConditionValue) {
		this.activityDefinitionConditionType = activityDefinitionConditionType;		
		this.activityDefinitionConditionValue = activityDefinitionConditionValue;		
		this.activityDefinitionTypeID = activityDefinitionConditionTypeID;
	}
	
}
