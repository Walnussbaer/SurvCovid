package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
	@Size(min=1,max=30)
	private String userName;

	@NotBlank
	@Size(max=50)
	@Email
	@Column(name="E_MAIL")
	private String email;
	
	@Column(name="PASSWORD",nullable=false)
	@NotBlank
	@Size(min=5, max=120)
	@JsonIgnore
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
	
	// Constructor using userName
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
	
	// copy constructor
	public User(User userToCopy) {
		
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty // otherwise we can't bind the password field from incoming requests to the password field of the user object
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
