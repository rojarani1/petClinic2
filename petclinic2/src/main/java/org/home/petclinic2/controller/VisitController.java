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

import javax.validation.Valid;

import org.home.petclinic2.domain.Pet;
import org.home.petclinic2.domain.Visit;
import org.home.petclinic2.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Visit controller
 * 
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class VisitController {

	@Autowired
	private ClinicService clinicService;

	/**
	 * Retrieves view for user to add a visit
	 * 
	 * @param petId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/owner/*/pet/{petId}/visit/new", method = RequestMethod.GET)
	public String initNewVisitForm(@PathVariable("petId") int petId, Model model) {
		Pet pet = clinicService.findPetById(petId);
		Visit visit = new Visit();
		visit.setPet(pet);
		model.addAttribute(visit);
		return "pet/visit";
	}

	/**
	 * Handles visit validation and saves the visit
	 * 
	 * @param visit
	 * @param result
	 * @param petId
	 * @return
	 */
	@RequestMapping(value = "/owner/{ownerId}/pet/{petId}/visit/new", method = RequestMethod.POST)
	public String processNewVisitForm(@Valid Visit visit, BindingResult result,
			@PathVariable int petId) {
		if (result.hasErrors()) {
			return "pet/visit";
		} else {
			Pet pet = clinicService.findPetById(petId);
			visit.setPet(pet);
			clinicService.saveVisit(visit);
			return "redirect:/owner/{ownerId}";
		}
	}

	/**
	 * Shows list of visits for pet
	 * <p>
	 * Doesn't look like it was ever implemented though
	 * 
	 * @param petId
	 * @return
	 */
	@RequestMapping(value = "/owner/*/pet/{petId}/visit", method = RequestMethod.GET)
	public ModelAndView showVisit(@PathVariable int petId) {
		ModelAndView mav = new ModelAndView("visitList");
		mav.addObject("visits", clinicService.findPetById(petId).getVisits());
		return mav;
	}

}
