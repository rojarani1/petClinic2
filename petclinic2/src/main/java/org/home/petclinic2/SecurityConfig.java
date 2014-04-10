package org.home.petclinic2;

import org.home.petclinic2.service.impl.PermissionEvaluatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Spring Security config
 * 
 * @author phil
 * 
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class MethodSecurityConfiguration extends
			GlobalMethodSecurityConfiguration {
		@Autowired
		private PermissionEvaluatorImpl permissionEvaluator;

		@Override
		protected MethodSecurityExpressionHandler createExpressionHandler() {
			DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
			handler.setPermissionEvaluator(permissionEvaluator);
			return handler;
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		 http
         	.authorizeRequests()
         		.antMatchers("/resources/**","/signup").permitAll()
         		.anyRequest().authenticated()
         		.and()
         	.formLogin()
         		.loginPage("/login")
         		.permitAll()
         		.and()
         	.logout()
         		.permitAll();
		// @formatter:on
	}

	@Autowired
	public void registerAuthentication(AuthenticationManagerBuilder auth)
			throws Exception {
		// @formatter:off
		auth
			.userDetailsService(userDetailsService);
        // @formatter:on
	}

}
