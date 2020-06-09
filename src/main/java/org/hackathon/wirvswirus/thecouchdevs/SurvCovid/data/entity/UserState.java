package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class UserState {

    @Column(name="LAST_LOGIN")
    private LocalDateTime lastLogin;

    public UserState (){

    }

    public UserState (LocalDateTime lastLogin){

        this.lastLogin = lastLogin;

    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }



}
