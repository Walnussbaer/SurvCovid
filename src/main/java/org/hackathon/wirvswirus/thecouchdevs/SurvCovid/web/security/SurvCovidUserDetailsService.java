package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // application will automatically detect it and create a bean out of this class
public class SurvCovidUserDetailsService implements UserDetailsService{

	/*
	 * The UserDetailsService interface is used to retrieve user-related data. 
	 * It has one method named loadUserByUsername() which can be overridden to customize the process of finding the user.
	 * It is used by the DaoAuthenticationProvider to load details about the user during authentication.
	 * 
	 */
	
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userService.getUserByName(username);
		
		if (user==null) {
			throw new UsernameNotFoundException(username);
		}
		
		// wrap user into a myUserPrincipal object
		return new SurvCovidUserPrincipal(user);
		
	}
	


}
