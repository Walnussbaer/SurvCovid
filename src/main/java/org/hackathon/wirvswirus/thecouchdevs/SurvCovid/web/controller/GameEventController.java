package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;
import java.util.Optional;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoEventsAvailableException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.EventRequestResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.SurvCovidBaseResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.GameEventManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventChoiceService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/event/")
public class GameEventController {
    
    @Autowired
    GameManager gameManager;

	@Autowired
    GameEventManager gameEventManager;
    
    @Autowired 
    UserService userService;
    
    @Autowired
    GameEventService gameEventService;
    
    @Autowired 
    GameEventChoiceService gameEventChoiceService;
    
	@GetMapping("/next")
	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public EventRequestResponse getOrCreate(@ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails,
											@RequestParam(name="user_id", required=true)long userId,
											HttpServletResponse response) {
		// HTTP status codes
		//   200 - Open event returned
		//   201 - New event created and returned
		//   401 - Invalid user for event request or user has no permissions to access this user (not self and no admin)
		//   500 - Could not find a possible next event for the user and there wasn't an open one

		// Check if the user is an admin
		if(!userDetails.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
			System.out.println("[DEBUG] User is not an admin");
			// If the user is not an admin, check if he tries to access his own events
			if (userDetails.getId() != userId) {
				System.out.println("[DEBUG] User {id: "+userDetails.getId()+", "
								 + "Name: " + userDetails.getUsername() + ", "
						         + "Authorities: "+ userDetails.getAuthorities()
								 + "} is not an admin and tries to fetch a job for another user (with id: "+userId+")!");
				// The user tries to access another user's events => we do not allow this
				// Set HTTP status "401 Unauthorized"
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
		}

		gameEventManager = gameManager.getGameEventManager();
	    
	    if (gameEventManager == null) {
			return null;
			// TODO: implement proper error handling
		}
	    
	    User player;
	    
	    try {
	    	player = userService.getUserById(userId);
	    } 
	    catch (UserNotExistingException unee) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	return null;
	    	//TODO: implement proper error handling
	    }

		 EventRequestResponse eventRequestResponse = null;

		 GameEvent nextGameEvent = gameEventManager.getOpenGameEvent(player.get());

	    if (nextGameEvent == null) {
			try {
				nextGameEvent = gameEventManager.initiateNewGameEvent(player.get());
				// If there was no exception, a next event was created => HTTP code 201
				response.setStatus(HttpServletResponse.SC_CREATED);
				return new EventRequestResponse("Created new event for user.", nextGameEvent);
			}
			catch(NoEventsAvailableException ex) {
				// Could not initiate a new game event, so we return none... => HTTP code 500
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return new EventRequestResponse("Could not instantiate a new event for this user. The player did not meet the requirements for any event definition.", null);
			}
	    }

		response.setStatus(HttpServletResponse.SC_OK);
		return new EventRequestResponse("There already is an open event for this user.", nextGameEvent);
	}
	
	@PutMapping("/next")
	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public SurvCovidBaseResponse respond(@ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails,
										 @RequestParam(name="user_id", required = true) long userId,
										 @RequestParam(name="game_event_id", required=true) long gameEventId,
										 @RequestParam(name="choice_id", required = true) long choiceId,
										 HttpServletResponse response) {

		// HTTP status codes
		//   200 - Choice registered
		//   401 - Invalid user for event request or user has no permissions to access this user (not self and no admin)
		//   403 - Invalid event (not open event for user) or invalid choice

		// Check if the user is an admin
		if(!userDetails.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
			System.out.println("[DEBUG] User is not an admin");
			// If the user is not an admin, check if he try to access his own events
			if (userDetails.getId() != userId) {
				System.out.println("[DEBUG] User is not an admin and tries to fetch a job for another user!");
				// The user tries to access another user's events => we do not allow this
				// Set HTTP status "401 Unauthorized"
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
		}
	    
	    GameEvent nextGameEvent = null;
	    GameEventChoice gameEventChoice = null;
	    gameEventManager = gameManager.getGameEventManager();
    
	    User player;
	    
	    try {
	    	player = userService.getUserById(userId);
	    } 
	    catch (UserNotExistingException unee) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	return null;
	    	//TODO: implement proper error handling
	    }
	    
	    nextGameEvent = gameEventService.getGameEventById(gameEventId);
	    gameEventChoice = gameEventChoiceService.getGameEventChoiceById(choiceId);

	    // Ensure that the user only responds to his currently open event
		GameEvent openEvent = gameEventManager.getOpenGameEvent(player.get());
		if(openEvent.getId() != gameEventId) {
			// The user tried to respond to another event
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return new SurvCovidBaseResponse("Could not register choice since the provided event does not match the event currently open for this user.");
		}

	    // Ensure that the user's choice was possible for the event
	    if(!gameEventManager.isPossibleChoice(gameEventId, choiceId)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return new SurvCovidBaseResponse("Could not register choice since the provided choice is not valid for the provided event.");
		}

	    nextGameEvent.setChosenChoice(gameEventChoice);
	    nextGameEvent.setDone(true);
	    gameEventService.saveGameEvent(nextGameEvent);

		  response.setStatus(HttpServletResponse.SC_OK);
	    return new SurvCovidBaseResponse("Your choice was registered, the event is over!");

	}

	////////////////////////////////////////////////////////////////////////
	/////////////////////////// TESTING ONLY ///////////////////////////////
	////////////////////////////////////////////////////////////////////////
//	@GetMapping("/possiblenext")
//	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	// Test: http://localhost:8080/api/v1/event/possiblenext?user_id=12
//	public List<GameEventDefinition> getPossibleNextEventsForUser(@RequestParam(name="user_id", required=true)long userId) {
//		return gameEventManager.getPossibleNextEventDefinitionsForUser(userId);
//	}
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////

}
