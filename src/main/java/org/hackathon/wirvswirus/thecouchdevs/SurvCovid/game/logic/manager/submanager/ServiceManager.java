package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventChoiceService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class ServiceManager {
    
    @Autowired
    GameEventChoiceService gameEventChoiceService;
    
    @Autowired
    GameEventDefinitionService gameEventDefinitionService;
    
    @Autowired
    GameEventService gameEventService;
    
    @Autowired
    UserService userService;
    
    public ServiceManager() {
        
    }
    
    @Autowired
    public ServiceManager(GameEventChoiceService gameEventChoiceService, GameEventDefinitionService gameEventDefinitionService, GameEventService gameEventService, UserService userService) {
        this.gameEventChoiceService = gameEventChoiceService;
        this.gameEventDefinitionService = gameEventDefinitionService;
        this.gameEventService = gameEventService;
        this.userService = userService;
    }

    
}
