package org.home.petclinic2.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.home.petclinic2.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Logging interceptor that adds user name to the log implementation's MDC
 * (allows us to log the user name)
 * <P>
 * 
 * <pre>
 * See: http://www.mkyong.com/spring-mvc/spring-mvc-handler-interceptors-example/
 * And: http://www.mkyong.com/spring-security/get-current-logged-in-username-in-spring-security/
 * </pre>
 * 
 * @author PMW1931
 * 
 */
@Component
public class LogUserInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(LogUserInterceptor.class);

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Nothing implemented
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// do nothing
	}

	/**
	 * Removes the user name from the MDC
	 * <p>
	 * We are done processing the user's request so we can remove the user's
	 * user name form the MDC
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// after performing actions remove user name from MDC
		MDC.remove("userName");
	}

	/**
	 * Adds user name to the MDC
	 * <p>
	 * We are about to process a user's request so we add the user's user name
	 * to the MDC so that it shows up in log statements.
	 * <p>
	 * Defaults to 'Unknown' when we don't know who the user is, could be an
	 * anonymous user (user not authenticated visiting one of our unsecured
	 * sections of the website)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserDetails userDetails = userDetailsService.getCurrentUser();

		// debugging to see what we got from the spring security context holder
		logger.debug("Spring's SecurityContextHolder.getContext() returned: "
				+ userDetails);

		String userName = null;
		if (userDetails == null) {
			// default to Unknown
			userName = "Unknown";
		} else {
			userName = userDetails.getUsername();
		}

		// add user name to MDC before performing actions
		MDC.put("userName", userName);

		return true;
	}

}
