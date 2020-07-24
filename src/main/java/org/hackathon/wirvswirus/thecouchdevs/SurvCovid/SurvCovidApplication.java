package org.hackathon.wirvswirus.thecouchdevs.SurvCovid;

import java.util.ArrayList;
import java.util.List;


import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.RoleRepository;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.GameManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.manager.submanager.ShopManager;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.utils.StartupUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SurvCovidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurvCovidApplication.class, args);
	}


	@Bean
	public Docket api() {

		// Add default headers to all endpoints
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		// First header
		aParameterBuilder.name("Authorization") // Name of Parameter
				.modelRef(new ModelRef("string"))
				.parameterType("header")               // Type of Parameter: "header"
				.defaultValue("Bearer abcdefabcdefabcdef")
				.required(true)
				.build();
		java.util.List<Parameter> aParameters = new ArrayList<>();
		aParameters.add(aParameterBuilder.build());             // Add Parameters to all endpoints
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build().pathMapping("")
				.globalOperationParameters(aParameters);
	}

	/**
	 * Create test data on startup of the application
	 *
	 */
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean CommandLineRunner createTestDataOnStartup(ActivityDefinitionService activityDefinitionService,
													StartupUtils startupUtils) {
		// CommandLineRunner is a functional interface
		return args -> {

			startupUtils.createRoles();

			startupUtils.createSamplePlayerUsers();
			startupUtils.createSampleAdminUser();
			startupUtils.createSampleEventData();
			startupUtils.createSampleInventoryData();
			startupUtils.createSampleShopData();
			startupUtils.createSampleEventFlowData();


			// Add Activities
			
			//ActivityDefinitionCondition activityDefinitionCondition1 = new ActivityDefinitionCondition(ActivityDefinitionConditionType.INVENTORY_ITEM, 1,3);

			System.out.println("Adding some Activities");
			ActivityDefinition activityDefinition1 = new ActivityDefinition("Workout","One Hour Sport", 2, null, null);
			activityDefinitionService.saveActivityDefinition(activityDefinition1);
			ActivityDefinition activityDefinition2 = new ActivityDefinition("Learn Suaheli","Learn a Module in Online Suaheli Couse", 3 ,null, null);
			activityDefinitionService.saveActivityDefinition(activityDefinition2);
		};
	}
}
