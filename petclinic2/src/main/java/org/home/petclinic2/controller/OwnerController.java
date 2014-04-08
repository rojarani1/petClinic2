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

import javax.validation.Valid;

import org.home.petclinic2.domain.Owner;
import org.home.petclinic2.service.ClinicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Owner controller facilitates all web requests for Owner entities
 * <p>
 * As much as possible, a controller should only be concerned with validation
 * (simple validation like missing or invalid fields, not business
 * requirements). Controllers are meant for responding to web requests by
 * transforming the request (http path, query string, forms, or bodies) then
 * using services to do meaningful things and return views with data bound into
 * them
 * 
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Phillip Williams
 */
@Controller
public class OwnerController {

	private static final Logger logger = LoggerFactory
			.getLogger(OwnerController.class);

	/**
	 * Autowired objects tell spring to go and find a service and plug it in so
	 * that I can use the service's (singleton) methods
	 */
	@Autowired
	private ClinicService clinicService;

	/**
	 * Init binders are for binding validators to controllers. You can also do
	 * other things but adding validators is the primary use of init binders.
	 * I'm not mentioning PropertyEditors, Converters, or Formatters because it
	 * is my belief that the direction of Spring that such things should be done
	 * centrally. Just know that you can use these spring concepts on an
	 * individual controller basis if you like repeating yourself or if you need
	 * one controller to behave differently than others
	 * 
	 * @param dataBinder
	 */
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		logger.info("Init binding owner controller");
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Retrieves all owners or allows for last name search
	 * 
	 * @param owner
	 * @param result
	 * @param model
	 * @return
	 */
	// be careful where you place the BindingResult, there are some rules about
	// where it must fall in the method signature. Typically, I place all of the
	// validated beans at the beginning and then follow each bean being
	// validated with the binding result
	@RequestMapping(value = "/owner", method = RequestMethod.GET)
	public String processFindForm(Owner owner, BindingResult result, Model model) {

		// allow parameterless GET request for /owner to return all records
		Collection<Owner> results = null;
		if (StringUtils.isEmpty(owner.getLastName())) {
			// find all owners
			results = clinicService.findAllOwners();
		} else {
			// find owners by last name
			results = clinicService.findOwnerByLastName(owner.getLastName());
		}

		// ensure that we received results, otherwise we have an issue on our
		// hands
		if (results == null) {
			throw new IllegalStateException(
					"Results were null. This should not happen");
		} else if (results.size() < 1) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owner/findOwner";
		} else if (results.size() > 1) {
			// multiple owners found
			// here we've specified how we want to reference the model object
			model.addAttribute("selections", results);
			return "owner/owners";
		} else {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owner/" + owner.getId();
		}
	}

	/**
	 * Retrieves view for searching owners
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/owner/find", method = RequestMethod.GET)
	public String initFindForm(Model model) {
		// stubs out an owner object
		model.addAttribute(new Owner());
		return "owner/findOwner";
	}

	/**
	 * Retrieves view for user to update an owner
	 * 
	 * @param ownerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/owner/{ownerId}", method = RequestMethod.GET)
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId,
			Model model) {
		// places the owner we are about to update (or just view) into the model
		// so that the jsp engine can plug in the values into the html
		Owner owner = clinicService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return "owner/owner";
	}

	/**
	 * Handles validation of the owner updates and saves the owner to the
	 * database
	 * 
	 * @param owner
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/owner/{ownerId}", method = RequestMethod.PUT)
	public String processUpdateOwnerForm(@Valid Owner owner,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "owner/owner";
		} else {
			clinicService.saveOwner(owner);
			// have to place the owner back on the model or use evil sessions to
			// keep it around
			model.addAttribute(owner);
			return "redirect:/owner/{ownerId}";
		}
	}

	/**
	 * Retrieves view for user to create an owner
	 * <p>
	 * I typically combine the new/edit screens into a single page unless they
	 * are vastly different. I also skip using a put and just use post
	 * 
	 * @param ownerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/owner/new", method = RequestMethod.GET)
	public String initCreateOwnerForm(Model model) {
		model.addAttribute(new Owner());
		return "owner/owner";
	}

	/**
	 * Handles validation of the owner creation and saves the owner to the
	 * database
	 * 
	 * @param owner
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/owner/new", method = RequestMethod.POST)
	public String processCreateOwnerForm(@Valid Owner owner,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "owner/owner";
		} else {
			clinicService.saveOwner(owner);
			model.addAttribute(owner);
			return "redirect:/owner/" + owner.getId();
		}
	}
}
