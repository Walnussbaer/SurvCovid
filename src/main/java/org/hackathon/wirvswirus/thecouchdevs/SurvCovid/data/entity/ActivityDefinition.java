package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVITY_DEFINITION")
public class ActivityDefinition {
	
		
	@Id
	@Column(name="ACTIVITY_DEFINITION_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityDefinitionId;
	
	@Column(name="ACTIVITY_DEFINITION_NAME")
	private String activityDefinitionName;
	
	@Column(name="ACTIVITY_DEFINITION_DESCRIPTION")
	private String activityDefinitionDescription;
	
	@Column(name="ACTIVITY_DEFINITION_EFFORT")
	private Integer activityDefinitionEffort;	
	
	//ToDo: Outcome (List), Condition (List)

	@ManyToMany
	@JoinTable (
	        name = "ACTIVITY_DEFINITION_OUTCOME",
	        joinColumns = @JoinColumn(name="ACTIVITY_DEFINITION_ID"),
	        inverseJoinColumns = @JoinColumn(name="ACTIVITY_DEFINITION_OUTCOME_ID")
	)
	private List <ActivityDefinitionOutcome> activityDefinitionOutcomes;
	
	@ManyToMany
	@JoinTable (
	        name = "ACTIVITY_DEFINITION_CONDITION",
	        joinColumns = @JoinColumn(name="ACTIVITY_DEFINITION_ID"),
	        inverseJoinColumns = @JoinColumn(name="ACTIVITY_DEFINITION_CONDITION_ID")
	)
	private List <ActivityDefinitionCondition> activityDefinitionConditions;

	public ActivityDefinition() {}
	
	//Constructor
	public ActivityDefinition(String activityDefinitionName,
			String activityDefinitionDescription,
			Integer activityDefinitionEffort,
			List <ActivityDefinitionCondition> activityDefinitionConditions,
			List <ActivityDefinitionOutcome> activityDefinitionOutcomes)
	{
		this.activityDefinitionName = activityDefinitionName;
		this.activityDefinitionDescription = activityDefinitionDescription;
		this.activityDefinitionEffort = activityDefinitionEffort;
		this.activityDefinitionConditions = activityDefinitionConditions;
		this.activityDefinitionOutcomes = activityDefinitionOutcomes;
		
	}


	public boolean hasActivtyConditions() {
		if(activityDefinitionConditions.size() == 0 ) return false;
		return true;		
	}

	public long getActivityDefinitionId() {
		return activityDefinitionId;
	}

	public void setActivityDefinitionId(long activityDefinitionId) {
		this.activityDefinitionId = activityDefinitionId;
	}

	public String getActivityDefinitionName() {
		return activityDefinitionName;
	}

	public void setActivityDefinitionName(String activityDefinitionName) {
		this.activityDefinitionName = activityDefinitionName;
	}

	public String getActivityDefinitionDescription() {
		return activityDefinitionDescription;
	}

	public void setActivityDefinitionDescription(String activityDefinitionDescription) {
		this.activityDefinitionDescription = activityDefinitionDescription;
	}

	public Integer getActivityDefinitionEffort() {
		return activityDefinitionEffort;
	}

	public void setActivityDefinitionEffort(Integer activityDefinitionEffort) {
		this.activityDefinitionEffort = activityDefinitionEffort;
	}

	public List<ActivityDefinitionOutcome> getActivityDefinitionOutcomes() {
		return activityDefinitionOutcomes;
	}

	public void setActivityDefinitionOutcomes(List<ActivityDefinitionOutcome> activityDefinitionOutcomes) {
		this.activityDefinitionOutcomes = activityDefinitionOutcomes;
	}

	public List<ActivityDefinitionCondition> getActivityDefinitionConditions() {
		return activityDefinitionConditions;
	}

	public void setActivityDefinitionConditions(List<ActivityDefinitionCondition> activityDefinitionConditions) {
		this.activityDefinitionConditions = activityDefinitionConditions;
	}
}
