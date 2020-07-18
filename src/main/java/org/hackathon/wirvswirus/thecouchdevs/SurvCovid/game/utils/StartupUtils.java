package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.utils;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.GameState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class StartupUtils {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private GameEventDefinitionService gameEventDefinitionService;

    @Autowired
    private GameEventService gameEventService;

    @Autowired
    private GameEventChoiceService gameEventChoiceService;

    /**
     * Create sample users for SurvCovid to test functionality. This method only creates normal players.
     * @throws NoValidUserException 
     */
    public void createSamplePlayerUsers() throws NoValidUserException  {

        System.out.println("Creating some player test users ...");

        String[] nameArray = {"John","Peter","Max","Volker","Paul","Sharmin","Vroni","Philipp","Gino","Henning"};
        
        
        for (String name:nameArray) {
            

            User user = new User(name);
            Set<Role> roles = new HashSet<>();

            roles.add(roleService.findByName(RoleName.ROLE_PLAYER)
                    .orElseThrow(() -> new RuntimeException("Error: Role player was not found")));

            user.setPassword("12345");
            user.setRoles(roles);
            user.setEmail(user.getUserName() + "@example.de");
            user.setUserState(new UserState(true));
            user.setGameState(new GameState());

            userService.saveUser(user);
        }
       

        System.out.println("Player test users got created!");
    }

    /**
     * Create an admin superuser which can do everything.
     * @throws NoValidUserException 
     */
    public void createSampleAdminUser() throws NoValidUserException {

        System.out.println("Creating a admin test user ...");

        Set<Role> adminRoles = new HashSet<>();
        User admin = new User("admin");
        Role role = roleService.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role admin was not found"));

        admin.setPassword("admin");
        admin.setEmail("admin@example.com");
        admin.setUserState(new UserState(true));
        admin.setGameState((new GameState()));

        adminRoles.add(role);
        admin.setRoles(adminRoles);
        userService.saveUser(admin);

        System.out.println("Admin test user got created!");

    }

    /**
     * Create roles for SurvCovid to test funtionality.
     */
    public void createRoles() {

        System.out.println("Creating test roles ...");

        Role role_player = new Role(RoleName.ROLE_PLAYER);
        Role role_admin = new Role(RoleName.ROLE_ADMIN);
        Role role_mod = new Role(RoleName.ROLE_MODERATOR);

        roleService.saveRole(role_player);
        roleService.saveRole(role_admin);
        roleService.saveRole(role_mod);

        System.out.println("Test roles got created!");

    }

    public void createSampleEventData() {
    	
    	User player;

        System.out.println("Creating game event test data ...");
        
        try {
        	player = userService.getUserByName("John");
        }
        catch (UserNotExistingException unee) {
        	throw new RuntimeException("No user with name John is existing!");
        }
        
        GameEventDefinition gameEventDefinition = new GameEventDefinition(
                "This is a test event. What do you want to do?",
                "test",
                GameEventDefinitionType.GENERIC_EVENT);

        gameEventDefinitionService.saveGameEventDefinition(gameEventDefinition);

        GameEvent gameEvent = new GameEvent(LocalDateTime.now(), player, gameEventDefinition,false);
        gameEventService.saveGameEvent(gameEvent);

        List<GameEventDefinition> gameEventDefinitions = new ArrayList<GameEventDefinition>();

        gameEventDefinitions.add(gameEventDefinition);

        GameEventChoice gameEventChoice_1 = new GameEventChoice("Do someting",gameEventDefinitions);
        GameEventChoice gameEventChoice_2 = new GameEventChoice("Do something else ",gameEventDefinitions);
        GameEventChoice gameEventChoice_3 = new GameEventChoice("Do anything",gameEventDefinitions);
        GameEventChoice gameEventChoice_4 = new GameEventChoice("Do anything else",gameEventDefinitions);

        gameEventChoiceService.saveGameEventChoice(gameEventChoice_1);
        gameEventChoiceService.saveGameEventChoice(gameEventChoice_2);
        gameEventChoiceService.saveGameEventChoice(gameEventChoice_3);
        gameEventChoiceService.saveGameEventChoice(gameEventChoice_4);

        List<GameEventChoice> gamneEventChoices = new ArrayList<GameEventChoice>();

        gamneEventChoices.add(gameEventChoice_1);
        gamneEventChoices.add(gameEventChoice_2);
        gamneEventChoices.add(gameEventChoice_3);
        gamneEventChoices.add(gameEventChoice_4);

        gameEventDefinition.setGameEventChoices(gamneEventChoices);

        gameEventDefinitionService.saveGameEventDefinition(gameEventDefinition);

        System.out.println("Event test data got created!");

    }

}
