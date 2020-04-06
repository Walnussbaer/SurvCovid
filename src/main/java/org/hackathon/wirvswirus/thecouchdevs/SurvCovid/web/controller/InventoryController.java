package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Inventory;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Item;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @Autowired InventoryService inventoryService;

    @GetMapping("/inventory")
    //public Inventory getInventory(@RequestParam(name="username", required=true)String userName) {
    public String getInventory(@RequestParam(name="username", required=true)String userName) {
        System.out.println("Received request for inventory of user " + userName);

        Inventory inventory = inventoryService.getInventory(userName);

        System.out.println("Fetched inventory of user " + userName + " from the database.");
        System.out.println("Inventory contents:");
        for(Item i: inventory.getItems())
            System.out.println("  ("+i.getItemId()+") " + i.getItemName());

        //return inventory;

        // Serialize inventory as JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String inventoryString = null;
        try {
            inventoryString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inventory);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(inventoryString == null)
            // TODO: Return error code
            return null;
        else
            return inventoryString;
    }

}
