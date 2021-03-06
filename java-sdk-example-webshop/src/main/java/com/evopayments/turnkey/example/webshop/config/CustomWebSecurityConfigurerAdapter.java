package com.evopayments.turnkey.example.webshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	// note: this is just a very basic example, in production always use proper
	// authentication (with csrf etc.)!

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("shopadmin").password(passwordEncoder().encode("shopadmin"))
				.authorities("ROLE_USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin/**").hasRole("USER")
		.anyRequest().permitAll()
		.and().httpBasic().realmName("java-sdk-example-webshop")
		.and().csrf().ignoringAntMatchers("/transactionresultcallback**", "/transactionresult**").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); 
		// these two endpoints have to be added as CSRF exceptions, because the HTTP POST request legitimately arrives from another site 
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}