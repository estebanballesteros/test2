package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.domain.AgendaDestinatario;
import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.service.dto.AgendaDestinatarioDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AgendaDestinatario.
 */
public interface AgendaDestinatarioService {

    /**
     * Save a agendaDestinatario.
     *
     * @param agendaDestinatarioDTO the entity to save
     * @return the persisted entity
     */
    AgendaDestinatarioDTO save(AgendaDestinatarioDTO agendaDestinatarioDTO) throws OctagonBusinessException;

    /**
     * Get all the agendaDestinatarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgendaDestinatarioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" agendaDestinatario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AgendaDestinatarioDTO> findOne(Long id);

    /**
     * Get the ClienteBase by dni
     * @param dni, clienteBase
     * @return ClienteBaseDTO
     */
    List<AgendaDestinatario> findByDniAndClienteBase(Long dni, ClienteBase clienteBase);


    /**
     * Delete the "id" agendaDestinatario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


//    /**
//     * Get the "clienteBaseId" agendaDestinatario.
//     *
//     * @param clienteBaseId the id of the entity
//     * @return the entity
//     */
//    List<AgendaDestinatario> findByclienteBaseId(Long clienteBaseId);


    /**
    * @param pageable the pagination information
    * @return the list of entities
     */
    Page<AgendaDestinatarioDTO> findByclienteBaseId(Long clienteBase, Pageable pageable);

}
