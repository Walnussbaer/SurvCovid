package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.UserState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoActionRequiredException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.UserUpdateRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.validation.UserValidator;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.UserRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller.ProfileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserValidator userValidator;

	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
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
	
	public User updateUser(UserUpdateRequest userUpdate, BindingResult bindingResult) throws NoValidUserException, UserNotExistingException, JsonProcessingException, NoActionRequiredException {
				
		User existingUser;
		User potentialUpdatedUser;
		User updatedUser;
		
		// throws a UserNotExistingException if user cannot be found
		existingUser = this.getUserById(userUpdate.getUserId());
			
		// map incoming data to existing user object
		try {
			potentialUpdatedUser = this.mapUserUpdateToExistingUser(userUpdate, existingUser);
		}
		catch (NoActionRequiredException e) {
			System.out.println("Update requested but data did not change. Returning existing user from database ... ");
			return existingUser;
		}
		
		// throws a NoValidUserException to the caller if the update is not valid
		this.userValidator.validateUserUpdate(potentialUpdatedUser, userUpdate, existingUser, bindingResult);
			
			
		updatedUser = this.userRepository.save(potentialUpdatedUser);

		
		return updatedUser;
		
	}
	
	private User mapUserUpdateToExistingUser(UserUpdateRequest userUpdate, User existingUser) throws NoActionRequiredException, JsonProcessingException {
		
		int updateCount = 0;

		// create a deep copy of the object
		ObjectMapper mapper = new ObjectMapper();
		User potentialUpdatedUser = null;

		try {
			potentialUpdatedUser = mapper.readValue(mapper.writeValueAsString(existingUser), User.class);
		}
		catch(JsonProcessingException ex) {
			// Should not happen since we serialize and deserialize
			System.err.println("This should not have happened!");
			throw ex;
		}

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
		else {
			// If not new password was provided, set the old one
			potentialUpdatedUser.setPassword(existingUser.getPassword());
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
	 * Updates the date and time of the last login for the given user id.
	 *
	 * @param userId - the id of the user that has logged in
	 * @return - the updated date and time for the new login
	 */
	public LocalDateTime updateLastLogin(long userId) throws UserNotExistingException {

		LocalDateTime lastLogin;
		Optional<User> optionalUser;
		User user;

		lastLogin = LocalDateTime.now();

		optionalUser = this.userRepository.findById(userId);

		if (optionalUser.isEmpty()){
			throw new UserNotExistingException("There is no user with id " + userId);
		}

		user = optionalUser.get();
		user.getUserState().setLastLogin(lastLogin);
		userRepository.save(user);

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
			throw new UserNotExistingException("There is no user with username " + userName);
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

	/**
	 * Looks for a {@link User} with the given mail.
	 *
	 * @param mail the mail of the user to search for
	 *
	 * @return the user instance
	 *
	 * @throws UserNotExistingException if there is no user for the given mail
	 */
	public User getUserByMail(String mail) throws UserNotExistingException {
		
		Optional<User> user = this.userRepository.findByEmail(mail);
		
		if (user.isEmpty()) {
			throw new UserNotExistingException("There is not user with email " + mail);
		}
		
		return user.get();
		
	}

	/**
	 * Changes the password for a user with the given id.
	 * <p>
	 * If no user is found for the given id, nothing happens.
	 *
	 * @param userId the id of the user to change the password for
	 * @param newPassword the new password
	 */
	public void changePassword(long userId, String newPassword) {

		Optional<User> optUser = this.userRepository.findById(userId);
		User user;

		if (optUser.isPresent()){

			user = optUser.get();

			user.setPassword(this.encoder.encode(newPassword));

			this.userRepository.save(user);

			this.logger.info("User " + user.getUserId() + " has successfully changed his/her password.");

		}
	}

}
