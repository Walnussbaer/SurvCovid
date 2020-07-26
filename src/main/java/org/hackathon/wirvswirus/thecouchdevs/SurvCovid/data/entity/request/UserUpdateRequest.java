package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Role;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.UserState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.GameState;

/**
 * This class is used to represent data that updates an existing user record. 
 * 
 * @author volke
 *
 */
public class UserUpdateRequest {
	
	/* necessary values */ 
	@NotNull
	private long userId;
	
	/* optional values */ 
	@Size(min=1,max=30)
	private String userName;
	
	@Email
	private String email;
	
	@Size(min=5, max=120)
	private String password;
	
	private Set<Role> roles = new HashSet<>();
	
	private UserState userState;
	
	private GameState gameState;
	
	

	public UserUpdateRequest(@NotNull long userId, String userName, String email, String password, Set<Role> roles,
			UserState userState, GameState gameState) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.userState = userState;
		this.gameState = gameState;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		
		return userName;
	}

	public void setUserName(String userName) {
		// we don't want to allow whitespaces, Spring validates after setting the value
		this.userName = userName.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	

}
