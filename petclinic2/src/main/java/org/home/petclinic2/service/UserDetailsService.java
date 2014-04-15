package org.home.petclinic2.service;

import org.home.petclinic2.domain.User;
import org.home.petclinic2.domain.dto.MyUserDetails;

/**
 * Spring Security UserDetailsService interface additions
 * <p>
 * Spring Security allows us to control what we store in the authentication's
 * principle. This interface extends the base contract with additional
 * responsibilities for our UserDetailsService impl
 * 
 * @author phil
 * 
 */
public interface UserDetailsService extends
		org.springframework.security.core.userdetails.UserDetailsService {

	/**
	 * Retrieve the current user
	 * <p>
	 * Typically, retrieving the user isn't difficult, but handling consistently
	 * what to do when the user isn't authenticated just yet adds to what would
	 * have to be repeated every time we want to retrieve the current user
	 * 
	 * @return
	 */
	public MyUserDetails getCurrentUser();

	/**
	 * Sometimes there isn't a user and we just want to use the system user's
	 * account, this retrieves the system user
	 * 
	 * @return
	 */
	public User getSystemUser();

	/**
	 * Saves a user (creation or update). Is also responsible for getting the
	 * same password encoder user by the authenticationManager to save the
	 * password in the same format
	 * <p>
	 * This may need to be split to prevent users from trying to sign up with an
	 * account that already exists
	 * 
	 * @param user
	 * @return
	 */
	public User save(User user);

}
