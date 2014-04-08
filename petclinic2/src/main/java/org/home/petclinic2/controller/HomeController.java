package org.home.petclinic2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	// We're using slf4j for logging. Every controller and service
	// implementation should have loggers (just make sure to put the correct
	// class in the getLogger call). slf4j is a logging facade which
	// allows you to mix projects that use differing logging frameworks and
	// implement just 1 logging framework. For example, spring uses Jakarta
	// Commons Logging (JCL), hibernate uses some jboss logger and I like to use
	// log4j or logback (soon we get log4j2 though!). What it does is let you
	// write log statements with an interface and then at startup it looks for
	// your logging jars and config and routes ALL log statement to your
	// implementation of choice. We'll be using logback for this app
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 * <p>
	 * This creates our landing page for when users just go to our url instead
	 * of a specific page. You could just as easily done this with the
	 * WebMVCConfig class
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "welcome";
	}

}
