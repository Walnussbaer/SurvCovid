package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Role;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.UserState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.LoginRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.SignupRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.GameState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.JwtResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.MessageResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.RoleRepository;
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

    //TODO: implement a role service
    @Autowired
    RoleRepository roleRepository;

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

        Optional<User> user;

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

        user = userService.getUserByName(loginRequestUserName);

        // check whether user exists
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user does not exist");
        }

        // check whether account of user is still active
        if (user.get().getUserState().isActive() == false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account got suspended");
        }

        // update last login for authenticated user
        userService.updateLastLogin(user.get());

        // send 200 OK and user data back to client
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                user.get().getUserState().getLastLogin(),
                user.get().getUserState().isActive()
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // if the chosen username already exists
        if (userService.checkIfExistsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // if the chosen mail already exists
        if (userService.checkIfExistsByMail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),new UserState(),new GameState());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        // if no role is added, add normal player role
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_PLAYER)
                    .orElseThrow(() -> new RuntimeException("Error: Role 'PLAYER' is not found."));
            roles.add(userRole);
        } else {
            // else check for the given roles and add them
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'ADMIN' is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(RoleName.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'MODERATOR' is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_PLAYER)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'PLAYER' is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }





}
