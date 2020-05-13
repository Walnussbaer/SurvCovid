package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.config;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetailsService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.jwt.AuthEntryPointJwt;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // enable Spring Security's web security support and provide Spring MVC integration, apply this class to the global Web Security
@EnableGlobalMethodSecurity( // provides AOP security on methods, enables @PreAuthorize, @PostAuthorize, supports JSR-250
        //securedEnabled = true,
        //jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // WebSecurityConfigureAdapter: provides HttpSecurity configurations to configure CORS, CSRF, session management
    // rules for protected resources, ...

    @Autowired
    private SurvCovidUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizeHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{

        // configure the DaoAuthenticationProvider
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // configure password encoder for the DaoAuthenticatinProvider (so we don't have to use plain text)
        return new BCryptPasswordEncoder();
    }

	@Override
    // this method defines which URL paths should be secured and which should not
    // per default, all paths require authentication if you do not explicitly allow access
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable() // configure CORS and CSRF
                .exceptionHandling().authenticationEntryPoint(unauthorizeHandler).and() // define exception handler
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/auth/**").permitAll() // / authentication endpoint does not need authorization
                    .antMatchers("/api/v1/**").permitAll() // TODO: discuss in team whether we want another layer in our URL only for security endpoints
                    .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // which filter and when


        // when a user successfully logs in, they are redirected to the previously requested page that required authentication
        // in this case, there is a custom /login page (specified by loginPage() and everyone is allowed to view it




    }


}
