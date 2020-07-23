package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;


import java.time.LocalDateTime;
import java.util.List;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoEventsAvailableException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionRequirementService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameEventManager {
	
	@Autowired
	UserService userService;
	
	@Autowired
	GameEventService gameEventService;
	
	@Autowired
	GameEventDefinitionService gameEventDefinitionService;

	@Autowired
	GameEventDefinitionRequirementService gameEventDefinitionRequirementService;
	
	public GameEvent serveGameEvent(User player){

		if (player == null) {
			throw new NullPointerException("player cannot be null");
		}
		
	    GameEvent gameEvent = null;
	     
	    gameEvent = gameEventService.findNextGameEventForUser(player);




		//return gameEventDefinitionService.getPossibleNextEventsForUser(player.getUserId());

		return gameEvent;
	}
	
	public GameEvent initiateNewGameEvent(User player) throws NoEventsAvailableException {

		if (player == null)
			throw new NullPointerException("player cannot be null");

		LocalDateTime currentTime = LocalDateTime.now();

		List<GameEventDefinition> gameEventDefinitions = gameEventDefinitionService.getPossibleNextEventsForUser(player.getUserId());

		if(gameEventDefinitions.isEmpty())
			throw new NoEventsAvailableException("No events available for this player.");

		// Select next event from list of possible next events
		// TODO: Check how we could order those and which one we select
		GameEventDefinition nextGameEventDefinition = gameEventDefinitions.get(0);

		// Use definition to instantiate an event for this specific user
		GameEvent gameEvent = new GameEvent(currentTime, player, nextGameEventDefinition, false);
	    gameEventService.saveGameEvent(gameEvent);

		return gameEvent;

//	    long gameEventDefinitionId = 1;
//
//	    GameEvent gameEvent = null;
//
//	    LocalDateTime currentTime = LocalDateTime.now();
//
//	    // Old dummy implementation
//	    //GameEventDefinition gameEventDefinition = gameEventDefinitionService.getGameEventDefinitionById(gameEventDefinitionId);
//
//		// TODO: Implement method properly (currently unfiltered)
//		List<GameEventDefinition> unfinishedEvents = gameEventDefinitionService
//				.getUnfinishedGameEventDefinitionsForUser(player);
//
//		// TODO: Implement method properly (currently unfiltered)
//		List<GameEventDefinition> finishedEvents = gameEventDefinitionService
//				.getFinishedGameEventDefinitionsForUser(player);
//
//		List<GameEventDefinition> possibleEvents = new ArrayList<GameEventDefinition>();
//
//		// Iterate over unfinished events
//		for(GameEventDefinition gameEventDefinition: unfinishedEvents) {
//			List<GameEventRequirement> requirements = gameEventDefinitionRequirementService
//					.getRequirementsForGameEventDefinition(gameEventDefinition);
//
//			boolean possible = true;
//
//			// Iterate over requirements for this unfinished event
//			for(GameEventRequirement gameEventRequirement: requirements) {
//				// Depending on the type of requirement, we deal with it differently
//
//				// Type: A specific event has happened before
//				if(gameEventRequirement.getType() == GameEventDefinitionRequirementType.HAS_HAPPENED) {
//
//					// Did the user already finish this event?
//					if (!finishedEvents.contains(gameEventRequirement.getRequiredGameEventDefinition())) {
//						// User did not finish this event => the new event cannot happen
//						possible = false;
//						// No further tests needed, we go to the next candidate for the next event
//						break;
//					}
//
//					// User already finished this event => good
//
//					// Is a specific choice in the event required?
//					if (gameEventRequirement.getGameEventChoice() == null) {
//						// No specific choice is required => requirement fulfilled
//						// Check the next requirement
//						continue;
//					}
//
//					// A specific choice must have been selected by the user
//					// TODO: Check which choice the user selected
//					GameEventChoice userChoice = null;
//
//					if (gameEventRequirement.getGameEventChoice() != userChoice) {
//						// User did not select the correct choice => the new event cannot happen
//						possible = false;
//						// No further tests needed, we go to the next candidate for the next event
//						break;
//					}
//
//					// => requirement fulfilled
//					// Check the next requirement
//					continue;
//				}
//				else if(gameEventRequirement.getType() == GameEventDefinitionRequirementType.HAS_NOT_HAPPENED) {
//
//				}
//				else {
//					// TODO: Whatever...
//				}
//			}
//
//			if(possible) {
//				// TODO: Either add to list of directly "break" an take this event if the input list "unfinishedEvent" was ordered by relevance/priority of the events
//				possibleEvents.add(gameEventDefinition);
//			}
//		}
//
//
//
//		// TODO: We need to have an order of events implemented, so we can take the first one which matches the requirements
//		GameEventDefinition gameEventDefinition = possibleEvents.get(0);
//
//	    boolean isDone = false;
//
//	    gameEvent = new GameEvent(currentTime, player, gameEventDefinition, isDone);
//	    gameEventService.saveGameEvent(gameEvent);
//
//	    return gameEvent;
	}

	public List<GameEventDefinition> getPossibleNextEventDefinitionsForUser(Long userId) {

		//////////////////////////////////////////////////////////////////////////
		////////////////////////////    DEBUGGING   //////////////////////////////
		//////////////////////////////////////////////////////////////////////////

		return gameEventDefinitionService.getPossibleNextEventsForUser(userId);

		//return null;

		//////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////
	}

	public void sayHello() {
	    System.out.println("Hello from GameEventManager!");
	}
	
	

}
