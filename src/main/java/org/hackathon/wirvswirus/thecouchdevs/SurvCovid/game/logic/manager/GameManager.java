package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.GameEventManager;

/**
 * Singleton which handles all game logic related decisions. 
 * @author volke
 *
 */
public class GameManager {
    
    private static GameManager instance = new GameManager();
    private final GameEventManager gameEventManager = new GameEventManager();

    
    private GameManager() {
        
    }
    
    public static GameManager getInstance() {
        return instance;
    }
    
    public void sayHello() {
        System.out.println("Hello from the GameManager!");
    }
    
    public GameEventManager getGameEventManager() {
        return this.gameEventManager;
    }
    
    

}
