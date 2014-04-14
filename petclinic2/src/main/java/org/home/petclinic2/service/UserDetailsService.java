package org.home.petclinic2.service;

import org.home.petclinic2.domain.User;
import org.home.petclinic2.domain.dto.MyUserDetails;

public interface UserDetailsService extends
		org.springframework.security.core.userdetails.UserDetailsService {

	public MyUserDetails getCurrentUser();

	public User getSystemUser();

	public User save(User user);

}
