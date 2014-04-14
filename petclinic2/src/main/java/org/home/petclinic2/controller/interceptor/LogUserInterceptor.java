package org.home.petclinic2.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// do nothing
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// after performing actions remove user name from MDC
		MDC.remove("userName");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// using spring security, get the user logged in and add them to the
		// logger MDC

		// get user name
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		
		// debugging to see what we got from the spring security context holder
		logger.debug("Spring's SecurityContextHolder.getContext() returned: "
				+ auth);
		
		String userName = auth.getName();

		// if user name not in request nor session, default to Unknown
		if (StringUtils.isEmpty(userName)) {
			userName = "Unknown";
		}

		// add user name to MDC before performing actions
		MDC.put("userName", userName);

		return true;
	}

}