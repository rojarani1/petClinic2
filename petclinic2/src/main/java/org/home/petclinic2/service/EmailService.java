package org.home.petclinic2.service;

public interface EmailService {

	public void sendExceptionToSupport(Exception ex, String userName);

}
