package org.home.petclinic2.controller.exception.resolver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.home.petclinic2.service.EmailService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Component
public class EmailSupportSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	@Autowired
	private EmailService emailService;

	private List<String> supportEmailAddresses;

	/**
	 * Sends exception to support team
	 * 
	 * @param e
	 */
	public void emailExceptionToSupportTeam(Exception e) {
		// ensure that we have email addresses to send and exception email to
		if (supportEmailAddresses == null || supportEmailAddresses.isEmpty()) {
			throw new IllegalStateException(
					"Must supply at least one email address so that we can email support when exceptions occur in the application");
		}

		try {
			// if the exception is that we couldn't send an email we don't want
			// to get in an infinite loop so we only log these sort of
			// exceptions
			// if (!(e instanceof UnableToSendNotificationException)) {
			emailService.sendExceptionToSupport(e, MDC.get("userName"),
					supportEmailAddresses);
			// } else {
			// when the exception is that we couldn't send an exception to
			// support we just log it
			// logger.error("Failed to send exception to support", e);
			// }
		} catch (Exception ex) {
			// if any exception occur, just swallow them and log the exception
			logger.error("Failed to send exception to support", e);
		}
	}

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		// log the exception to the log file just like normally in
		// SimpleMappingExceptionResolver
		super.logException(ex, request);
		// but also send an email to support letting them know
		emailExceptionToSupportTeam(ex);
	}

	public List<String> getSupportEmailAddresses() {
		return supportEmailAddresses;
	}

	public void setSupportEmailAddresses(List<String> supportEmailAddresses) {
		this.supportEmailAddresses = supportEmailAddresses;
	}

}
