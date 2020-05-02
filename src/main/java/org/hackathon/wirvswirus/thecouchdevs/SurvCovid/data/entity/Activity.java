package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="ACTIVITY")
public class Activity {
	
	@Id
	@Column(name="ACTIVITY_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="CREATION_TIME")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	@JsonIgnore //ignore this attribute in the API
	LocalDateTime serverCreationTime;
	
	@Column(name="CLIENT_RECEIVED_TIME")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDateTime clientReceivedTime;
		
	@ManyToOne
	@JoinColumn(name="PLAYER")
	@JsonBackReference
	User player;
	
	@ManyToOne
	@JoinColumn(name = "ACTIVITY_DEFINITION_ID")
	@JsonManagedReference
	private ActivityDefinition activityDefinition; 
			
	public Activity(LocalDateTime time, User player, ActivityDefinition activityDefinition){        
        this.serverCreationTime = time;
        this.player = player;
        this.activityDefinition = activityDefinition;         
	}

    public long getId() {
        return id;
    }
   

    
	
	
	
}
