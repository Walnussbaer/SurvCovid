package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GAME_EVENT_IMPACT")
public class GameEventImpact {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;

}
