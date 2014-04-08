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

import org.home.petclinic2.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA specialization of the {@link VisitRepository} interface
 * <p>
 * You should really just go ahead and implement PagingAndSortingRepository
 * 
 * @author Michael Isvy
 * @since 15.1.2013
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
}
