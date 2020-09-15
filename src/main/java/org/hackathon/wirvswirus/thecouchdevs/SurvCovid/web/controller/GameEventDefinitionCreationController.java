package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.GameEventDefinitionCreationRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionCreationService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/gameEventDefinitionCreationController")
public class GameEventDefinitionCreationController {

    @Autowired
    GameEventDefinitionCreationService gameEventDefinitionCreationService;


    @PostMapping
    @PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity create(@RequestBody final GameEventDefinitionCreationRequest gameEventDefinitionCreationRequest,
                                 @ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails) {
        //		// HTTP status codes
//		//   200 - Open event returned
//		//   401 - Invalid user for event request or user has no permissions to access this user (not self and no admin)
//		//   500 - Could not find a possible next event for the user and there wasn't an open one
//
        // TODO: Add proper permission check by role
//		// Check if the user is an admin
//		if(!userDetails.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
//			System.out.println("[DEBUG] User is not an admin");
//			// If the user is not an admin, check if he tries to access his own events
//			if (userDetails.getId() != userId) {
//				System.out.println("[DEBUG] User {id: "+userDetails.getId()+", "
//								 + "Name: " + userDetails.getUsername() + ", "
//						         + "Authorities: "+ userDetails.getAuthorities()
//								 + "} is not an admin and tries to fetch a job for another user (with id: "+userId+")!");
//				// The user tries to access another user's events => we do not allow this
//				// Set HTTP status "401 Unauthorized"
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//            }
//        }

        gameEventDefinitionCreationService.createGameEventDefinitionWithRequirements(gameEventDefinitionCreationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

//    @GetMapping
//    public List<ActivityDefinition> list(){
//
//        return activityDefinitionService.getAllActivitiesDefinitions();
//
//    }
//
//    @GetMapping
//    @RequestMapping("{activityDefinitionId}")
//    public ActivityDefinition get(@PathVariable long activityDefinitionId){
//
//        Optional<ActivityDefinition> activityDefinition = activityDefinitionService.getActivityDefinitionById(activityDefinitionId);
//
//        if (activityDefinition.isEmpty()) {
//            return null;
//        }
//
//        return activityDefinition.get();
//    }
//
//    @PostMapping
//    public ActivityDefinition create(@RequestBody final ActivityDefinition activityDefinition) {
//
//        ActivityDefinition createdActivityDefinition;
//
//        createdActivityDefinition = activityDefinitionService.saveActivityDefinition(activityDefinition);
//
//        return createdActivityDefinition;
//    }
//
//    @RequestMapping(value = "{activityDefinitionId}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable long activityDefinitionId) {
//
//        activityDefinitionService.deleteActivityDefinitionById(activityDefinitionId);
//    }
//
//    @PutMapping
//    public ActivityDefinition update(@RequestBody final ActivityDefinition activityDefinition) {
//
//        ActivityDefinition updatedActivityDefinition;
//
//        updatedActivityDefinition = activityDefinitionService.saveActivityDefinition(activityDefinition);
//
//        return updatedActivityDefinition;
//
//    }
}
