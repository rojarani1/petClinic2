package org.home.petclinic2.exception;

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
