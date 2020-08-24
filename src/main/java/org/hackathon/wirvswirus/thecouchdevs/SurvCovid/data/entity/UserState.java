package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


import java.time.LocalDateTime;

@Embeddable
public class UserState {

    @Column(name="LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name="IS_ACTIVE")
    @NotNull
    private boolean active;
    
    public UserState() {}


    public UserState(boolean active) {

        this.active = active;

    }

    public UserState (LocalDateTime lastLogin){
    	
        this.lastLogin = lastLogin;

    } 

    public UserState(LocalDateTime lastLogin, @NotNull boolean active) {
    	
		this.lastLogin = lastLogin;
		this.active = active;
		
	}

	public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return active;
    }

    public void setIsActive(boolean active) {
        this.active = active;
    }
}
