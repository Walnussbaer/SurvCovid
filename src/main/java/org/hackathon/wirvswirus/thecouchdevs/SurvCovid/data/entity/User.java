package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userId;
	
	@Column(name="USER_NAME", nullable=false, unique=true)
	private String userName;
	
	@Column(name="PASSWORD",nullable=false)
	private String password;
	
	@Column(name="LAST_LOGIN")
	private LocalDateTime lastLogin;
	
	public User() {}
	
	//Constructor using userName
	public User(String userName) {
		this.userName = userName;
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

}
