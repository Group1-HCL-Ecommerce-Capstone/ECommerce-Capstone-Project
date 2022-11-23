package com.capstone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


//import com.capstone.security.AuthEntryPointJwt;

@EnableOAuth2Client
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//@Autowired
	//private AuthEntryPointJwt unauthorizedHandler;

	  @Override  
	    protected void configure(HttpSecurity http) throws Exception {
			/*
			 * http.antMatcher("/**") //.cors().and() //.csrf().disable()
			 * //.formLogin().disable() // .httpBasic().disable()
			 * //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			 * // .and() .authorizeRequests() .antMatchers("/test/all").permitAll() // allow
			 * all at test home page .antMatchers("/images**").permitAll() // allow all to
			 * access static images .anyRequest().authenticated() .and().oauth2Login(); //
			 * authenticate everything else!
			 * //.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
			 * //.and().oauth2ResourceServer().jwt();
			 */	
		  
		  //blacklist
		http
        .authorizeRequests()
        .antMatchers("/**", "/test/**", "/api/Products/**","/api/roles/**","/api/User/**","/api/ShoppingCart/**", "/api/auth/**").authenticated()
        .and().oauth2Client();
		}
}
