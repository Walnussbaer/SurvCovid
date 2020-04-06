package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import java.util.Random;

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
	
	//Constructor using userName
	public User(String userName) {
		this.userName = userName;
		this.userNumber = generateUserId(1000,9999);
		this.userId = this.userName + "_" + this.userNumber;
	}

	//Get-Methods
	public String getUserName() {
		return this.userName;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public long getUserNumber() {
		return this.userNumber;
	}
	
	//Set-Methods

	public void setUserName(String userName) {
		this.userName = userName;
		this.userId =  userName + "_" + this.userNumber;
	}
	
	/*	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setUserNumber(long userNumber) {
		this.userNumber = userNumber;
	}
	*/
	
	//Other Methods
	
	 private long generateUserId(int min, int max) {
		 Random random = new Random();
		 var userId = random.ints(min,(max+1)).findFirst().getAsInt();		 
		 return userId;
	 }
	 
	 
	
	
	
	

}
