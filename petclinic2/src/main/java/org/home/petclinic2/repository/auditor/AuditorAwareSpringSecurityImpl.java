package org.home.petclinic2.repository.auditor;

import org.home.petclinic2.domain.User;
import org.home.petclinic2.domain.dto.MyUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Data JPA auditor aware impl which uses Spring Security to resolve
 * current user
 * <p>
 * When an entity is created or updated we want to put on the database who did
 * the creation or update, this class takes care of that by retrieving the user
 * name from the Spring Security context
 * <p>
 * See:
 * http://blog.countableset.ch/2014/03/08/auditing-spring-data-jpa-java-config/
 */
public class AuditorAwareSpringSecurityImpl implements AuditorAware<User> {

	public User getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}

		MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return (User) myUserDetails;
	}

}
