package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.UserState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoActionRequiredException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.UserUpdateRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.validation.UserValidator;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.google.gson.Gson;

@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		if (userRepository == null) {
			throw new NullPointerException("Could not intialize the User Repository!");
		}
		this.userRepository = userRepository;
	}
	
	public User saveUser(User user, BindingResult bindingResult) throws NoValidUserException {
		
		// throws a NoValidUserException back to the caller
		userValidator.validateNewUser(user, bindingResult);
		
		// hash the password
		user.setPassword(encoder.encode(user.getPassword()));
		
		user.setUserState(new UserState(true));
		user.setGameState(new GameState());	
		
		user = this.userRepository.save(user);
		
		return user;
	}
	
	public User updateUser(UserUpdateRequest userUpdate, BindingResult bindingResult) throws NoValidUserException, UserNotExistingException {
				
		User existingUser;
		User potentialUpdatedUser;
		User updatedUser;
		
		// throws a UserNotExistingException if user cannot be found
		existingUser = this.getUserById(userUpdate.getUserId());
			
		// map incoming data to existing user object
		try {
			potentialUpdatedUser = this.mapUserUpdateToExistingUser(userUpdate, existingUser);
		}
		catch (NoActionRequiredException nare) {
			System.out.println("Update requested but data did not change. Returning existing user from database ... ");
			return existingUser;
		}
		
		// throws a NoValidUserException to the caller if the update is not valid
		this.userValidator.validateUserUpdate(potentialUpdatedUser, userUpdate, existingUser, bindingResult);
			
			
		updatedUser = this.userRepository.save(potentialUpdatedUser);

		
		return updatedUser;
		
	}
	
	private User mapUserUpdateToExistingUser(UserUpdateRequest userUpdate, User existingUser) throws NoActionRequiredException {
		
		int updateCount = 0;
		
		Gson gson = new Gson();
		
		// create a deep copy of the object
		User potentialUpdatedUser = gson.fromJson(gson.toJson(existingUser), User.class);
		
		if (userUpdate.getUserName() != null && !(userUpdate.getUserName().isEmpty()) && !userUpdate.getUserName().equals(existingUser.getUserName())) {
			updateCount+=1;
			potentialUpdatedUser.setUserName(userUpdate.getUserName());
		}
		
		if (userUpdate.getEmail() != null && !userUpdate.getEmail().isEmpty() && !userUpdate.getEmail().equals(existingUser.getEmail())) {
			updateCount+=1;
			potentialUpdatedUser.setEmail(userUpdate.getEmail());
		}
		
		if (userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()) {
			updateCount+=1;
			potentialUpdatedUser.setPassword(encoder.encode(userUpdate.getPassword()));
		}
		
		if (userUpdate.getRoles() != null && userUpdate.getRoles().isEmpty()) {
			updateCount+=1;
			potentialUpdatedUser.setRoles(userUpdate.getRoles());
		}
		
		if (userUpdate.getUserState() != null) {
			updateCount+=1;
			potentialUpdatedUser.setUserState(userUpdate.getUserState());
		}
		
		if (userUpdate.getGameState() != null) {
			updateCount+=1;
			potentialUpdatedUser.setGameState(userUpdate.getGameState());
		}
		
		if (updateCount == 0) {
			throw new NoActionRequiredException("Nothing happened!");
		}
		
		return potentialUpdatedUser;
		
	}
	
	public List<User> getAllUsers(){
		
		List<User> users = (List<User>) this.userRepository.findAll(); 
		return users;
	}
	
	/**
	 * Changes the username of an already existing user. 
	 * 
	 * @param id - the id of the user whoose username shall be changed
	 * @param userName - the new username
	 */
	public void changeUserNameById(long id, String userName) {
			
		this.userRepository.findById(id)
		.map(user -> {
			user.setUserName(userName);
			return this.userRepository.save(user);
			
		});		
		
	}
	
	/**
	 * Deletes a user with the given ID
	 * 
	 * @param id - the id of the user that shall be deleted
	 */
	public void deleteUserById(long id) throws UserNotExistingException{
		
		Optional<User> user = this.userRepository.findById(id);
		if( user.isEmpty()) {
			throw new UserNotExistingException("There is no user with userID " + id);			
		}
				
		this.userRepository.deleteById(id);		
	}

	/**
	 * Checks if there is a user in the database with the given username. 
	 * 
	 * @param username - the username which needs to be searched for in the database
	 * @return true if a user with the given username exists, else false
	 */
	public boolean isExistingUserName(String username){

		return userRepository.existsByUserName(username);

	}

	/**
	 * Checks if there is a user in the database with the given email. 
	 * 
	 * @param email - the mail which needs to be searched for in the database
	 * @return true if a user with the given username exists, else false
	 */
	public boolean isExistingMail(String email){

		return userRepository.existsByEmail(email);

	}

	/**
	 * Updates the date and time of the last login of the given user.
	 *
	 * @param user - the user that has logged in
	 * @return - the updated date and time for the new login
	 */
	public LocalDateTime updateLastLogin(User user) {

		LocalDateTime lastLogin;

		// validity check
		if (user == null) {
			return null;
		}

		lastLogin = LocalDateTime.now();

		user.getUserState().setLastLogin(lastLogin);

		System.out.println("User " + user.getUserName() + " has logged in successfully! Last login date is now: " + lastLogin + ", Account state is " + user.getUserState().isActive());

		return lastLogin;

	}

	/**
	 * Takes in a username and checks whether there is a user with this username in the database, if yes it returns the user. 
	 * 
	 * @param userName - the username to serch for
	 * @return the user object if there is a user with the given username
	 * @throws UserNotExistingException an Exception if there is no user with the given name
	 */
	public User getUserByName(String userName) throws UserNotExistingException {

		Optional<User> user = userRepository.findByUserName(userName);
		
		if (user.isEmpty()) {
			throw new UserNotExistingException("There is not user with username " + userName);
		}
		
		return user.get();
	}
	
	/**
	 * Searchs for a user with the given id and returns the user object if the user ist present. 
	 * 
	 * @param id - the id of the user to use for the search
	 * @return - an instance of {@link User}
	 * @throws - a UserNotExistingException if the user is not existing
	 */
	public User getUserById(long id) throws UserNotExistingException {
		
		Optional<User> user = this.userRepository.findById(id);
		
		if( user.isEmpty()) {
			throw new UserNotExistingException("There is no user with userId " + id);
		}		
		
		return user.get();		
	}
	
	public User getUserByMail(String mail) throws UserNotExistingException {
		
		Optional<User> user = this.userRepository.findByEmail(mail);
		
		if (user.isEmpty()) {
			throw new UserNotExistingException("There is not user with email " + mail);
		}
		
		return user.get();
		
	}

}
