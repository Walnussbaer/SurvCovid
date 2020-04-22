package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.GameEventManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ServiceManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * This class handles all game related logic, e.g. pushing events to the players. 
 * 
 * The game manager uses several nested objects (such as a GameEventManger) to achieve its task. 
 * 
 * It is basically a coordinator of all the submanagers. 
 * 
 * @author volke
 *
 */
@Component
public class GameManager {
    
    @Autowired
    private GameEventManager gameEventManager;

    @Autowired
    private ShopManager shopManager;
        
    public GameManager() {
        
    }
    
    @Autowired
    public GameManager(GameEventManager gameEventManager, ShopManager shopManager) {
        this.gameEventManager = gameEventManager;
        this.shopManager = shopManager;
    }
    
    public void sayHello() {
        System.out.println("Hello from the GameManager!");
    }
    
    public GameEventManager getGameEventManager() {
        return this.gameEventManager;
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }
    
    public void setGameEventmanager(GameEventManager gameEventManager) {
        this.gameEventManager = gameEventManager;
    }
    
}
