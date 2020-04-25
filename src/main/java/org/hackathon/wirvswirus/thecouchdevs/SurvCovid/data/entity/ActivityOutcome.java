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
@Table(name="ACTIVITY_OUTCOME")
public class ActivityOutcome {
	

	@Id
	@Column(name="ACTIVITY_OUTCOME_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityOutcomeId;
	
	@Column(name="ACTIVITY_OUTCOME_TYPE")
	private String activityOutcomeType;
	
	@Column(name="ACTIVITY_OUTCOME_VALUE")
	private String activityOutcomeValue;	
	
	@ManyToMany(mappedBy = "activityOutcomes")
	@JsonBackReference
	private List<Activity> activities; 
	
}
