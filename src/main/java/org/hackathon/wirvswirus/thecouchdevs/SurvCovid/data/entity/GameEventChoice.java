package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="GAME_EVENT_CHOICE")
public class GameEventChoice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="DATA")
	private String data;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="GAME_EVENT_IMPACT_ID")
	private GameEventImpact impact;
	

}
