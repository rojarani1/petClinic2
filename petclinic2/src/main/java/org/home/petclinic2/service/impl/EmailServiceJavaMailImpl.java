package org.home.petclinic2.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.home.petclinic2.exception.UnableToSendExceptionEmail;
import org.home.petclinic2.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Email service JavaMail implementation
 * 
 * @author phil
 * 
 */
@Service
public class EmailServiceJavaMailImpl implements EmailService {
	private static final Logger logger = LoggerFactory
			.getLogger(EmailServiceJavaMailImpl.class);

	// I'm using the full blown JavaMailSender so that we can do fancy emails
	private JavaMailSenderImpl mailSender;

	// pulls in the smtp host name via properties value
	@Value("${email.host}")
	private String host;

	@Value("${email.from}")
	private String from;

	@Value("${app.name}")
	private String applicationName;

	// I used to set the email distro directly in this service but I'm trying a
	// new approach in which we send the list of emails to use in the function
	// parameters. It may still be a good idea to swap them out when in test
	// mode (See the testDistro variable)
	// @Value("${email.support.distro}")
	// private String[] supportDistro;

	// @Value("${export.email.test.distro}")
	// private String[] testDistro;

	@PostConstruct
	public void initService() {
		logger.debug("Initializing JavaMail service. Service: " + toString());

		// fail fast
		if (StringUtils.isEmpty(host) || StringUtils.isEmpty(from)
				|| StringUtils.isEmpty(applicationName)) {
			throw new IllegalStateException(
					"Unable to initialize email service because we are missing the email host name, from parameter, or the application name");
		}

		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
	}

	@Override
	public void sendExceptionToSupport(Exception ex, String userName,
			List<String> supportEmailAddresses) {
		try {
			// create multipart message/message helper
			MimeMessageHelper helper = new MimeMessageHelper(
					mailSender.createMimeMessage(), true);

			// set from field
			helper.setFrom(new InternetAddress(from));

			// set to field
			// I was checking if I needed to swap the email To property when we
			// are in test mode but I've left it out in this impl. Leaving
			// behind the remnants so I remember how I did it
			// if (!testMode) {
			helper.setTo(convertToInternetAddressArray(supportEmailAddresses));
			// } else {
			// helper.setTo(convertToInternetAddressArray(testDistro));
			// }

			// set subject field
			helper.setSubject(applicationName + " - Exception occurred");

			// build text of message
			StringWriter errors = new StringWriter();
			// add user name and time of exception
			errors.append("User: ");
			errors.append(userName);
			errors.append("\r\n");
			errors.append("Time: ");
			errors.append(Calendar.getInstance().getTime().toString());
			errors.append("\r\n");
			errors.append("Server: ");
			errors.append(getLocalHost().getHostName());
			errors.append("\r\n");
			errors.append("Stacktrace:");
			errors.append("\r\n");
			// add stack trace
			ex.printStackTrace(new PrintWriter(errors));

			// add to body of email
			helper.setText(errors.toString());

			// send message
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			// have to throw special exception so that we don't keep going in
			// loops trying to send messages about exceptions thrown while
			// attempting to send messages to support
			throw new UnableToSendExceptionEmail(
					"Exception occurred while attempting to send an exception email to support",
					e);
		}
	}

	/**
	 * Checks if we should use test accounts for to email addresses
	 * <P>
	 * In certain test regions, we want the emails to go to a select group of
	 * users instead of the users they are testing as. This indicator should be
	 * set to true when we should use the testDistro of users when we send out
	 * emails
	 */
	// I was checking the active profile to see if we were in prod or a test
	// region. If we were in a test region (not prod or training) I'd send the
	// email to the real user account. Other wise, I would swap the list with
	// test accounts
	// private boolean useTestAccounts() {
	// if (!KFBApplicationContextInitializer.isProdActiveProfile()
	// && !KFBApplicationContextInitializer.isTrainingActiveProfile()) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	/**
	 * Retrieves InetAddress for localhost
	 * <P>
	 * Can be used to identify the server name and IP address
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	private InetAddress getLocalHost() throws UnknownHostException {
		InetAddress a = InetAddress.getLocalHost();
		return a;
	}

	/**
	 * Converts String array into Address array
	 * 
	 * @param addresses
	 * @return
	 * @throws AddressException
	 */
	private InternetAddress[] convertToInternetAddressArray(
			List<String> addresses) throws AddressException {
		InternetAddress[] internetAddresses = new InternetAddress[addresses
				.size()];
		for (int i = 0; i < addresses.size(); i++) {
			internetAddresses[i] = new InternetAddress(addresses.get(i));
		}

		return internetAddresses;
	}

	@Override
	public String toString() {
		return "EmailServiceImpl [mailSender=" + mailSender + ", host=" + host
				+ ", from=" + from + ", applicationName=" + applicationName
				+ "]";
	}

}
