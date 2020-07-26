package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response;

import java.util.List;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;

/**
 * This response is used in the {@link UserController} for every response back to the client.
 * 
 * @author volke
 *
 */
public class UserControllerResponse extends SurvCovidBaseResponse {
	
	private List<User> data;
	
	public List<User> getData(){
		return this.data;
	}
	
	public void setData (List<User> users){
		this.data = users;
	}
	
	public UserControllerResponse() {}
	
	public UserControllerResponse(String message, List<User> users) {
		super(message);
		this.data = users;
	}

}
