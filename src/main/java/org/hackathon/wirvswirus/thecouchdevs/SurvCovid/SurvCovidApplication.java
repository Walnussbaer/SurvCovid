package org.hackathon.wirvswirus.thecouchdevs.SurvCovid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Activity;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.InventoryItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.ActivityService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventChoiceService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.InventoryService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.ItemTypeService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SurvCovidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurvCovidApplication.class, args);
	}
	
	/**
	 * Creates test data for the user table. 
	 * 
	 * @param userService
	 * @return
	 */
	@Bean CommandLineRunner createUserTestData(UserService userService,
			ItemTypeService itemTypeService,
			InventoryService inventoryService,
			ActivityService activityService) {
		
		return args -> {
		    
			System.out.println("Creating user test data");
			
			Stream.of("John","Peter","Max","Volker","Paul","Sharmin","Vroni","Philipp","Gino","Henning").forEach(name -> {
				User user = new User(name);
				userService.saveUser(user);
			});
			System.out.println("Created test users");
			userService.getAllUsers().forEach(user -> {
				System.out.println(user.getUserName());
				//System.out.println(user.getUserId());
			});
			
			////////// Testing inventory and items
            // Adding user
            System.out.println("Fetching one test user");
            User user = userService.getUserByName("Philipp");

            // Adding item type
            System.out.println("Creating item types");
            ItemType itemType1 = new ItemType(1, "Zeitung");
            ItemType itemType2 = new ItemType(2, "Buch");
            itemTypeService.addItem(itemType1);
            itemTypeService.addItem(itemType2);

            // List currently existing items
            System.out.println("List all item types in the database:");
            List<ItemType> testItemTypes = itemTypeService.getAllItemTypes();
            for(ItemType i: testItemTypes)
                System.out.println("  - (" + i.getItemTypeId() + ") " + i.getItemTypeName());

            // Adding items of item types to the user's inventory
            System.out.println("Adding items to the user's inventory");
            InventoryItem inventoryItem1 = new InventoryItem(user, itemType1, 1);
            inventoryService.saveInventoryItem(inventoryItem1);
            InventoryItem inventoryItem2 = new InventoryItem(user, itemType2, 5);
            inventoryService.saveInventoryItem(inventoryItem2);

            // List items in user's inventory
            System.out.println("List items of user " + user.getUserName() + "s inventory");
            List<InventoryItem> userItems = inventoryService.getInventory("Philipp");
            for(InventoryItem i: userItems)
                System.out.println("User " + i .getUserName() + " has " + i.getItemCount() + " of " + i.getItemTypeName() + ".");

            // Adding some more of one item type to the user's inventory
            System.out.println("Adding some more of one item type to the user's inventory");
            inventoryService.addItemCount(user, itemType1, 3);

            // List items in user's inventory
            System.out.println("List items of user " + user.getUserName() + "s inventory");
            userItems = inventoryService.getInventory("Philipp");
            for(InventoryItem i: userItems)
                System.out.println("User " + i .getUserName() + " has " + i.getItemCount() + " of " + i.getItemTypeName() + ".");
            
            // Add Activities
            System.out.println("Adding some Activities");
            Activity activity1 = new Activity("Workout","One Hour Sport", 2);
            activityService.saveActivity(activity1);
            Activity activity2 = new Activity("Learn Suaheli","Learn a Module in Online Suaheli Couse", 3);
            activityService.saveActivity(activity2);
			
			System.out.println("Finished creating user test data");
		};
	}
	
	/**
	 * Creates test data for the game event table. 
	 * 
	 * @param gameEventService
	 * @return 
	 */
	@Bean CommandLineRunner createEventTestData(GameEventService gameEventService, GameEventDefinitionService gameEventDefinitionService, UserService userService, GameEventChoiceService gameEventChoiceService) {
		
		return args -> {
			
			System.out.println("Creating game event test data");
			
			User player = new User("Peter");
			userService.saveUser(player);
			

			
			GameEventDefinition gameEventDefinition = new GameEventDefinition("This is a test event. What do you want to do?", "test",GameEventDefinitionType.GENERIC_EVENT);
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
			

			System.out.println("Finished creating game event test data");
			
		};
		
	}
}
