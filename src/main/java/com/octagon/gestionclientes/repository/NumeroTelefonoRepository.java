package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.NumeroTelefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Spring Data  repository for the NumeroTelefono entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumeroTelefonoRepository extends JpaRepository<NumeroTelefono, Long> {

}
