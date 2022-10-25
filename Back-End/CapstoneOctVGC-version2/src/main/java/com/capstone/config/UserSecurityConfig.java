package com.capstone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class UserSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors()
			.and()
				.authorizeRequests()
					.antMatchers(HttpMethod.GET, "")
						.hasAuthority("SCOPE_read")
					.antMatchers(HttpMethod.PUT, "")
						.hasAuthority("SCOPE_write")
					.anyRequest()
						.authenticated()
				.and()
					.oauth2ResourceServer()
						.jwt();
	}
}
