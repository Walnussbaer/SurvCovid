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

@Entity
@Table(name="GAME_EVENT")
public class GameEvent {
	
	@Id
	@Column(name="GAME_EVENT_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="TIMESTAMP")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDateTime time;
	
	@ManyToOne
	@JoinColumn(name="PLAYER")
	@JsonBackReference
	User player;
	
	@ManyToOne
	@JoinColumn(name = "GAME_EVENT_DEFINITION_ID")
	@JsonBackReference
	private GameEventDefinition gameEventDefinition; 
	
	@Column(name="IS_DONE")
	private boolean isDone;
	
	public GameEvent(){
	    
	}
	
	public GameEvent(LocalDateTime time, User player, GameEventDefinition gameEventDefinition, boolean isDone){
        
        this.time = time;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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
	
	
	
}
