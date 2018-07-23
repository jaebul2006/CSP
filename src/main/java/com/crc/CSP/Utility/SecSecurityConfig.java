package com.crc.CSP.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.crc.CSP.HomeController;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
			.withUser("username").password("password").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.authorizeRequests()
			.antMatchers("/login*").anonymous()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login.jsp")
			.defaultSuccessUrl("/")
			.failureUrl("/login_fail")
			.and()
			.logout().logoutSuccessUrl("/login.jsp");
		
		/*
		http
			.csrf().disable()
			.exceptionHandling()
				.authenticationEntryPoint(RestAuthenticationEntryPoint())
			*/
			
	}
	
}
