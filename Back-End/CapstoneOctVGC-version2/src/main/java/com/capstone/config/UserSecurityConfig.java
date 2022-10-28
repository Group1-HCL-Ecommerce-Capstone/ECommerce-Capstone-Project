package com.capstone.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capstone.repository.UserRepository;
import com.capstone.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig {//extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
/*
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
*/
	/*
	private UserService userServ;
	
	public UserSecurityConfig(UserService userServ) {
		this.userServ=userServ;
	}
	*/
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(email -> userServ
				.findUserByEmail(email)
				.orElseThrow(
						()->new UsernameNotFoundException(
								format("User: %s, not found", username)
								)
						));
	}
	*/	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	}

	*/
	/*
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.authorizeRequests(auth->auth
						.mvcMatchers("/").permitAll()
						.anyRequest().authenticated()
						)
				.formLogin(Customizer.withDefaults())
				//.headers(headers->headers.frameOptions().sameOrigin())
				.build();
	}
	*/
	/*
	@Bean
	InMemoryUserDetailsManager users() {
		return new InMemoryUserDetailsManager(
				User.withUsername("vero")
				.password("{noop}password")
				.roles("ADMIN")
				.build());
	}
	*/
	
	/*
	@Autowired
	UserService usrService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	*/
	
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception{
	 * http.cors() .and() .authorizeRequests() .antMatchers(HttpMethod.GET, "")
	 * .hasAuthority("SCOPE_read") .antMatchers(HttpMethod.PUT, "")
	 * .hasAuthority("SCOPE_write") .anyRequest() .authenticated() .and()
	 * .oauth2ResourceServer() .jwt(); }
	 */
}
