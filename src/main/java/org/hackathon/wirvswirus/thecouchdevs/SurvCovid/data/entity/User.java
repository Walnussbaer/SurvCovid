package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.time.LocalDateTime;
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
	
	@Column(name="LAST_LOGIN")
	private LocalDateTime lastLogin;

	// assigned roles to this user are stored in a m:n relationship
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="USER_ROLES",
			joinColumns=@JoinColumn(name="USER_ID"),
			inverseJoinColumns = @JoinColumn(name="ROLE_ID")
	)
	// in a HasSet, elements have to be unique
	private Set<Role> roles = new HashSet<>();
	
	public User() {}
	
	//Constructor using userName
	public User(String userName) {
		this.userName = userName;
	}

	public User(String userName, String email, String password){
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	//Get-Methods
	public String getUserName() {
		return this.userName;
	}
		
	public long getUserId() {
		return this.userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public String getEmail() {
		return email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	//Set-Methods
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
