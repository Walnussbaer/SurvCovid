package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.InventoryItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.DatabaseIntegrityException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.ItemBuyRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.InventoryService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @GetMapping("/api/inventory/items")
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public List<InventoryItem> getInventory(@AuthenticationPrincipal SurvCovidUserDetails userDetails,
                                            @RequestParam(name="user_id", required=true)long userId,
                                            HttpServletResponse response) {

        System.out.println("[DEBUG] ##### Accessing user inventory endpoint to LIST ITEMS.");
        System.out.println("[DEBUG] Authorities: ");
        for(GrantedAuthority auth: userDetails.getAuthorities())
            System.out.println("  - " + auth);

        System.out.println("[DEBUG] UserID: " + userDetails.getId() + " / " + userId);

        // Check if the user is an admin
        if(!userDetails.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
            System.out.println("[DEBUG] User is not an admin");
            // If the user is not an admin, check if he try to access his own inventory
            if (userDetails.getId() != userId) {
                System.out.println("[DEBUG] User is not an admin and tries to access another user's inventory!");
                // The user try to access another user's inventory => we do not allow this
                // Set HTTP status "401 Unauthorized"
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return null;
            }
        }

        System.err.println("[DEBUG] User is allowed to access the inventory");

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


    @PostMapping("/api/inventory/items")
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public boolean buyItems(@AuthenticationPrincipal SurvCovidUserDetails userDetails,
//                            @Valid @RequestBody ItemBuyRequest itemBuyRequest,
            @RequestParam(name="user_id", required = true) long userId,
            /*@RequestParam(name="shop_id", required=true) long shopId,*/
            @RequestParam(name="item_type_id", required=true) long itemTypeId,
            @RequestParam(name="item_amount", required=true) int itemAmount,
            HttpServletResponse response) {

        System.out.println("[DEBUG] ##### Accessing user inventory endpoint to BUY ITEMS.");
        System.out.println("[DEBUG] Authorities: ");
        for(GrantedAuthority auth: userDetails.getAuthorities())
            System.out.println("  - " + auth);

        System.out.println("[DEBUG] UserID: " + userDetails.getId() + " / " + userId);

        // Check if the user is an admin
        if(!userDetails.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
            System.out.println("[DEBUG] User is not an admin");
            // If the user is not an admin, check if he try to access his own inventory
            if (userDetails.getId() != userId) {
                System.out.println("[DEBUG] User is not an admin and tries to access another user's inventory!");
                // The user try to access another user's inventory => we do not allow this
                // Set HTTP status "401 Unauthorized"
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        System.out.println("[DEBUG] User is allowed to access the inventory");

        ShopManager shopManager = gameManager.getShopManager();

        Optional<User> player;

        player = userService.getUserById(userId);

        if (player.isEmpty()) {
            System.err.println("Could not retrieve user by id '" + userId + "'");
            // Set HTTP status "401 Unauthorized"
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        boolean success = false;

        try {
            success = shopManager.buyItems(player.get(), itemTypeId, itemAmount);
        }
        catch(DatabaseIntegrityException ex0) {
            System.err.println("Database integrity was violated!"
                    + "\nCould not buy items for user!"
                    + "\n  UserId: " + userId
                    + "\n  ItemType: " + itemTypeId
                    + "\n  ItemAmount: " + itemAmount
                    + "\nException: " + ex0.getMessage()
                    + "\n\n");
            ex0.printStackTrace();
        }
        catch(Exception ex1) {
            System.err.println("An exception occured while buying items for user."
                    + "\n  UserId: " + userId
                    + "\n  ItemType: " + itemTypeId
                    + "\n  ItemAmount: " + itemAmount
                    + "\nException: " + ex1.getMessage()
                    + "\n\n");
            ex1.printStackTrace();

            // Set HTTP status "500 Internal Server Error"
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        if(!success) {
            System.err.println("Could not buy items for user."
                    + "\n  UserId: " + userId
                    + "\n  Itemtype: " + itemTypeId
                    + "\n  ItemAmount: " + itemAmount);

            // Set HTTP status "403 Forbidden"
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setHeader("X-UserMessage",
                               "Could not buy the requested items because they are not available in the shop.");
            return false;
        }

        // Buying items was successful
        // Set HTTP status "200 OK"
        response.setStatus(HttpServletResponse.SC_OK);
        return success;
    }

}
