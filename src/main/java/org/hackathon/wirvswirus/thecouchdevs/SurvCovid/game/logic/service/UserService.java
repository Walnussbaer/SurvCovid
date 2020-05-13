package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		if (userRepository == null) {
			throw new NullPointerException("userRepository cannot be null");
		}
		this.userRepository = userRepository;
	}
	
	public User saveUser(User user) {
		
		if (user == null) {
			throw new NullPointerException("user cannot be null");
		}
		this.userRepository.save(user);
		
		return user;
	}
	
	public List<User> getAllUsers(){
		
		List<User> users = (List<User>) this.userRepository.findAll(); 
		return users;
	}
	
	public Optional<User> getUserById(long id) {
		
		Optional<User> user = this.userRepository.findById(id);
		if( user.isEmpty()) {
			throw new NullPointerException("user_id does not exist");			
		}		
		
		return user;		
	}
	
	public void changeUserNameById(long id, String userName) {
			
		this.userRepository.findById(id)
		.map(user -> {
			user.setUserName(userName);
			return this.userRepository.save(user);
			
		});		
		
	}
	
	public void deleteUserById(long id){
		
		Optional<User> user = this.userRepository.findById(id);
		if( user.isEmpty()) {
			throw new NullPointerException("user_id does not exist");			
		}
				
		this.userRepository.deleteById(id);		
	}

	public boolean checkIfExistsByUserName(String username){

		return userRepository.existsByUserName(username);

	}

	public boolean checkIfExistsByMail(String email){

		return userRepository.existsByEmail(email);

	}

	public User getUserByName(String userName) {
		return this.userRepository.findByUserName(userName);
	}

}
