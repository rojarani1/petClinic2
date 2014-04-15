package org.home.petclinic2.repository;

import org.home.petclinic2.domain.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PetType repository
 * 
 * @author phil
 * 
 */
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {

}
