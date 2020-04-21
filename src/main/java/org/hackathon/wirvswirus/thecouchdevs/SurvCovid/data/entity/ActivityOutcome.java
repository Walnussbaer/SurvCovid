package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ActivityOutcome {

	@Id
	@Column(name="ACTIVITY_OUTCOME_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityOutcomeId;
	
	@Column(name="ACTIVITY_OUTCOME_TYPE")
	private String activityOutcomeType;
	
	@Column(name="ACTIVITY_OUTCOME_VALUE")
	private String activityOutcomeValue;		
	
}
