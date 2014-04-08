package org.home.petclinic2.domain;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Person info
 * <p>
 * Reused in owner as well as vet
 * 
 * @author phil
 * 
 */
@MappedSuperclass
public class Person extends BaseEntity {
	// the @NotEmpty, @Digits, etc... are JSR-303 bean validation annotations.
	// You'll see them used in the controllers. They tell the controller what
	// can and cannot be null or what size and format data should be. This frees
	// us up from having to write validation logic
	@NotEmpty
	protected String firstName;

	@NotEmpty
	protected String lastName;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Address address;

	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String telephone;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
