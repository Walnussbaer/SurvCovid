package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Role;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.UserState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.LoginRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.SignupRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.JwtResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.MessageResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.RoleRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.RoleService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Authenticate incoming requests to the auth api.
     *
     * @param loginRequest - the content of the body of the incoming login request
     *
     * @return an HTTP response
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        User user;

        String loginRequestUserName;
        String loginRequestPassword;

        loginRequestUserName = loginRequest.getUsername();
        loginRequestPassword = loginRequest.getPassword();

        // authenticate the user using given username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestUserName, loginRequestPassword));
        // TODO: prevent that orginal java trace is sent out to client if login was not successful

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        SurvCovidUserDetails userDetails = (SurvCovidUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        try {
        	user = userService.getUserByName(loginRequestUserName);
        }
        catch (UserNotExistingException unee) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user does not exist");
        }
     
        // check whether account of user is still active
        if (user.getUserState().isActive() == false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account got suspended");
        }

        // update last login for authenticated user
        userService.updateLastLogin(user);

        // send 200 OK and user data back to client
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                user.getUserState().getLastLogin(),
                user.getUserState().isActive()
            )
        );
    }

    /**
     * Register a new user.
     *
     * @param signUpRequest - the content of the body of the incoming signup request
     *
     * @return - an HTTP response
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult bindingResult){
        
        // check whether the binding for the given SingnupRequest is valid
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("This is not a valid signup request!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),new UserState(),new GameState());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        // if no role is added, add normal player role
        if (strRoles == null) {
            Role userRole = roleService.findByName(RoleName.ROLE_PLAYER)
                    .orElseThrow(() -> new RuntimeException("Error: Role 'PLAYER' is not found."));
            roles.add(userRole);
        } else {
            // else check for the given roles and add them
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'ADMIN' is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleService.findByName(RoleName.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'MODERATOR' is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleService.findByName(RoleName.ROLE_PLAYER)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'PLAYER' is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        
        try {
        	// BindingResult ist not needed here anymore
        	userService.saveUser(user, null);
        } 
        catch (NoValidUserException nvue) {
        	// if the user object is not valid, it cannot be created and an error message is returned
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(nvue.getMessage());
        }
          
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
