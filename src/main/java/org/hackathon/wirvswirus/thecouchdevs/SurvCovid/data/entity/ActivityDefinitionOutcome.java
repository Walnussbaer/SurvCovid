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
@Table(name="ACTIVITY_DEFINITION_OUTCOME")
public class ActivityDefinitionOutcome {	

	@Id
	@Column(name="ACTIVITY_DEFINITION_OUTCOME_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long activityDefinitionOutcomeId;
	
	@Enumerated(EnumType.ORDINAL)
	private ActivityDefinitionOutcomeType activityDefinitionOutcomeType;
	
	@Column(name="ACTIVITY_DEFINITION_OUTCOME_VALUE")
	private Integer activityDefinitionOutcomeValue;		
	
	@ManyToMany(mappedBy = "activityDefinitionOutcomes")
	@JsonBackReference
	private List<ActivityDefinition> activityDefinitions; 
	
	public enum ActivityDefinitionOutcomeType{
		INVENTORY_ITEM
	}
	
	public ActivityDefinitionOutcome(ActivityDefinitionOutcomeType activityDefinitionOutcomeType
			, long activityDefinitionOutcomeTypeID
			, int activityDefinitionOutcomeValue) {
		this.activityDefinitionOutcomeType = activityDefinitionOutcomeType;		
		this.activityDefinitionOutcomeValue = activityDefinitionOutcomeValue;		
	}
	
}
