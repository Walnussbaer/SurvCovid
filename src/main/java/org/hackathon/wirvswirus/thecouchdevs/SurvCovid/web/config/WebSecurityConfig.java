package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // enable Spring Security's web security support and provide Spring MVC integration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
    // this method defines which URL paths should be secured and which should not
    // per default, all paths require authentication if you do not explicitly allow access
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll() // / and home do not require authentication
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();

        // when a user successfully logs in, they are redirected to the previously requested page that required authentication
        // in this case, there is a custom /login page (specified by loginPage() and everyone is allowed to view it


    }
    

}
