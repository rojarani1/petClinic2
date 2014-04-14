package org.home.petclinic2.controller;

import java.util.List;

import javax.validation.Valid;

import org.home.petclinic2.domain.User;
import org.home.petclinic2.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Allows users to sign up.
 * 
 * @author Rob Winch
 * 
 */
@Controller
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(method = RequestMethod.GET)
	public String signupForm(@ModelAttribute User user) {
		return "user/signup";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String signup(@Valid User user, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "user/signup";
		}
		user = userDetailsService.save(user);
		// not sure what this is doing
		// redirect.addFlashAttribute("globalMessage",
		// "Successfully signed up");

		List<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList("ROLE_USER");
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), authorities);
		Authentication auth = new UsernamePasswordAuthenticationToken(
				userDetails, user.getPassword(), authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:/";
	}
}
