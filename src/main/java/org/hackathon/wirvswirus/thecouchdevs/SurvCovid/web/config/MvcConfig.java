package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry){

        /*add view controllers to return specific templates */
        //registry.addViewController("/").setViewName("test.html");
        //registry.addViewController("/abcdefg").setViewName("/templates/blabla.html");
        //registry.addViewController("/login").setViewName("login");

    }
}
