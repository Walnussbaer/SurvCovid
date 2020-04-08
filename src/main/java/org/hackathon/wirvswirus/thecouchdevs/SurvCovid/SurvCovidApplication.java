package org.hackathon.wirvswirus.thecouchdevs.SurvCovid;

import java.util.List;
import java.util.stream.Stream;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.InventoryItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
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

	@Bean CommandLineRunner setup(UserService userService, InventoryService inventoryService, ItemTypeService itemTypeService) {
		return args -> {
			Stream.of("John","Peter","Max","Volker","Paul","Sharmin","Vroni","Philipp","Gino","Henning").forEach(name -> {
				User user = new User(name);
				userService.saveUser(user);
			});
			System.out.println("Created test users");
			userService.getAllUsers().forEach(user -> {
				System.out.println(user.getUserName());
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
		};
	}
}
