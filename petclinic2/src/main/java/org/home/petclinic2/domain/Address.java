package org.home.petclinic2.domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Address info
 * <p>
 * I went ahead and created a proper address object
 * 
 * @author phil
 * 
 */
@Entity
public class Address extends BaseEntity {

	@NotEmpty
	private String addressLine1;

	private String addressLine2;

	@NotEmpty
	private String City;

	@NotEmpty
	private String State;

	@NotEmpty
	private String postalCode;

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
