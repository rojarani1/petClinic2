package org.home.petclinic2.repository;

import org.home.petclinic2.domain.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Specialty repository
 * 
 * @author phil
 * 
 */
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

	public Specialty findByName(String name);
}
