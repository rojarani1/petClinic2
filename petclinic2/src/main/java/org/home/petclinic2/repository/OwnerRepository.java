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
package org.home.petclinic2.repository;

import java.util.List;

import org.home.petclinic2.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA specialization of the {@link OwnerRepository} interface
 * <p>
 * You should really just go ahead and implement PagingAndSortingRepository
 * <p>
 * This interface will create several more functions at run time. Just
 * familiarize yourself with them by going to the documentation or in a
 * controller you can play around with them using type ahead (ctrl+space). The
 * real benefits of spring data are that they free you up from having to write
 * DAOs, they make writing custom queries easier because you just use findByxxx
 * where xxx is the column. If you need to do anything more complex (i.e. sub
 * query) then just user the @Query annotation. When you implement spring
 * security you can also perform auditing (who updated and when) very easily
 * 
 * @author Michael Isvy
 * @since 15.1.2013
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	// At first, I couldn't get the findByLastNameLike query to work so I
	// overrided it using the actual query. But that too was case sensitive
	// @Query("SELECT owner FROM Owner owner WHERE owner.lastName like :lastName%")
	// public List<Owner> findByLastNameLike(@Param("lastName") String
	// lastName);

	/**
	 * Finds Owners using case insensitive search for owners whose last name is
	 * like the given value
	 * 
	 * @param lastName
	 * @return
	 */
	public List<Owner> findByLastNameContainingIgnoreCase(String lastName);
}
