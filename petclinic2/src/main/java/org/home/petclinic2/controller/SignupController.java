package org.home.petclinic2.controller;

import javax.validation.Valid;

import org.home.petclinic2.domain.User;
import org.home.petclinic2.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Allows users to sign up
 * <p>
 * Also auto-logs the user into the application... which isn't typically
 * something you see done but it is a good example to show
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
		// not sure what this is doing. Flash attributes help spring mesh with
		// flash but we aren't using flash... They might have had an idea to put
		// sections on webpages to announce to the user when things happened
		// similar to toastr.js
		redirect.addFlashAttribute("globalMessage", "Successfully signed up");

		// the following authenticates the user so that they don't have to log
		// in just after signing up
		UserDetails userDetails = userDetailsService.loadUserByUsername(user
				.getEmail());
		Authentication auth = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);

		// redirects the user to the home controller
		return "redirect:/";
	}
}
