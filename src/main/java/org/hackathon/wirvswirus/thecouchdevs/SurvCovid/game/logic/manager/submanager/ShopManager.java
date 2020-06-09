package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;


import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.DatabaseIntegrityException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ShopManager {
	
	@Autowired
	ShopService shopService;
	
	@Autowired
	ShopItemService shopItemService;

	@Autowired
	ItemTypeService itemTypeService;

	@Autowired
	UserService userService;

	@Autowired
	InventoryService inventoryService;

	public boolean userHasValidShop(User player) {
		return (shopService.getShop(player) != null);
	}

	/** Shows the currently available shop  for the specific player.
	 *
	 * @param player
	 * 		Player to show shop  for
	 * @return
	 * 		List of available ShopItems
	 */
	public List<ShopItem> getOrCreateShopStock(User player) {
		// Check if there is a shop  which is still valid (shops change with the ingame time).

		List<ShopItem> shopItems = null;

		Shop shop = shopService.getShop(player);

		if(shop != null) {
			System.out.println("shop is not null, fetching items");
			// TODO: Check if valid

			// If yes, fetch items and return
			shopItems = shopItemService.getShopItems(shop);
		}
		else {
			// If there is no valid shop or the available one is outdated, we generate a new one (and remove the old one).
			System.err.println("No valid shop for player. Creating a new one (NOT IMPLEMENTED YET!");
			shopItems = generateNewShopItems(player);
		}

	    return shopItems;
	}

	private List<ShopItem> generateNewShopItems(User player) {
		ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();

		shopItems = new ArrayList<ShopItem>();

		Shop shop = new Shop(player);
		shop = shopService.saveShop(shop);

		// TODO: Get random selection of item types

		// TODO: Dummy, replaced later
		List<ItemType> randomItemTypes = itemTypeService.getRandomItemTypes(1337);

		for (ItemType it : randomItemTypes) {
			// TODO: Implement randomization within bounds for each itemType (newspapers should not cost 500€)
			int randomAmount = 5;
			double randomPrice = 4.99;
			ShopItem shopItem = new ShopItem(shop, it, randomAmount, randomPrice);
			shopItem = shopItemService.saveShopItem(shopItem);
			shopItems.add(shopItem);
		}

		return shopItems;
	}

	// TODO: Test if transactional behaviour works
	public boolean buyItems(User player, long itemTypeId, int itemAmount) throws DatabaseIntegrityException {
		if(!userHasValidShop(player)) {
			// User does not have a valid shop
			System.err.println("Player " + player.getUserName() + "does not have a valid shop. Need to request shop stock first.");
			// TODO: Add proper error handling
			return false;
		}

		// Get current shop revision for user
		Shop currentShop = getCurrentShopForUser(player);

		// Verify requested amount of item type is available
		if(!shopItemService.shopHasItemAmount(currentShop, itemTypeId, itemAmount)) {
			// Not enough items offered
			System.err.println("Shop of player " + player.getUserName() + "does not have " + itemAmount + " of item type " + itemTypeId);
			// TODO: Add proper error handling
			return false;
		}

		// TODO: Deal with money

		ItemType itemType = itemTypeService.getItemByTypeId(itemTypeId);

		if(itemType == null) {
			throw new DatabaseIntegrityException("ItemType " + itemTypeId + " is not defined in the database!");
		}

		// Add items to player's inventory
		inventoryService.addItemCount(player, itemType, itemAmount);
		System.out.println("Added " + itemAmount + " " + itemType.getItemTypeDisplayName() + " to player " + player.getUserName() + "'s inventory.");

		// Remove item's from shop's stock
		if(!shopItemService.shopItemRemove(currentShop, itemType, itemAmount)) {

			return false;
		}

		// TODO: Create entity "ShopHistory" (Date, UserId, ItemTypeId, ItemCount, ItemPrice) and add an entry to it, so we can later check what the user bought when

		// Items were bought successfully
		System.out.println("Removed " + itemAmount + " " + itemType.getItemTypeDisplayName() + " from player " + player.getUserName() + " shop's stock.");
		return true;
	}

	private Shop getCurrentShopForUser(User player) {
		return shopService.getShop(player);
	}

	private long getCurrentShopIdForUser(User player) {
		// TODO: Ensure there is a shop
		Optional<Shop> currentShop = Optional.ofNullable(getCurrentShopForUser(player));

		if(currentShop.isPresent())
			return currentShop.get().getShopId();
		else
			// TODO: Think of other "negative" value
			return -1L;
	}

	public void sayHello() {
	    System.out.println("Hello from ShopManager!");
	}
	
	

}
