package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVITY")
public class Activity {
	
		
	@Id
	@Column(name="ACTIVITY_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityId;
	
	@Column(name="ACTIVITY_NAME")
	private String activityName;
	
	@Column(name="ACTIVITY_DESCRIPTION")
	private String activityDescription;
	
	@Column(name="ACTIVITY_EFFORT")
	private Integer activityEffort;	
	
	//ToDo: Outcome (List), Condition (List)
	
	//@Column(name="ACTIVITY_OUTCOMES")
	//private <List> ActivityOutcome activityOutcomes;
	
	//@Column(name="ACTIVITY_CONDITION")
	//private <List> ActivityCondition activityCondition;		
		
	public Activity() {}
	
	//Constructor using userName
	public Activity(String actvityName, String activityDescription, Integer activityEffort) {
		this.activityName = actvityName;
		this.activityDescription = activityDescription;
		this.activityEffort = activityEffort;
	}

	//Get-Methods
	public String getActivityName() {
		return this.activityName;
	}
	
	public Integer getActivityEffort() {
		return this.getActivityEffort();
	}
		
	public long getActivityId() {
		return this.activityId;
	}
	
	//Set-Methods
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	
	public void setActivityEffort(Integer activityEffort) {
		this.activityEffort = activityEffort;
	}

}
