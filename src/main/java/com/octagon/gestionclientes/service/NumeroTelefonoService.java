package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.NumeroTelefono;
import com.octagon.gestionclientes.service.dto.NumeroTelefonoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing NumeroTelefono.
 */
public interface NumeroTelefonoService {

    /**
     * Save a numeroTelefono.
     *
     * @param numeroTelefonoDTO the entity to save
     * @return the persisted entity
     */
    NumeroTelefonoDTO save(NumeroTelefonoDTO numeroTelefonoDTO);

    /**
     * Get all the numeroTelefonos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<NumeroTelefonoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" numeroTelefono.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NumeroTelefonoDTO> findOne(Long id);

    /**
     * Delete the "id" numeroTelefono.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    //Page<NumeroTelefonoDTO> findByClienteBaseId(Long clienteBaseId);


    /**
     * Return the princial numeroTelfeno by clienteBase
     *
     * @param clienteBase NumeroTelefono
     * @return
     */
    NumeroTelefono getNumeroTelefonoPrincipal(ClienteBase clienteBase);
}
