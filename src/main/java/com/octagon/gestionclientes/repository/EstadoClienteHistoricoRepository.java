package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.EstadoClienteHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoClienteHistorico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoClienteHistoricoRepository extends JpaRepository<EstadoClienteHistorico, Long> {

}
