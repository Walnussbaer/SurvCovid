package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import java.util.List;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.AppConfig;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.SurvCovidApplication;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Inventory;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.InventoryRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        if (inventoryRepository == null)
            throw new NullPointerException("inventoryRepository cannot be null");
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory getInventory(String userName) {
        //ApplicationContext appContext = SurvCovidApplication.getApplicationContext();
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = appContext.getBean("userService", UserService.class);
        User user = userService.getUserByName(userName);
        return user.getInventory();
    }

    public Inventory saveInventory(Inventory inventory) {

        if (inventory == null) {
            throw new NullPointerException("inventory cannot be null");
        }
        return this.inventoryRepository.save(inventory);
    }


}
