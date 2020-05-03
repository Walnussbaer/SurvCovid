package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="ACTIVITY_CONDITION")
public class ActivityCondition {
	
	@Id
	@Column(name="ACTIVITY_CONDITION_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityConditionId;
	
	@Column(name="ACTIVITY_CONDITION_TYPE")
	private String activityConditionType;
	
	@Column(name="ACTIVITY_CONDITION_VALUE")
	private String activityConditionValue;	
	
	@ManyToMany(mappedBy = "activityConditions")
	@JsonBackReference
	private List<Activity> activities; 
	
}
