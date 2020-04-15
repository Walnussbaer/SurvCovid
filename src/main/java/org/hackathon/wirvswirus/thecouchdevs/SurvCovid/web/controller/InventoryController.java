package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.InventoryItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ShopItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.InventoryService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class InventoryController {

    @Autowired
    GameManager gameManager;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    UserService userService;

    @GetMapping("/inventory/items")
    public List<InventoryItem> getInventory(@RequestParam(name="user_id", required=true)long userId,
                                            HttpServletResponse response) {

        Optional<User> player;

        player = userService.getUserById(userId);

        if (player.isEmpty()) {
            // Set HTTP status "401 Unauthorized"
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        System.out.println("Received request for inventory of user " + player.get().getUserName());

        // Set HTTP status "200 OK"
        response.setStatus(HttpServletResponse.SC_OK);
        return inventoryService.getInventory(player.get());
    }


    @PostMapping("/inventory/items")
    public boolean buyItems(
            @RequestParam(name="user_id", required = true) long userId,
            /*@RequestParam(name="shop_id", required=true) long shopId,*/
            @RequestParam(name="item_id", required=true) long shopItemId,
            @RequestParam(name="item_amount", required=true) int itemAmount,
            HttpServletResponse response) {

        ShopManager shopManager = gameManager.getShopManager();

        Optional<User> player;

        player = userService.getUserById(userId);

        if (player.isEmpty()) {
            // Set HTTP status "401 Unauthorized"
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        boolean success = false;

        try {
            success = shopManager.buyItems(player.get(), shopItemId, itemAmount);
        }
        catch(Exception ex) {
            // Set HTTP status "403 Forbidden"
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        if(!success) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // Buying items was successful
        // Set HTTP status "200 OK"
        response.setStatus(HttpServletResponse.SC_OK);
        return success;
    }

}
