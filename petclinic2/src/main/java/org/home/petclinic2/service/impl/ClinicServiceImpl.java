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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.home.petclinic2.domain.Address;
import org.home.petclinic2.domain.Owner;
import org.home.petclinic2.domain.Pet;
import org.home.petclinic2.domain.PetType;
import org.home.petclinic2.domain.Specialty;
import org.home.petclinic2.domain.Vet;
import org.home.petclinic2.domain.Visit;
import org.home.petclinic2.repository.OwnerRepository;
import org.home.petclinic2.repository.PetRepository;
import org.home.petclinic2.repository.PetTypeRepository;
import org.home.petclinic2.repository.SpecialtyRepository;
import org.home.petclinic2.repository.VetRepository;
import org.home.petclinic2.repository.VisitRepository;
import org.home.petclinic2.service.ClinicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClinicService implementation
 * <p>
 * Here all methods are Transactional, if this is the case you typically define
 * that at the class level. If some methods are transactional and others are
 * not, you do as you see below and mark individual methods as transactional
 * <p>
 * A Transactional annotation wraps the class or method into a single
 * transaction making it possible, for example, to perform multiple actions and
 * roll back separate actions (e.g. database updates, jms put or pulls, emails,
 * etc...) should one of the actions fail
 * <p>
 * The majority of logging should be done in the controller and services.
 * Repositories are interfaces now and you can't log there and you really
 * shouldn't log that low unless it is a very complex query
 * 
 * @author Michael Isvy
 */
@Service
public class ClinicServiceImpl implements ClinicService {
	private static final Logger logger = LoggerFactory
			.getLogger(ClinicServiceImpl.class);

	/**
	 * Not used in this example but we can utilize profiles in spring to have
	 * differing behavior. Here, I'm going to mock up that we want to load some
	 * sample data for local environments
	 */
	@Resource
	private Environment env;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private PetTypeRepository petTypeRepository;

	@Autowired
	private VetRepository vetRepository;

	@Autowired
	private SpecialtyRepository specialtyRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private VisitRepository visitRepository;

	/**
	 * PostConstruct is a means for doing initialization tasks. You want to do
	 * this in a PostConstruct for 2 reasons 1) It is guaranteed to be called
	 * only once and after the bean has been built (autowired), 2) Spring uses a
	 * lot of reflection and AOP so you can't just stick this sort of stuff into
	 * a constructor because there is no guarantee that it'll ever be called
	 */
	@PostConstruct
	public void initService() {
		// most times you don't need to state what class the logger is coming
		// from. We are used to doing for example
		// "ClinicServiceImpl is initializing" but the logger will spit out the
		// package and class name so it is redundant
		logger.info("Initializing service");

		// mock up database but only for local environments (this should be in a
		// service somewhere else where we can scan all of the active
		// environments though I've only ever had 1 set at a time
		// String activeEnv = env.getActiveProfiles()[0];
		// if (activeEnv.equals("local")) {
		if (true) {
			// load pet types if there aren't any
			if (petTypeRepository.findAll().isEmpty()) {
				// using a linked hashset so the order is retained
				Set<PetType> petTypes = new LinkedHashSet<PetType>();
				PetType dog = new PetType();
				dog.setName("Dog");
				petTypes.add(dog);

				PetType cat = new PetType();
				cat.setName("Cat");
				petTypes.add(cat);

				PetType bird = new PetType();
				bird.setName("Bird");
				petTypes.add(bird);

				PetType lizard = new PetType();
				lizard.setName("Lizard");
				petTypes.add(lizard);

				petTypeRepository.save(petTypes);
				logger.debug("There weren't any pet types so we added some");
			}

			// load specialities
			// couldn't figure out how to persist many to many with already
			// existing specialty
			/*
			 * if (specialtyRepository.findAll().isEmpty()) { Set<Specialty>
			 * specialities = new LinkedHashSet<Specialty>(); Specialty
			 * radiology = new Specialty(); radiology.setName("radiology");
			 * specialities.add(radiology);
			 * 
			 * Specialty surgery = new Specialty(); surgery.setName("surgery");
			 * specialities.add(surgery);
			 * 
			 * Specialty dentistry = new Specialty();
			 * dentistry.setName("dentistry"); specialities.add(dentistry);
			 * 
			 * specialtyRepository.save(specialities);
			 * logger.debug("There weren't any specialities so we added some");
			 * }
			 */

			// load vets
			if (vetRepository.findAll().isEmpty()) {
				Set<Vet> vets = new LinkedHashSet<Vet>();

				Vet vet1 = new Vet();
				vet1.setFirstName("Mark");
				vet1.setLastName("Smith");
				vet1.setTelephone("5555555555");
				Address address1 = new Address();
				address1.setAddressLine1("123 Easy St.");
				address1.setCity("Louisville");
				address1.setState("Kentucky");
				address1.setPostalCode("40220");
				vet1.setAddress(address1);
				Set<Specialty> specialties1 = new HashSet<Specialty>();
				Specialty specialty1 = new Specialty();
				specialty1.setName("Radiology");
				specialties1.add(specialty1);
				vet1.setSpecialties(specialties1);
				vets.add(vet1);

				Vet vet2 = new Vet();
				vet2.setFirstName("Sam");
				vet2.setLastName("Green");
				vet2.setTelephone("6666666666");
				Address address2 = new Address();
				address2.setAddressLine1("1 Main St.");
				address2.setCity("Lexington");
				address2.setState("Kentucky");
				address2.setPostalCode("55555");
				vet2.setAddress(address2);
				Set<Specialty> specialties2 = new HashSet<Specialty>();
				Specialty specialty2 = new Specialty();
				specialty2.setName("Surgery");
				specialties2.add(specialty2);
				vet2.setSpecialties(specialties2);
				vets.add(vet2);

				Vet vet3 = new Vet();
				vet3.setFirstName("Greg");
				vet3.setLastName("Stevens");
				vet3.setTelephone("7777777777");
				Address address3 = new Address();
				address3.setAddressLine1("3 Last Ave.");
				address3.setCity("Lexington");
				address3.setState("Kentucky");
				address3.setPostalCode("66666");
				vet3.setAddress(address3);
				Set<Specialty> specialties3 = new HashSet<Specialty>();
				Specialty specialty3 = new Specialty();
				specialty3.setName("Dentistry");
				specialties3.add(specialty3);
				vet3.setSpecialties(specialties3);
				vets.add(vet3);

				vetRepository.save(vets);
				logger.debug("There weren't any vets so we added some");
			}
		}
		// the first time this app is ran we won't have any owners, pets, vets,
		// specialties, pet types, etc... so this is a good of place as any to
		// load these. Probably want to check the profile to ensure that we are
		// in test and not in prod though

		logger.info("Initialized service");
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() {
		return petTypeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) {
		// load pets since they are lazy loaded we gain the ability to chose via
		// service methods when to load them and when to leave the empty
		Owner owner = ownerRepository.findOne(id);
		// ensure that pet collection has been initialized regardless of
		// owner having pets or not
		owner.setPets(new HashSet<Pet>());
		owner.getPets().addAll(petRepository.findByOwner(owner));
		return owner;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) {
		logger.info("Finding owner by last name: " + lastName);
		List<Owner> owners = ownerRepository
				.findByLastNameContainingIgnoreCase(lastName);

		// load pets since they are lazy loaded we gain the ability to chose via
		// service methods when to load them and when to leave the empty
		for (Owner owner : owners) {
			// ensure that pet collection has been initialized regardless of
			// owner having pets or not
			owner.setPets(new HashSet<Pet>());
			owner.getPets().addAll(petRepository.findByOwner(owner));
		}
		return owners;
	}

	@Override
	@Transactional
	public Owner saveOwner(Owner owner) {
		logger.info("Saving owner with id: " + owner.getId());
		return ownerRepository.save(owner);
	}

	@Override
	@Transactional
	public Visit saveVisit(Visit visit) {
		logger.info("Saving visit with id: " + visit.getId());
		return visitRepository.save(visit);
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findPetById(int id) {
		logger.info("Finding pet with id: " + id);
		return petRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pet> findPetsByOwner(Owner owner) {
		return petRepository.findByOwner(owner);
	}

	@Override
	@Transactional
	public Pet savePet(Pet pet) {
		logger.info("Saving pet with id: " + pet.getId());
		return petRepository.save(pet);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Vet> findVets() {
		logger.info("Retrieving all vets");
		Collection<Vet> vets = vetRepository.findAll();
		// you can sprinkle in debug statements with more details for more
		// complex logging information. You should really log all service method
		// entry and exits especially if they have more logic than just a
		// repository call
		logger.debug("Found " + vets.size());
		return vets;
	}

	@Override
	public Collection<Owner> findAllOwners() {
		List<Owner> owners = ownerRepository.findAll();
		// load pets since they are lazy loaded we gain the ability to chose via
		// service methods when to load them and when to leave the empty
		for (Owner owner : owners) {
			// ensure that pet collection has been initialized regardless of
			// owner having pets or not
			owner.setPets(new HashSet<Pet>());
			owner.getPets().addAll(petRepository.findByOwner(owner));
		}
		return owners;
	}

	@Override
	public Collection<Pet> findAllPets() {
		// not implemented just for show
		throw new UnsupportedOperationException("Not implemented");
		// return petRepository.findAll();
	}

	@Override
	public Collection<Pet> findPetByName(String name) {
		// not implemented just for show
		throw new UnsupportedOperationException("Not implemented");
		// return petRepository.findByName();
	}
}
