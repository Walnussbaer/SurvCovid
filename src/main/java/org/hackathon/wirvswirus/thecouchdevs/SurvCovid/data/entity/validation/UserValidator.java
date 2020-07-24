package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.validation;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

@Service
public class UserValidator {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Validates a given user object and checks whether it can be persisted inside the database. 
	 * 
	 * @param user - the user object to check for sanity
	 * @throws NoValidUserException if the given user is not valid
	 */
	public void validateUser(User user) throws NoValidUserException {
		
		if (user == null) {
			throw new NoValidUserException("Please provide a user object!");
		}
		
		if (user.getPassword().isEmpty()) {
			throw new NoValidUserException("Please provide a valid password!");
		}
		
		if (userService.isExistingUserName(user.getUserName())==true){
			throw new NoValidUserException("Please proivde a valid username!");
		}
		
		if (userService.isExistingMail(user.getEmail())==true) {
			throw new NoValidUserException("Please provide a valid email address!");
		}
	}
}
