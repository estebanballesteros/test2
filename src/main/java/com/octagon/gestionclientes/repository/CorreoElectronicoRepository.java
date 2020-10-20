package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.CorreoElectronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CorreoElectronico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorreoElectronicoRepository extends JpaRepository<CorreoElectronico, Long> {
  
}
