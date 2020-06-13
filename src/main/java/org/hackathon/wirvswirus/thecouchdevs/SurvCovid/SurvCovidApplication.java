package org.hackathon.wirvswirus.thecouchdevs.SurvCovid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ActivityDefinitionCondition.ActivityDefinitionConditionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.GameState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.RoleRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SurvCovidApplication {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SurvCovidApplication.class, args);
	}


	@Bean
	public Docket api() {
		// Add default headers to all endpoints
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		// First header
		aParameterBuilder.name("Authorization") // Name of Parameter
				.modelRef(new ModelRef("string"))
				.parameterType("header")               // Type of Parameter: "header"
				.defaultValue("Bearer abcdefabcdefabcdef")
				.required(true)
				.build();
		java.util.List<Parameter> aParameters = new ArrayList<>();
		aParameters.add(aParameterBuilder.build());             // Add Parameters to all endpoints
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build().pathMapping("")
				.globalOperationParameters(aParameters);
	}

	/**
	 * Creates test users and some other test data.
	 *
	 */
	@Order(Ordered.LOWEST_PRECEDENCE)
	@Bean CommandLineRunner createUserTestData(UserService userService,
											   ItemTypeService itemTypeService,
											   InventoryService inventoryService,
											   ShopService shopService,
											   ShopItemService shopItemService,
											   GameManager gameManager,
											   ActivityDefinitionService activityDefinitionService) {
		return args -> {

			/* create roles for testing */
			System.out.println("Creating Roles ...");

			Role role_player = new Role(RoleName.ROLE_PLAYER);
			Role role_admin = new Role(RoleName.ROLE_ADMIN);
			Role role_mod = new Role(RoleName.ROLE_MODERATOR);

			roleRepository.save(role_player);
			roleRepository.save(role_admin);
			roleRepository.save(role_mod);

			System.out.println("Roles got created!");

			/* set up a couple of different users */
			System.out.println("Creating user test data ...");

			Stream.of("John","Peter","Max","Volker","Paul","Sharmin","Vroni","Philipp","Gino","Henning").forEach(name -> {
				User user = new User(name);
				user.setPassword(encoder.encode("12345"));
				user.setEmail(user.getUserName() + "@example.de");
				user.setUserState(new UserState(true));
				user.setGameState(new GameState());

				userService.saveUser(user);
			});

			/* set up an admin user */
			Set<Role> adminRoles = new HashSet<>();
			User admin = new User("admin");
			admin.setPassword(encoder.encode("admin"));
			admin.setEmail("admin@example.com");
			admin.setUserState(new UserState(true));
			admin.setGameState((new GameState()));
			Role role = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role admin was not found"));
			adminRoles.add(role);
			admin.setRoles(adminRoles);
			userService.saveUser(admin);

			System.out.println("Users got created!");
			/*
			userService.getAllUsers().forEach(user -> {
				System.out.println(user.getUserId() + ") " + user.getUserName());
			});
			*/
			
			/* Testing inventory and items */
            // Adding user
            System.out.println("Fetching one test user");
            User user = userService.getUserByName("Philipp");

            // Adding item type
            System.out.println("Creating item types");
            ItemType itemType1 = new ItemType(1, "newspaper", "Zeitung");
            ItemType itemType2 = new ItemType(2, "book", "Buch");
            itemTypeService.addItem(itemType1);
            itemTypeService.addItem(itemType2);

            // List currently existing items
            System.out.println("List all item types in the database:");
            List<ItemType> testItemTypes = itemTypeService.getAllItemTypes();
            for(ItemType i: testItemTypes)
                System.out.println("  - (" + i.getItemTypeId() + " / " + i.getItemTypeName() + ") => '" + i.getItemTypeDisplayName() + "'");

            // Adding items of item types to the user's inventory
            System.out.println("Adding items to the user's inventory");
            InventoryItem inventoryItem1 = new InventoryItem(user, itemType1, 1);
            inventoryService.saveInventoryItem(inventoryItem1);
            InventoryItem inventoryItem2 = new InventoryItem(user, itemType2, 5);
            inventoryService.saveInventoryItem(inventoryItem2);

            // List items in user's inventory
            System.out.println("List items of user " + user.getUserName() + "s inventory");
            List<InventoryItem> userItems = inventoryService.getInventory(user.getUserId());
            for(InventoryItem i: userItems)
                System.out.println("User " + i .getUserName() + " has " + i.getItemCount() + " of " + i.getItemTypeDisplayName() + ".");

            // Adding some more of one item type to the user's inventory
            System.out.println("Adding some more of one item type to the user's inventory");
            inventoryService.addItemCount(user, itemType1, 3);

            // List items in user's inventory
            System.out.println("List items of user " + user.getUserName() + "s inventory");
            userItems = inventoryService.getInventory(user.getUserId());
            for(InventoryItem i: userItems)
                System.out.println("User " + i .getUserName() + " has " + i.getItemCount() + " of " + i.getItemTypeDisplayName() + ".");

			////////// Testing shops
			// Create a shop
			System.out.println("Testing Shop creation");
			Shop userShop = new Shop(user);
			userShop = shopService.saveShop(userShop);

			// Adding items to Shop
			ShopItem shopItem1 = new ShopItem(userShop, itemType1, 5, 4.75);
			shopItemService.saveShopItem(shopItem1);
			ShopItem shopItem2 = new ShopItem(userShop, itemType2, 27, 19.99);
			shopItemService.saveShopItem(shopItem2);

			ShopManager shopManager = gameManager.getShopManager();
			List<ShopItem> sortiment = shopManager.getOrCreateShopStock(user);

			System.out.println("Shop of player " + user.getUserName() + " currently offers the following items:");
			for(ShopItem ssi: sortiment)
				System.out.println("- ItemType '" + ssi.getItemType().getItemTypeDisplayName() + "' / ItemCount: " + ssi.getItemCount() + " / ItemPrice: " + ssi.getItemPrice() + ".");

			// Add Activities
			
			//ActivityDefinitionCondition activityDefinitionCondition1 = new ActivityDefinitionCondition(ActivityDefinitionConditionType.INVENTORY_ITEM, 1,3);
			
			
			System.out.println("Adding some Activities");
			ActivityDefinition activityDefinition1 = new ActivityDefinition("Workout","One Hour Sport", 2, null, null);
			activityDefinitionService.saveActivityDefinition(activityDefinition1);
			ActivityDefinition activityDefinition2 = new ActivityDefinition("Learn Suaheli","Learn a Module in Online Suaheli Couse", 3 ,null, null);
			activityDefinitionService.saveActivityDefinition(activityDefinition2);
		};
	}
	
	/**
	 * Creates test data for the game event table. 
	 * 
	 * @param gameEventService
	 * @return 
	 */
	@Order(3)
	@Bean CommandLineRunner createEventTestData(GameEventService gameEventService, GameEventDefinitionService gameEventDefinitionService, UserService userService, GameEventChoiceService gameEventChoiceService) {
		
		return args -> {
			
			System.out.println("Creating game event test data");
			
			User player = new User("NewPeter");
			player.setPassword(encoder.encode("12345"));
			player.setEmail(player.getUserName() + "@test.de");
			player.setGameState(new GameState());
			player.setUserState(new UserState(true));
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

			System.out.println("Finished creating game event test data for user " + player.getUserId());
			
		};
	}

}
