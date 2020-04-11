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
@Table(name="GAME_EVENT")
public class GameEvent {
	
	@Id
	@Column(name="GAME_EVENT_ID")
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
	@JoinColumn(name="CHOSEN_CHOICE")
	GameEventChoice chosenChoice;
	
	@ManyToOne
	@JoinColumn(name="PLAYER")
	@JsonBackReference
	User player;
	
	@ManyToOne
	@JoinColumn(name = "GAME_EVENT_DEFINITION_ID")
	@JsonManagedReference
	private GameEventDefinition gameEventDefinition; 
	
	@Column(name="IS_DONE")
	private boolean isDone;
	
	public GameEvent(){
	    
	}
	
	public GameEvent(LocalDateTime time, User player, GameEventDefinition gameEventDefinition, boolean isDone){
        
        this.serverCreationTime = time;
        this.player = player;
        this.gameEventDefinition = gameEventDefinition;
        this.isDone = isDone;
        
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public GameEventDefinition getGameEventDefinition() {
        return gameEventDefinition;
    }

    public void setGameEventDefinition(GameEventDefinition gameEventDefinition) {
        this.gameEventDefinition = gameEventDefinition;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public LocalDateTime getServerCreationTime() {
        return serverCreationTime;
    }

    public void setServerCreationTime(LocalDateTime serverCreationTime) {
        this.serverCreationTime = serverCreationTime;
    }

    public LocalDateTime getClientReceivedTime() {
        return clientReceivedTime;
    }

    public void setClientReceivedTime(LocalDateTime clientReceivedTime) {
        this.clientReceivedTime = clientReceivedTime;
    }
    
    public GameEventChoice getChosenChoice() {
        return chosenChoice;
    }

    public void setChosenChoice(GameEventChoice chosenChoice) {
        this.chosenChoice = chosenChoice;
    }
    
	
	
	
}
