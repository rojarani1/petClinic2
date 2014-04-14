package org.home.petclinic2.service;

import java.util.List;

public interface EmailService {

	public void sendExceptionToSupport(Exception ex, String userName,
			List<String> supportEmailAddresses);

}
