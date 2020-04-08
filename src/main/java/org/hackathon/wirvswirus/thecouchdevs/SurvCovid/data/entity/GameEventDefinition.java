package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="GAME_EVENT_DEFINITION")
public class GameEventDefinition {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(mappedBy = "gameEventDefinition", cascade = CascadeType.ALL) 
	@JsonManagedReference
	List<GameEvent> gameEvents;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SHORT_TITLE")
	private String shortTitle;
	
	@Enumerated(EnumType.STRING)
	private GameEventDefinitionType gameEventDefinitionType;
	
	public GameEventDefinition() {
	    
	}
	
	public GameEventDefinition(String description, String shortTitle, GameEventDefinitionType gameEventDefinitionType) {
		
		this.description = description;
		this.shortTitle = shortTitle;
		this.gameEventDefinitionType = gameEventDefinitionType;
		
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(List<GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public GameEventDefinitionType getGameEventDefinitionType() {
        return gameEventDefinitionType;
    }

    public void setGameEventDefinitionType(GameEventDefinitionType gameEventDefinitionType) {
        this.gameEventDefinitionType = gameEventDefinitionType;
    }

}
