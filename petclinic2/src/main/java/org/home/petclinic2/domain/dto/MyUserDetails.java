package org.home.petclinic2.domain.dto;

import java.util.Collection;

import org.home.petclinic2.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

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

	public org.springframework.security.core.userdetails.User getUser() {
		org.springframework.security.core.userdetails.User ssUser = new org.springframework.security.core.userdetails.User(
				getUsername(), getPassword(), getAuthorities());
		return ssUser;
	}
}