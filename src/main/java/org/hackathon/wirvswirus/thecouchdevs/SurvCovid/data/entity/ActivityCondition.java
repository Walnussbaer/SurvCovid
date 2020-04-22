package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ActivityCondition {
	
	@Id
	@Column(name="ACTIVITY_CONDITION_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityConditionId;
	
	@Column(name="ACTIVITY_CONDITION_TYPE")
	private String activityConditionType;
	
	@Column(name="ACTIVITY_CONDITION_VALUE")
	private String activityConditionValue;	
	
	
}
