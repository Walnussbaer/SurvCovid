package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import io.swagger.annotations.ApiOperation;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.InventoryItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.ItemBuyStatus;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.DatabaseIntegrityException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.exception.UserNotExistingException;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request.ItemBuyRequest;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response.ItemBuyResponse;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.InventoryService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiOperation(value = "List inventory of a user.",
                  notes = "Lists the items in a user's inventory.")
    public ResponseEntity<List<InventoryItem>> getInventory(@ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails,
                                                            @RequestParam(name="user_id", required=true)long userId) {

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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }

        System.err.println("[DEBUG] User is allowed to access the inventory");

        User player;
        
        try {
        	player = userService.getUserById(userId);
        } catch (UserNotExistingException unee) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        System.out.println("Received request for inventory of user " + player.getUserName());

        // Set HTTP status "200 OK"
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventory(player));
    }


    @PostMapping("/api/inventory/items")
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    @ApiOperation(value = "Buy items for a user.",
                  notes = "Buy items from the user's shop and put them into his inventory. "
                          + "This requires the user's shop's stock to be requested using the shop endpoint before.")
    public ResponseEntity<ItemBuyResponse> buyItems(@ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails,
                                    @Valid @RequestBody ItemBuyRequest itemBuyRequest) {

        System.out.println("[DEBUG] ##### Accessing user inventory endpoint to BUY ITEMS.");
        System.out.println("[DEBUG] Authorities: ");
        for(GrantedAuthority auth: userDetails.getAuthorities())
            System.out.println("  - " + auth);

        System.out.println("[DEBUG] UserID: " + userDetails.getId() + " / " + itemBuyRequest.getUserId());

        // Check if the user is an admin
        if(!userDetails.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
            System.out.println("[DEBUG] User is not an admin");
            // If the user is not an admin, check if he try to access his own inventory
            if (userDetails.getId() != itemBuyRequest.getUserId()) {
                System.out.println("[DEBUG] User is not an admin and tries to access another user's inventory!");
                // The user try to access another user's inventory => we do not allow this
                // Set HTTP status "401 Unauthorized"
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }

        System.out.println("[DEBUG] User is allowed to access the inventory");

        ShopManager shopManager = gameManager.getShopManager();

        User player;

        try {
        	player = userService.getUserById(itemBuyRequest.getUserId());
        	
        } catch (UserNotExistingException unee) {
            System.err.println("Could not retrieve user by id '" + itemBuyRequest.getUserId() + "'");
            // Set HTTP status "401 Unauthorized"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
       

        boolean success = false;

        try {
            success = shopManager.buyItems(player, itemBuyRequest.getItemTypeId(), itemBuyRequest.getItemAmount());
        }
        catch(DatabaseIntegrityException ex0) {
            System.err.println("Database integrity was violated!"
                    + "\nCould not buy items for user!"
                    + "\n  UserId: " + itemBuyRequest.getUserId()
                    + "\n  ItemType: " + itemBuyRequest.getItemTypeId()
                    + "\n  ItemAmount: " + itemBuyRequest.getItemAmount()
                    + "\nException: " + ex0.getMessage()
                    + "\n\n");
            ex0.printStackTrace();
        }
        catch(Exception ex1) {
            System.err.println("An exception occured while buying items for user."
                    + "\n  UserId: " + itemBuyRequest.getUserId()
                    + "\n  ItemType: " + itemBuyRequest.getItemTypeId()
                    + "\n  ItemAmount: " + itemBuyRequest.getItemAmount()
                    + "\nException: " + ex1.getMessage()
                    + "\n\n");
            ex1.printStackTrace();

            // Set HTTP status "500 Internal Server Error"
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("X-UserMessage",
                            "An internal error occurred. We are really sorry, please try again later...")
                    .body(null);
        }

        if(!success) {
            System.err.println("Could not buy items for user."
                    + "\n  UserId: " + itemBuyRequest.getUserId()
                    + "\n  Itemtype: " + itemBuyRequest.getItemTypeId()
                    + "\n  ItemAmount: " + itemBuyRequest.getItemAmount());

            // Set HTTP status "403 Forbidden"
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ItemBuyResponse(
                            "Could not buy the requested items because they are not available in the shop.",
                            ItemBuyStatus.INSUFFICIENT_STOCK));
        }

        // Buying items was successful
        // Set HTTP status "200 OK"
        return ResponseEntity.status(HttpStatus.OK).body(
                new ItemBuyResponse("Bought items.",
                ItemBuyStatus.SUCCESS));
    }

}
