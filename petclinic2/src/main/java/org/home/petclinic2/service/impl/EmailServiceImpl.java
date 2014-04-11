package org.home.petclinic2.service.impl;

import org.home.petclinic2.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory
			.getLogger(EmailServiceImpl.class);

	@Override
	public void sendExceptionToSupport(Exception ex, String userName) {
		logger.info("Sending exception to support");

	}

}
