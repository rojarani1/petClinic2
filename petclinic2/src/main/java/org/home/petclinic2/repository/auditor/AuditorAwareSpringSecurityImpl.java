package org.home.petclinic2.repository.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Data JPA auditor aware impl which uses Spring Security to resolve
 * current user
 * <p>
 * This is currently not working
 */
public class AuditorAwareSpringSecurityImpl implements AuditorAware<String> {

	public String getCurrentAuditor() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
