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
    private boolean isActive;
    
    public UserState() {}


    public UserState(boolean isActive) {

        this.isActive = isActive;

    }

    public UserState (LocalDateTime lastLogin){
    	
        this.lastLogin = lastLogin;

    } 

    public UserState(LocalDateTime lastLogin, @NotNull boolean isActive) {
    	
		this.lastLogin = lastLogin;
		this.isActive = isActive;
		
	}

	public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
