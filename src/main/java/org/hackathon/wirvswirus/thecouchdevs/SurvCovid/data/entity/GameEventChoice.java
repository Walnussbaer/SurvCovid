package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="GAME_EVENT_CHOICE")
public class GameEventChoice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="DATA")
	private String data;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="GAME_EVENT_IMPACT_ID")
	private GameEventImpact impact;
	
	@ManyToMany(mappedBy = "gameEventChoices")
	@JsonBackReference
	private List<GameEventDefinition> gameEventDefinitions; 
	
	public GameEventChoice(String description, List<GameEventDefinition> gameEventDefinitions) {
	    this.description = description;
	    this.gameEventDefinitions = gameEventDefinitions;
	    
	}
	
	public GameEventChoice() {
	    
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GameEventImpact getImpact() {
        return impact;
    }

    public void setImpact(GameEventImpact impact) {
        this.impact = impact;
    }

    public List<GameEventDefinition> getGameEventDefinitions() {
        return gameEventDefinitions;
    }

    public void setGameEventDefinitions(List<GameEventDefinition> gameEventDefinitions) {
        this.gameEventDefinitions = gameEventDefinitions;
    }
	

}
