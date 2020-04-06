package org.hackathon.wirvswirus.thecouchdevs.SurvCovid;

import java.util.List;
import java.util.stream.Stream;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Inventory;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Item;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.InventoryService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.ItemService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SurvCovidApplication {

	//private static ApplicationContext context;

	public static void main(String[] args) {
		//context = new AnnotationConfigApplicationContext(AppConfig.class);
		SpringApplication.run(SurvCovidApplication.class, args);
	}

	//public static ApplicationContext getApplicationContext() {
	//	return context;
	//}
	
	@Bean CommandLineRunner setup(UserService userService, InventoryService inventoryService, ItemService itemService) {
		return args -> {
			Stream.of("John","Peter","Max","Volker","Paul","Sharmin","Vroni","Philipp","Gino","Henning").forEach(name -> {
				User user = new User(name);
				userService.saveUser(user);
			});
			System.out.println("Created test users");
			userService.getAllUsers().forEach(user -> {
				System.out.println(user.getUserName());
			});

			// Testing inventory and items
			System.out.println("Fetching one test user");
			User testUser = userService.getUserByName("Philipp");

			System.out.println("Adding items to the user's inventory");
			Item testItem1 = new Item(1, "Some Stuff");
			testUser.getInventory().addItem(testItem1);
			Item testItem2 = new Item(2, "Another Thingy");
			testUser.getInventory().addItem(testItem2);

			System.out.println("Saving the user (and his inventory)");
			testUser = userService.saveUser(testUser);

			// List currently existing items
			System.out.println("List all items in the database:");
			List<Item> testItems = itemService.getAllItems();
			for(Item i: testItems) {
				System.out.println("  - (" + i.getItemId() + ") " + i.getItemName());
			}

			// Trying to retrieve inventory and items
			System.out.println("Fetching the inventory of the test user and its items back.");
			User testUser2 = userService.getUserByName("Philipp");
			Inventory testInventory2 = testUser2.getInventory();

			List<Item> itemsOfTestUser = testInventory2.getItems();
			for(Item i: itemsOfTestUser) {
				System.out.println("  - (" + i.getItemId() + ") " + i.getItemName());
			}

		};
	}
}
