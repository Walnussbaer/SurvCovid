package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    
	@GetMapping("/shop/stock")
	public List<ShopItem> getShopStock(/*@RequestParam*/@RequestHeader(name="user_id", required=true)long userId,
														HttpServletResponse response) {

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
