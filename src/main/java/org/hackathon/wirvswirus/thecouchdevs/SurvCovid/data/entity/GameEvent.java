package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="GAME_EVENT")
public class GameEvent {
	
	@Id
	@Column(name="GAME_EVENT_ID")
	private long id;
	
	@Column(name="TIMESTAMP")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDateTime time;
	
	@Column(name="RECEIVER")
	String receiver;
	
	@ManyToOne
	@JoinColumn(name = "GAME_EVENT_DEFINITION_ID")
	@JsonBackReference
	private GameEventDefinition gameEventDefinition; 
	

}
