package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.EstadoClienteHistorico;
import com.octagon.gestionclientes.service.dto.EstadoClienteHistoricoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EstadoClienteHistorico.
 */
public interface EstadoClienteHistoricoService {

    /**
     * Save a estadoClienteHistorico.
     *
     * @param estadoClienteHistoricoDTO the entity to save
     * @return the persisted entity
     */
    EstadoClienteHistoricoDTO save(EstadoClienteHistoricoDTO estadoClienteHistoricoDTO);

    /**
     * Get all the estadoClienteHistoricos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstadoClienteHistoricoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estadoClienteHistorico.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EstadoClienteHistoricoDTO> findOne(Long id);

    /**
     * Delete the "id" estadoClienteHistorico.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * CreateEstadoHistorico
     *
     * @param nuevoEstado
     * @return EstadoClienteHistorico
     */
    EstadoClienteHistorico createEstadoHistorico(Integer nuevoEstado, ClienteBase clienteBase);


}
