package org.home.petclinic2.service.impl;

import org.home.petclinic2.domain.User;
import org.home.petclinic2.domain.dto.MyUserDetails;
import org.home.petclinic2.repository.UserRepository;
import org.home.petclinic2.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService impl
 * <p>
 * Somewhat of a hybrid of a plain UserService and UserDetailsService, I'm still
 * trying to mesh the 2 concepts
 * 
 * @author phil
 * 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory
			.getLogger(UserDetailsServiceImpl.class);

	private static final String SYSTEM_USER = "system.account@noWhere.com";

	@Autowired
	private UserRepository userRepository;

	@Override
	public MyUserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			return null;
		}
		return new MyUserDetails(user);
	}

	@Override
	public MyUserDetails getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth == null || !auth.isAuthenticated()
				|| auth instanceof AnonymousAuthenticationToken) {
			return null;
		} else if (auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
			org.springframework.security.core.userdetails.User userBase = (org.springframework.security.core.userdetails.User) auth
					.getPrincipal();
			MyUserDetails userDetails = loadUserByUsername(userBase
					.getUsername());
			return userDetails;
		} else if (auth.getPrincipal() instanceof MyUserDetails) {
			MyUserDetails userRepositoryUserDetails = (MyUserDetails) auth
					.getPrincipal();
			MyUserDetails userDetails = loadUserByUsername(userRepositoryUserDetails
					.getEmail());
			return userDetails;
		} else {
			logger.error("Unable to return current user. Authentication's principle is either unexpected or non-implemented object: "
					+ auth.getPrincipal());
			throw new RuntimeException("Unsupported principle object");
		}
	}

	@Override
	public User getSystemUser() {
		return userRepository.findByEmail(SYSTEM_USER);
	}

	@Override
	public User save(User user) {
		// encoding the password using the same password encoder that our
		// authentication manager is using. This bean should be created in the
		// SecurityConfig and wired in using the AuthenicationManagerBuilder
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}

}
