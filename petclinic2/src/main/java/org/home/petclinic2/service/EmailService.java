package org.home.petclinic2.service;

import java.util.List;

/**
 * Email service
 * <p>
 * Implementations will be responsible for sending emails via SMTP, Gmail,
 * etc...
 * 
 * @author phil
 * 
 */
public interface EmailService {

	/**
	 * Sends exception to support team so that they can be pro-active to issues
	 * in the application
	 * <p>
	 * Here we are sending whomever is in the supportEmailAddress list, the
	 * exception and user that had the issue. The implementation should also
	 * take care of noting the server name (but that's not very apparent here)
	 * 
	 * @param ex
	 * @param userName
	 * @param supportEmailAddresses
	 */
	public void sendExceptionToSupport(Exception ex, String userName,
			List<String> supportEmailAddresses);

}
