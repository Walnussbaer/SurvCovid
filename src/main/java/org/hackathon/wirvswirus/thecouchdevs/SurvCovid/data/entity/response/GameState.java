package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class GameState {

    @Column(name="INGAME_DATE")
    LocalDateTime ingameDate;

    @Column(name="PLAYER_HEALTH")
    float playerHealth;

    @Column(name="PLAYER_FITNESS")
    float playerFitness;

    @Column(name="PLAYER_ACCOUNT_BALANCE")
    double playerAccountBalance;

    public GameState () {

    }

    public GameState(LocalDateTime ingameDate, float playerHealth, float playerFitness, double playerAccountBalance) {
        this.ingameDate = ingameDate;
        this.playerHealth = playerHealth;
        this.playerFitness = playerFitness;
        this.playerAccountBalance = playerAccountBalance;
    }

    public LocalDateTime getIngameDate() {
        return ingameDate;
    }

    public void setIngameDate(LocalDateTime ingameDate) {
        this.ingameDate = ingameDate;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public float getPlayerFitness() {
        return playerFitness;
    }

    public void setPlayerFitness(float playerFitness) {
        this.playerFitness = playerFitness;
    }

    public double getPlayerAccountBalance() {
        return playerAccountBalance;
    }

    public void setPlayerAccountBalance(double playerAccountBalance) {
        this.playerAccountBalance = playerAccountBalance;
    }
}
