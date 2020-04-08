package org.hackathon.wirvswirus.thecouchdevs.SurvCovid;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.GameEventDefinitionType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventDefinitionService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.GameEventService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SurvCovidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurvCovidApplication.class, args);
	}
	
	/**
	 * Creates test data for the user table. 
	 * 
	 * @param userService
	 * @return
	 */
	@Bean CommandLineRunner createUserTestData(UserService userService) {
		return args -> {
			
			System.out.println("Creating user test data");
			
			Stream.of("John","Peter","Max","Volker","Paul","Sharmin","Vroni","Philipp","Gino","Henning").forEach(name -> {
				User user = new User(name);
				userService.saveUser(user);
			});
			System.out.println("Created test users");
			userService.getAllUsers().forEach(user -> {
				System.out.println(user.getUserName());
				System.out.println(user.getUserId());
			});
			
			System.out.println("Finished creating user test data");
		};
	}
	
	/**
	 * Creates test data for the game event table. 
	 * 
	 * @param gameEventService
	 * @return 
	 */
	@Bean CommandLineRunner createEventTestData(GameEventService gameEventService, GameEventDefinitionService gameEventDefinitionService, UserService userService) {
		
		return args -> {
			
			System.out.println("Creating game event test data");
			
			User player = new User("Peter");
			userService.saveUser(player);
			
			GameEventDefinition gameEventDefinition = new GameEventDefinition("This is a test event. What do you want to do?", "test",GameEventDefinitionType.GENERIC_EVENT);
			gameEventDefinitionService.saveGameEventDefinition(gameEventDefinition);
			
			GameEvent gameEvent = new GameEvent(LocalDateTime.now(), player, gameEventDefinition,false);
			gameEventService.saveGameEvent(gameEvent);
			
			System.out.println("Finished creating game event test data");
			
		};
		
	}
}
