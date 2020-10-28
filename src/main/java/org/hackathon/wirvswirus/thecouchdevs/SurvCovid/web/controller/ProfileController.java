package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.PasswordChangeRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.MessageResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.UserControllerResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * This class provides functionality for the users to work with their profile.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @PreAuthorize("hasRole('PLAYER')")
    @RequestMapping(value="/changePassword", method= RequestMethod.PUT)
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest){

        User user;
        Optional<User> optUser;

        // get information about the logged in user
        SurvCovidUserDetails userDetails = (SurvCovidUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.info("User with id " + userDetails.getId() + " wants to change the password!");

        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getNewPassword_2())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("The passwords do not match!"));
        }

        this.userService.changePassword(userDetails.getId(),passwordChangeRequest.getNewPassword());

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Password was changed successfully!"));
    }
}
