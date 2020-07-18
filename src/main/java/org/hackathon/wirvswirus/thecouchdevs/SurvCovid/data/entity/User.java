package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.GameState;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="USER",
uniqueConstraints = {
		@UniqueConstraint(columnNames="USER_NAME"),
		@UniqueConstraint(columnNames = "E_MAIL")
})
public class User {
	
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userId;
	
	@Column(name="USER_NAME", nullable=false)
	@NotBlank
	@Size(max=30)
	private String userName;

	@NotBlank
	@Size(max=50)
	@Email
	@Column(name="E_MAIL")
	private String email;
	
	@Column(name="PASSWORD",nullable=false)
	@NotBlank
	@Size(max=120)
	private String password;

	// assigned roles to this user are stored in a m:n relationship
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="USER_ROLES",
			joinColumns=@JoinColumn(name="USER_ID"),
			inverseJoinColumns = @JoinColumn(name="ROLE_ID")
	)
	// in a HahSet, elements have to be unique
	private Set<Role> roles = new HashSet<>();

	@Embedded
	private UserState userState;

	@Embedded
	private GameState gameState;
	
	public User() {
	}
	
	//Constructor using userName
	public User(String userName) {
		this.userName = userName;
	}

	public User(String userName, String email, String password, UserState userState, GameState gameState){
		this.userName = userName;
		this.email = email;
		this.password = password;
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
		this.userName = userName;
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
