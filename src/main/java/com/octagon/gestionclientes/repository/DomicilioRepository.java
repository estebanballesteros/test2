package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Domicilio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {

}
