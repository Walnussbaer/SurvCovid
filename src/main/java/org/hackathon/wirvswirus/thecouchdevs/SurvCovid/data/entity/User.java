package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

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
	
	@Column(name="USER_NAME")
	private String userName;
	
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
	
	//Set-Methods
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
