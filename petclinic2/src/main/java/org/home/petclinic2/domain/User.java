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
package org.home.petclinic2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Represents a user in our system.
 * 
 * <p>
 * In a real system use {@link PasswordEncoder} to ensure the password is
 * secured properly. This demonstration does not address this due to time
 * restrictions.
 * </p>
 * 
 * @author Rob Winch
 */
@Entity
public class User extends Person {

	public User() {
	}

	public User(User user) {
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.email = user.email;
		this.password = user.password;
	}

	@Email(message = "Please provide a valid email address.")
	@NotEmpty(message = "Email is required.")
	@Column(unique = true, nullable = false)
	private String email;

	@NotEmpty(message = "Password is required.")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}