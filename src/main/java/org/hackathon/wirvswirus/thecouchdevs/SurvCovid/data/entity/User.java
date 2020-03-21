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
	
	@Column(name="USER_ID")
	private String userId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userNumber;
	
	@Column(name="USER_NAME")
	private String userName;
	
	public User() {}
	
	public User(String userName) {
		this.userName = userName;
		// TODO: write a random string generator
		this.userId = userName + "_" + userNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserId() {
		return this.userId;
	}
	

}
