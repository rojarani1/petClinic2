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
package org.home.petclinic2.validator;

import org.home.petclinic2.domain.Pet;
import org.home.petclinic2.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * This is another way of using JSR-303. You can do more complex validations
 * than @NotNull or @Size here. I'd recommend you still keep complex business
 * validation in the services though, or call these validators from your service
 * and not rely on the @Valid to do so simply because the @Valid is called
 * constantly and eventually you will want to do what are called validation
 * groups:
 * 
 * <pre>
 * http://digitaljoel.nerd-herders.com/2010/12/28/spring-mvc-and-jsr-303-validation-groups/
 * </pre>
 * 
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
@Component
public class PetValidator implements Validator {

	// I know it isn't used but I went ahead and showed you how to wire in
	// services to validators
	@Autowired
	private ClinicService clinicService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Pet.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// fail fast if not given a Pet object. This should have already been
		// done via the supports method but since the method is public it
		// doesn't hurt
		if (!(obj instanceof Pet)) {
			throw new IllegalArgumentException(
					"Pet validator was given object that is not of type Pet. obj obj: "
							+ obj);
		}

		Pet pet = (Pet) obj;

		// name validation
		if (!StringUtils.hasLength(pet.getName())) {
			errors.rejectValue("name", "required", "required");
		}

		// type validation
		if (pet.isNew() && pet.getType() == null) {
			errors.rejectValue("type", "required", "required");
		}

		// type validation
		if (pet.getBirthDate() == null) {
			errors.rejectValue("birthDate", "required", "required");
		}
	}

}
