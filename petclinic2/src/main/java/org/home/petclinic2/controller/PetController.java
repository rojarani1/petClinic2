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
package org.home.petclinic2.controller;

import java.util.Collection;

import org.home.petclinic2.domain.Owner;
import org.home.petclinic2.domain.Pet;
import org.home.petclinic2.domain.PetType;
import org.home.petclinic2.service.ClinicService;
import org.home.petclinic2.validator.PetValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Pet controller facilitates all web requests for Pet entities
 * 
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class PetController {

	private static final Logger logger = LoggerFactory
			.getLogger(PetController.class);

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private PetValidator petValidator;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		logger.info("Init binding owner controller");
		dataBinder.setDisallowedFields("id");
		// Just showing you how to bind validators. This example does the
		// validation manually
		// dataBinder.addValidators(petValidator);
	}

	/**
	 * When you would like to include a collection of objects to be used in the
	 * jsp engine (say for a select or a radio button group) you can include
	 * them via a method annotated with ModelAttribute. The only downside to
	 * doing this is if you perform a redirect you will see some nasty looking
	 * query strings created
	 * 
	 * @return
	 */
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return clinicService.findPetTypes();
	}

	/**
	 * Retrieves all owners or allows for last name search
	 * 
	 * @param owner
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pet", method = RequestMethod.GET)
	public String processFindForm(Pet pet, BindingResult result, Model model) {

		// allow parameterless GET request for /pet to return all records
		Collection<Pet> results = null;
		if (StringUtils.isEmpty(pet.getName())) {
			// find all pets
			results = clinicService.findAllPets();
		} else {
			// find owners by last name
			results = clinicService.findPetByName(pet.getName());
		}

		if (results == null) {
			throw new IllegalStateException(
					"Results were null. This should not happen");
		} else if (results.size() < 1) {
			// no pets found
			result.rejectValue("name", "notFound", "not found");
			return "pet/findPet";
		} else if (results.size() > 1) {
			// multiple pets found
			// here we've specified how we want to reference the model object
			model.addAttribute("selections", results);
			return "pet/pets";
		} else {
			// 1 pet found
			pet = results.iterator().next();
			return "redirect:/pet/" + pet.getId();
		}
	}

	/**
	 * Retrieves view showing user a pet's details. Allows them to update
	 * 
	 * @param petId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/owner/*/pet/{petId}", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("petId") int petId, Model model) {
		Pet pet = clinicService.findPetById(petId);
		model.addAttribute(pet);
		return "pet/pet";
	}

	/**
	 * Handles validation and updates made to a pet
	 * 
	 * @param pet
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/owner/{ownerId}/pet/{petId}", method = RequestMethod.PUT)
	public String processUpdateForm(@PathVariable("ownerId") int ownerId,
			@ModelAttribute("pet") Pet pet, BindingResult result) {
		// retrieve owner so it isn't disassociated
		Owner owner = clinicService.findOwnerById(ownerId);
		pet.setOwner(owner);

		// not sure why we aren't using @Valid annotation here but it is a good
		// example of how to manually do validation
		petValidator.validate(pet, result);
		if (result.hasErrors()) {
			return "pet/pet";
		} else {
			clinicService.savePet(pet);
			return "redirect:/owner/{ownerId}";
		}
	}

	/**
	 * Retrieves view allowing user to add a new pet
	 * 
	 * @param ownerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/owner/{ownerId}/pet/new", method = RequestMethod.GET)
	public String initCreationForm(@PathVariable("ownerId") int ownerId,
			Model model) {
		Owner owner = clinicService.findOwnerById(ownerId);
		Pet pet = new Pet();
		pet.setOwner(owner);
		owner.getPets().add(pet);
		model.addAttribute(pet);
		return "pet/pet";
	}

	/**
	 * Handles validation and creation of new pet
	 * 
	 * @param ownerId
	 * @param pet
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/owner/{ownerId}/pet/new", method = RequestMethod.POST)
	public String processCreationForm(@PathVariable int ownerId,
			@ModelAttribute("pet") Pet pet, BindingResult result) {
		Owner owner = clinicService.findOwnerById(ownerId);
		pet.setOwner(owner);
		// not sure why we aren't using @Valid annotation here but it is a good
		// example of how to manually do validation
		petValidator.validate(pet, result);
		if (result.hasErrors()) {
			return "pet/pet";
		} else {
			clinicService.savePet(pet);
			return "redirect:/owner/{ownerId}";
		}
	}
}
