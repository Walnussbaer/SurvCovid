package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.jwt;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service.UserService;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security.SurvCovidUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter that executes once per request
 *
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UserService userService;

    @Autowired
    private SurvCovidUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        try {
            // get JWT token from AuthorizationHeader from the request
            String jwt = parseJwt(request);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // parse username from JWT token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                
                // get userDetails object for authenticating username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // build authentication object
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // set current user details in Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                /*
                After this, everytime you want to get UserDetails, just use SecurityContext like this:

                UserDetails userDetails =
                        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                // userDetails.getUsername()
                // userDetails.getPassword()
                // userDetails.getAuthorities()

                */
            }

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}",e);
        }
        filterChain.doFilter(request,response);

    }

    /**
     * Extracts the JWT token from the request which is stored inside the "Authorization" header of the request.
     *
     * @param request - the incoming request
     *
     * @return the extracted JWT token, else null
     */
    private String parseJwt(HttpServletRequest request){

        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7,headerAuth.length());
        }

        return null;

    }

}
