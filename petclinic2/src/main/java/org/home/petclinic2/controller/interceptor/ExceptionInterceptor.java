package org.home.petclinic2.controller.interceptor;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.home.petclinic2.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Intercepts all uncaught exceptions and sends an email to support
 * <p>
 * Except for when the exception is that we couldn't send an email
 * 
 * @deprecated Was using this class to send errors in application to the support
 *             team, but I think a HandlerExceptionResolver will solve the issue
 *             better. It will still need to email support, but it will also
 *             send the user to a more friendly "Error has occurred" page
 *             instead of letting the container use its default
 * @author phil
 * 
 */
// @Component
@Deprecated()
public class ExceptionInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(ExceptionInterceptor.class);

	@Autowired
	private EmailService emailService;

	private List<String> supportEmailAddresses;

	/**
	 * Ensure that supportEmailAddresses was initialized
	 */
	// @PostConstruct is really helpful for ensuring that components are
	// initialized properly. You will notice that constructors are hardly relied
	// upon
	@PostConstruct
	public void initEmailSupportSimpleMappingExceptionResolver() {
		if (supportEmailAddresses == null || supportEmailAddresses.isEmpty()) {
			throw new IllegalStateException(
					"Must supply at least one email address so that we can email support when exceptions occur in the application");
		}
	}

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
				emailService.sendExceptionToSupport(ex, MDC.get("userName"),
						supportEmailAddresses);
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

	public List<String> getSupportEmailAddresses() {
		return supportEmailAddresses;
	}

	public void setSupportEmailAddresses(List<String> supportEmailAddresses) {
		this.supportEmailAddresses = supportEmailAddresses;
	}

}
