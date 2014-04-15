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

	/**
	 * Registers a method security handler
	 * <p>
	 * This allows us to secure methods within the application so that only
	 * specific roles can access them
	 * 
	 * @author phil
	 * 
	 */
	@Configuration
	// the enable global method security allows for us to secure methods within
	// classes requiring that the current user have specific roles
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

	/**
	 * Configures spring security
	 * <p>
	 * We're defining which URL's are accessible for those that aren't
	 * authenticated (e.g. the signup page and resources folder), we are also
	 * stating that any other requests require authentication. Finally, we are
	 * defining the login page signature and that we will allow all users to
	 * access the log out location
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// I am a big fan of using ctrl+shift+f which is an eclispe short cut
		// that formats your code consistently. Sometimes, however, the
		// formatting gets in the way and formats code that looks better the
		//way we originally wrote it. By using @formatter:off and on we can 
		//tell eclipse to leave certain portions of our code alone. 
		//See: http://stackoverflow.com/questions/1820908/how-to-turn-off-the-eclipse-code-formatter-for-certain-sections-of-java-code 
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

	/**
	 * Defining user details service impl
	 * <p>
	 * Spring security retains the authenticated user details within the
	 * authentication's principle. It leaves what we actually store in the
	 * principle (user details) up to us. This portion of the spring security
	 * config is our way of defining what we store in the principle
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void registerAuthentication(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
