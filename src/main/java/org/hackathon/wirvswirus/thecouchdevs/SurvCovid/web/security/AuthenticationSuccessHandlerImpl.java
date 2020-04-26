package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
/**
 * This class handles successful authentications of users. 
 * 
 * @author Volker Sedlmair
 *
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{
	
	@Autowired 
	UserService userService;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		
		// TODO: implement update of last login here
		
	}
	


}
