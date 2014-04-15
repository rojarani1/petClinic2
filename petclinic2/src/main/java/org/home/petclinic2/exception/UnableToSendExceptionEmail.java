package org.home.petclinic2.exception;

/**
 * Exception indicating that an email was not successfully sent
 * <p>
 * Our EmailSupportSimpleMappingExceptionResolver can throw exceptions when we
 * are trying to inform support of another exception in our application. When
 * this happens we need to not send them another email that could potentially
 * cause another exception
 * 
 * @author phil
 * 
 */
@SuppressWarnings("serial")
public class UnableToSendExceptionEmail extends RuntimeException {

	public UnableToSendExceptionEmail() {
		super();
	}

	public UnableToSendExceptionEmail(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToSendExceptionEmail(String message) {
		super(message);
	}

}
