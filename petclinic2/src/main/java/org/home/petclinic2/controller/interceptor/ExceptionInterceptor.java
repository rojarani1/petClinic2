package org.home.petclinic2.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.home.petclinic2.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Intercepts all uncaught exceptions and sends an email to support
 * <p>
 * Except for when the exception is that we couldn't send an email
 * 
 * @author phil
 * 
 */
@Component
public class ExceptionInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(ExceptionInterceptor.class);

	@Autowired
	private EmailService emailService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// if there were any exceptions, send email about error (unless
		// exception was that we couldn't send an email)
		if (ex != null) {
			// if the exception is that we couldn't send an email we don't want
			// to get in an infinite loop so we only log these sort of
			// exceptions
			if (!(ex instanceof UnableToSendNotificationException)) {
				emailService.sendExceptionToSupport(ex, MDC.get("userName"));
			} else {
				// when the exception is that we couldn't send an exception to
				// support we just log it
				logger.error("Failed to send exception to support", ex);
			}
		}

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// doing nothing
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// doing nothing
		return true;
	}

}
