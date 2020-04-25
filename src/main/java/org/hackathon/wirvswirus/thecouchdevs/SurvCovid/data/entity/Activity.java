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

	@ManyToMany
	@JoinTable (
	        name = "ACTIVITY_OUTCOME",
	        joinColumns = @JoinColumn(name="ACTIVITY_ID"),
	        inverseJoinColumns = @JoinColumn(name="ACTIVITY_OUTCOME_ID")
	)
	private List <ActivityOutcome> activityOutcomes;
	
	@ManyToMany
	@JoinTable (
	        name = "ACTIVITY_CONDITION",
	        joinColumns = @JoinColumn(name="ACTIVITY_ID"),
	        inverseJoinColumns = @JoinColumn(name="ACTIVITY_CONDITION_ID")
	)
	private List <ActivityCondition> activityConditions;

	public Activity() {}
	
	//Constructor
	public Activity(String actvityName,
			String activityDescription,
			Integer activityEffort,
			List <ActivityOutcome> activityOutcomes,
			List <ActivityCondition> activityConditions) {
		this.activityName = actvityName;
		this.activityDescription = activityDescription;
		this.activityEffort = activityEffort;
		this.activityOutcomes = activityOutcomes;
		this.activityConditions = activityConditions;
	}

	//Get-Methods
	public String getActivityName() {
		return this.activityName;
	}
	
	public Integer getActivityEffort() {
		return this.activityEffort;
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
