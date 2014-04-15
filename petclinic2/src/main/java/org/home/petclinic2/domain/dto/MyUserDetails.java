package org.home.petclinic2.domain.dto;

import java.util.Collection;

import org.home.petclinic2.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User Details impl
 * <p>
 * Spring security allows us to configure what is stored in the spring security
 * authentication context, this is typically user details and this is our
 * implementation
 * 
 * @author phil
 * 
 */
@SuppressWarnings("serial")
public class MyUserDetails extends User implements UserDetails {

	public MyUserDetails(User user) {
		super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_USER");
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}