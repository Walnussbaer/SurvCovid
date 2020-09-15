package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto.GameEventDefinitionWithRequirementsAndChoicesDTO;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventDefinitions/")
public class GameEventDefinitionController {

    @Autowired
    GameEventDefinitionService gameEventDefinitionService;

	@GetMapping("/list")
	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<GameEventDefinition>> getAll(@ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails) {
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

		return ResponseEntity.status(HttpStatus.OK).body(gameEventDefinitionService.getAll());
	}

	@GetMapping("/listWithRequirements")
	@PreAuthorize("hasRole('PLAYER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<GameEventDefinitionWithRequirementsAndChoicesDTO>> getAllWithRequirements(@ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails) {

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

		return ResponseEntity.status(HttpStatus.OK).body(gameEventDefinitionService.getAllWithRequirementsDTOs());
	}

}
