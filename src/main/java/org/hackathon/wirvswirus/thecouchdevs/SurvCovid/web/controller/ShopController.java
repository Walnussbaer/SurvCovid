package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class ShopController {

    @Autowired
    GameManager gameManager;

    @Autowired 
    UserService userService;
    
    @Autowired
	ShopService shopService;
    
    @Autowired
	ShopItemService shopItemService;
    
	@GetMapping("/api/shop/stock")
	@PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
	public List<ShopItem> getShopStock(@ApiIgnore @AuthenticationPrincipal SurvCovidUserDetails userDetails,
									   @RequestParam(name="user_id", required=true)long userId,
									   HttpServletResponse response) {

		System.out.println("[DEBUG] ##### Accessing user shop endpoint to LIST STOCK.");
		System.out.println("[DEBUG] Authorities: ");
		for(GrantedAuthority auth: userDetails.getAuthorities())
			System.out.println("  - " + auth);

		System.out.println("[DEBUG] UserID: " + userDetails.getId() + " / " + userId);

		// Check if the user is an admin
		if(!userDetails.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
			System.out.println("[DEBUG] User is not an admin");
			// If the user is not an admin, check if he try to access his own inventory
			if (userDetails.getId() != userId) {
				System.out.println("[DEBUG] User is not an admin and tries to access another user's shop stock!");
				// The user try to access another user's inventory => we do not allow this
				// Set HTTP status "401 Unauthorized"
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
		}

		System.out.println("[DEBUG] User is allowed to access the inventory");

	    Optional<User> player = userService.getUserById(userId);
	    
	    if (player.isEmpty()) {
			// Set HTTP status "401 Unauthorized"
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        return null;
	    }

		ShopManager shopManager = gameManager.getShopManager();

		if (shopManager == null) {
			// Set HTTP status "500 Internal Server Error"
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

	    List<ShopItem> shopItems = shopManager.getOrCreateShopStock(player.get());
	    
	    if (shopItems == null) {

	        return null;
	        // TODO implement proper error handling
	    }
	   
		return shopItems;
	}

}
