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

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ActivityDefinitionCondition.ActivityDefinitionConditionType;

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
	private long activityDefinitionConditionTypeID;	
	
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
		this.activityDefinitionConditionTypeID = activityDefinitionConditionTypeID;
	}
	
	public ActivityDefinitionConditionType  getActivityDefinitionConditionType() {
		return this.activityDefinitionConditionType;
		
	}
	
	public long getActivityDefinitionConditionTypeID() {
		return this.activityDefinitionConditionTypeID;		
	}
	
	public int getActivityDefinitionConditionValue() {
		return this.getActivityDefinitionConditionValue();
	}
	
	
	
}
