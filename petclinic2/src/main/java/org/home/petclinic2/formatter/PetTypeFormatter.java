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
package org.home.petclinic2.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.home.petclinic2.domain.PetType;
import org.home.petclinic2.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'.
 * Starting from Spring 3.0, Formatters have come as an improvement in
 * comparison to legacy PropertyEditors. See the following links for more
 * details: - The Spring ref doc:
 * http://static.springsource.org/spring/docs/current
 * /spring-framework-reference/html/validation.html#format-Formatter-SPI - A
 * nice blog entry from Gordon Dickens:
 * http://gordondickens.com/wordpress/2010/09
 * /30/using-spring-3-0-custom-type-converter/
 * <p/>
 * Also see how the bean 'conversionService' has been declared inside
 * /WEB-INF/mvc-core-config.xml
 * 
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 */
// @Component is another Spring stereotype just like @Repository, @Service, or
// @Controller only it is generic and can be used for anything. When spring sees
// this (it sees this because you included the package in the @ComponentScan), a
// singleton instance of this component is added to the spring context. You can
// of course create non-singleton components but you have to specify the bean's
// scope as "prototype". Look it up in the documentation or find examples on the
// web
@Component
public class PetTypeFormatter implements Formatter<PetType> {

	@Autowired
	private ClinicService clinicService;

	/**
	 * Converts PetType entity to a string
	 */
	@Override
	public String print(PetType petType, Locale locale) {
		return petType.getName();
	}

	/**
	 * Converts a String back into a PetType
	 */
	@Override
	public PetType parse(String text, Locale locale) throws ParseException {
		Collection<PetType> findPetTypes = clinicService.findPetTypes();
		for (PetType type : findPetTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
