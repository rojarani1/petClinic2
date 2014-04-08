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
package org.home.petclinic2.service;

import java.util.Collection;
import java.util.List;

import org.home.petclinic2.domain.Owner;
import org.home.petclinic2.domain.Pet;
import org.home.petclinic2.domain.PetType;
import org.home.petclinic2.domain.Vet;
import org.home.petclinic2.domain.Visit;
import org.springframework.dao.DataAccessException;

/**
 * Combined service for all entities
 * <p>
 * This is alright for simple applications. Typically, you will create an
 * service interface and implementation for each entity. But you can group
 * services into a single interface and implementation where it makes sense
 * <p>
 * You don't have to use interfaces to define services but there are 2 reasons
 * why you should. 1) If you ever need to use AOP annotations like @Transactional
 * you now have to rely on CGLIB to create a proxy object. Spring uses aspectj
 * to wrap interfaces automatically for these. 2) Interfaces are not a means of
 * multiple inheritance like we were taught but really a means to write a
 * contract. If your implementation should ever need to be changes you just swap
 * out the implementation and not the interface. Sweet, sweet loosely coupled
 * Cohesion you always hear about!
 * <p>
 * Services should be over documented, implementations should only outline how
 * the contract was fulfilled. For example, an encryption service should define
 * what things are encrpted/decrypted and the implementation should be
 * EncryptionServiceSHAImpl or EncryptionServiceDESImpl. This tells you right
 * away what technique the service is actually being implemented by
 * 
 * @author Michael Isvy
 */
public interface ClinicService {

	/**
	 * Retrieves all pet types
	 * 
	 * @return
	 */
	public Collection<PetType> findPetTypes();

	/**
	 * Retrieves owner by id
	 * 
	 * @param id
	 * @return
	 */
	public Owner findOwnerById(int id);

	/**
	 * Retrieves pet by id
	 * 
	 * @param id
	 * @return
	 */
	public Pet findPetById(int id);

	/**
	 * Retrieves pet(s) by owner
	 * 
	 * @param owner
	 * @return
	 */
	public List<Pet> findPetsByOwner(Owner owner);

	/**
	 * Saves pet
	 * 
	 * @param pet
	 * @return
	 */
	public Pet savePet(Pet pet);

	/**
	 * Saves visit
	 * 
	 * @param visit
	 * @return
	 */
	public Visit saveVisit(Visit visit);

	/**
	 * Retrieves all Vets
	 * 
	 * @return
	 */
	public Collection<Vet> findVets();

	/**
	 * Saves owner (remember that owner has some cascading relationships)
	 * 
	 * @param owner
	 * @return
	 */
	public Owner saveOwner(Owner owner);

	/**
	 * Retrieves owners by last name (how this is implemented (e.g. repository
	 * using like query, solr, elastisearch) should be outlined in the services
	 * name and this should really be in its owner service...but this is a small
	 * app)
	 * 
	 * @param lastName
	 * @return
	 * @throws DataAccessException
	 */
	public Collection<Owner> findOwnerByLastName(String lastName);

	public Collection<Owner> findAllOwners();

	public Collection<Pet> findAllPets();

	public Collection<Pet> findPetByName(String name);

}
