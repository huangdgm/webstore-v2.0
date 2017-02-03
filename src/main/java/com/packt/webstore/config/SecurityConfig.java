package com.packt.webstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("john").password("pa55word").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("USER", "ADMIN");
		auth.inMemoryAuthentication().withUser("huangdgm").password("123456").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.formLogin().loginPage("/login").usernameParameter("userId").passwordParameter("password");

		httpSecurity.formLogin().defaultSuccessUrl("/market/products").failureUrl("/login?error");

		httpSecurity.logout().logoutSuccessUrl("/login?logout");

		httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");

		/**
		 * Understand the following statement:
		 * 
		 * 1. All user roles can access http://localhost:8080/webstore 2. Only
		 * the user with the role of Admin can access
		 * http://localhost:8080/webstore/market/products/addProduct 3. Only the
		 * user with the role of USER can access
		 * http://localhost:8080/webstore/market/**, but not the URL as stated
		 * in No.2
		 */
		httpSecurity.authorizeRequests().antMatchers("/").permitAll().antMatchers("/**/addProduct")
				.access("hasRole('ADMIN')").antMatchers("/**/customers/**").access("hasRole('ADMIN')").antMatchers("/**/market/**").access("hasRole('USER')");
		// httpSecurity.authorizeRequests().antMatchers("/**/addProduct").access("hasRole('ADMIN')").antMatchers("http://localhost:8080/webstore/welcome/greeting").access("hasRole('USER')");
//		httpSecurity.authorizeRequests().antMatchers("/").permitAll().antMatchers("/**/products")
//				.access("hasRole('USER')").antMatchers("/**/addProduct").access("hasRole('ADMIN')");

		httpSecurity.csrf().disable();
	}
}
