/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.home.petclinic2.service.impl;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Permission evaluator impl
 * <p>
 * When a user is trying to do something (call a method) that we have secured
 * this service checks that the user has permission
 * 
 * @author phil
 * 
 */
@Component
public class PermissionEvaluatorImpl implements PermissionEvaluator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission
	 * (org.springframework.security.core.Authentication, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		if (authentication == null) {
			return false;
		}
		// Message message = (Message) targetDomainObject;
		// if (message == null) {
		return true;
		// }
		// User currentUser = (User) authentication.getPrincipal();
		// return currentUser.getId().equals(message.getTo().getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission
	 * (org.springframework.security.core.Authentication, java.io.Serializable,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException();
	}

}
