package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.InventoryItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        if (inventoryRepository == null)
            throw new NullPointerException("inventoryRepository cannot be null");
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryItem> getInventory(User user) {
        return this.inventoryRepository.findByUser(user);
    }

    public List<InventoryItem> getInventory(long userId) {
        Optional<User> user = userService.getUserById(userId);
        if(user.isEmpty()) {
            // TODO: Add error handling
            return null;
        }
        return this.getInventory(user.get());
    }

    public void addItem(User user, ItemType itemType) {
        this.addItemCount(user, itemType, 1);
    }

    public void addItemCount(User user, ItemType itemType, int itemCount) {
        // If the user already has some items of this type, we fetch them
        InventoryItem inventoryItem = this.inventoryRepository.findInventoryItemByUserAndItemType(user, itemType);

        // If the user does not have any items of this type yet, we initialize the entry
        if(inventoryItem == null) {
            System.err.println("[DEBUG] user: " + user);
            System.err.println("[DEBUG] itemType: " + itemType);
            inventoryItem = new InventoryItem(user, itemType, 0);
        }
        
        // We add the new items to the existing ones
        inventoryItem.addItemCount(itemCount);

        // We save the update to the database
        this.inventoryRepository.save(inventoryItem);
    }

    public InventoryItem saveInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem == null)
            throw new NullPointerException("inventory cannot be null");
        return this.inventoryRepository.save(inventoryItem);
    }

}
