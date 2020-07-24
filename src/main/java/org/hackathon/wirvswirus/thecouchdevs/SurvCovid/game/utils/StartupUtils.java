package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.utils;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionRequirementType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.NoValidUserException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.GameState;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
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
    private GameEventDefinitionRequirementService gameEventDefinitionRequirementService;

    @Autowired
    private GameEventService gameEventService;

    @Autowired
    private GameEventChoiceService gameEventChoiceService;

    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopItemService shopItemService;

    @Autowired
    private GameManager gameManager;

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
                "Test event's short title",
                GameEventDefinitionType.GENERIC_EVENT);
        gameEventDefinitionService.saveGameEventDefinition(gameEventDefinition);


        // Define event choices
        List<GameEventDefinition> gameEventDefinitions = new ArrayList<GameEventDefinition>();
        gameEventDefinitions.add(gameEventDefinition);

        // Create possible choices for the event
        GameEventChoice gameEventChoice_1 = new GameEventChoice("Do something" ,gameEventDefinitions);
        GameEventChoice gameEventChoice_2 = new GameEventChoice("Do something else ", gameEventDefinitions);
        GameEventChoice gameEventChoice_3 = new GameEventChoice("Do anything", gameEventDefinitions);
        GameEventChoice gameEventChoice_4 = new GameEventChoice("Do anything else", gameEventDefinitions);

        gameEventChoiceService.saveGameEventChoice(gameEventChoice_1);
        gameEventChoiceService.saveGameEventChoice(gameEventChoice_2);
        gameEventChoiceService.saveGameEventChoice(gameEventChoice_3);
        gameEventChoiceService.saveGameEventChoice(gameEventChoice_4);

        // Associate possible choices with event definition
        List<GameEventChoice> gameEventChoices = new ArrayList<GameEventChoice>();
        gameEventChoices.add(gameEventChoice_1);
        gameEventChoices.add(gameEventChoice_2);
        gameEventChoices.add(gameEventChoice_3);
        gameEventChoices.add(gameEventChoice_4);

        gameEventDefinition.setGameEventChoices(gameEventChoices);
        gameEventDefinitionService.saveGameEventDefinition(gameEventDefinition);

        // Instantiate a specific event from the created definition
        GameEvent gameEvent = new GameEvent(LocalDateTime.now(), player, gameEventDefinition,false);
        gameEventService.saveGameEvent(gameEvent);

        System.out.println("Event test data got created!");
    }

    public void createSampleEventFlowData() throws UserNotExistingException {
        System.out.println("##### Create example data for event flows and requirements");

        /////////////// Fetch one test user
        User user = userService.getUserByName("admin");

        System.out.println("Using user '" + user.getUserName() + "' for demonstration.");

        /////////////// Create some event choices we associate with event definitions later on
        /////////////// For testing, we will provide those four choices for all event definitions
        System.out.println("Creating event choices to assign to events later on");

        List<GameEventChoice> choicesForEvent = new ArrayList<GameEventChoice>();

        GameEventChoice gameEventChoice1 = new GameEventChoice("Do the RIGHT thing!");
        gameEventChoice1 = gameEventChoiceService.saveGameEventChoice(gameEventChoice1);
        choicesForEvent.add(gameEventChoice1);

        GameEventChoice gameEventChoice2 = new GameEventChoice("Do  another RIGHT thing!");
        gameEventChoice2 = gameEventChoiceService.saveGameEventChoice(gameEventChoice2);
        choicesForEvent.add(gameEventChoice2);

        GameEventChoice gameEventChoice3 = new GameEventChoice("Do the WRONG thing!");
        gameEventChoice3 = gameEventChoiceService.saveGameEventChoice(gameEventChoice3);
        choicesForEvent.add(gameEventChoice3);

        GameEventChoice gameEventChoice4 = new GameEventChoice("Do another WRONG thing!");
        gameEventChoice4 = gameEventChoiceService.saveGameEventChoice(gameEventChoice4);
        choicesForEvent.add(gameEventChoice4);

        /////////////// Create definitions for events the user has finished in the past
        System.out.println("Create definitions for events the user has finished in the past (and set possible choices)");

        GameEventDefinition gameEventDefinitionPastEvent1 = new GameEventDefinition("Testing event requirements (PAST EVENT 01).",
                "Requirement Test - Past Event 01",
                GameEventDefinitionType.GENERIC_EVENT);
        gameEventDefinitionPastEvent1.setGameEventChoices(choicesForEvent);
        gameEventDefinitionPastEvent1 = gameEventDefinitionService.saveGameEventDefinition(gameEventDefinitionPastEvent1);

        GameEventDefinition gameEventDefinitionPastEvent2 = new GameEventDefinition("Testing event requirements (PAST EVENT 02).",
                "Requirement Test - Past Event 02",
                GameEventDefinitionType.GENERIC_EVENT);
        gameEventDefinitionPastEvent2.setGameEventChoices(choicesForEvent);
        gameEventDefinitionPastEvent2 = gameEventDefinitionService.saveGameEventDefinition(gameEventDefinitionPastEvent2);

        /////////////// Tell the database, the user has already finished those events
        System.out.println("Tell the database, the user has already finished those events (and made the right choices)");

        GameEvent gameEvent1 = new GameEvent(null, user, gameEventDefinitionPastEvent1, true);
        gameEvent1.setChosenChoice(gameEventChoice1);
        gameEvent1 = gameEventService.saveGameEvent(gameEvent1);

        GameEvent gameEvent2 = new GameEvent(null, user, gameEventDefinitionPastEvent2, true);
        gameEvent2.setChosenChoice(gameEventChoice2);
        gameEvent2 = gameEventService.saveGameEvent(gameEvent2);

        /////////////// Define future events for the user
        System.out.println("Define possible future events for players (including our example user)");

        GameEventDefinition gameEventDefinitionFutureEvent1 = new GameEventDefinition("Testing event requirements (FUTURE EVENT 01).",
                "Requirement Test - Future Event 01",
                GameEventDefinitionType.GENERIC_EVENT);
        gameEventDefinitionFutureEvent1.setGameEventChoices(choicesForEvent);
        gameEventDefinitionFutureEvent1 = gameEventDefinitionService.saveGameEventDefinition(gameEventDefinitionFutureEvent1);

        GameEventDefinition gameEventDefinitionFutureEvent2 = new GameEventDefinition("Testing event requirements (FUTURE EVENT 02).",
                "Requirement Test - Future Event 02",
                GameEventDefinitionType.GENERIC_EVENT);
        gameEventDefinitionFutureEvent2.setGameEventChoices(choicesForEvent);
        gameEventDefinitionFutureEvent2 = gameEventDefinitionService.saveGameEventDefinition(gameEventDefinitionFutureEvent2);

        /////////////// Define requirements for future events
        System.out.println("Define requirements for future events");

        GameEventRequirement gameEventRequirementFutureEvent1 = new GameEventRequirement(gameEventDefinitionFutureEvent1,
                gameEventDefinitionPastEvent1,
                gameEventChoice1,
                GameEventDefinitionRequirementType.HAS_HAPPENED);
        gameEventRequirementFutureEvent1 = gameEventDefinitionRequirementService.saveGameEventRequirement(gameEventRequirementFutureEvent1);

        GameEventRequirement gameEventRequirementFutureEvent2 = new GameEventRequirement(gameEventDefinitionFutureEvent2,
                gameEventDefinitionPastEvent2,
                gameEventChoice3,
                GameEventDefinitionRequirementType.HAS_NOT_HAPPENED);
        gameEventRequirementFutureEvent2 = gameEventDefinitionRequirementService.saveGameEventRequirement(gameEventRequirementFutureEvent2);

        System.out.println("Define requirements for future events");

        // DONE
        System.out.println("\n");
    }

    public void createSampleEventStoryData() {

    }

    public void createSampleInventoryData() throws UserNotExistingException {
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
    }

    public void createSampleShopData() throws UserNotExistingException {
        ////////// Testing shops

        System.out.println("Fetching one test user");
        User user = userService.getUserByName("Philipp");

        // Create a shop
        System.out.println("Testing Shop creation");
        Shop userShop = new Shop(user);
        userShop = shopService.saveShop(userShop);

        // Get two item types to use for shop items
        ItemType itemType1 = itemTypeService.getItemByTypeId(1);
        ItemType itemType2 = itemTypeService.getItemByTypeId(2);

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
    }

}
