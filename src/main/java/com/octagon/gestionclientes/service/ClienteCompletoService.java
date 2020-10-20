package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.service.dto.ClienteCompletoDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.web.dto.ClienteBaseCompletoDTOExt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URISyntaxException;

/**
 * Service Interface for managing ClienteCompleto.
 */
public interface ClienteCompletoService {

    /**
     * Save a clienteCompleto.
     *
     * @param clienteCompletoDTO the entity to save
     * @return the persisted entity
     */
    ClienteCompletoDTO save(ClienteCompletoDTO clienteCompletoDTO);

    /**
     * Get all the clienteCompletos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClienteCompletoDTO> findAll(Pageable pageable);


    /**
     * Get ResponseDTO with one clienteCompleto by id.
     *
     * @param id the id of the entity
     * @return ResponseDTO
     */
    ResponseDTO findOne(Long id);

    /**
     * Delete the "id" clienteCompleto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Create Cliente Base and Cliente Complete
     * @param clienteCompletoBaseDTO ClienteCompletoDTO
     * @return ClienteBaseCompletoDTOExt
     */
    ClienteBaseCompletoDTOExt createClienteCompletoBaseCuenta(ClienteBaseCompletoDTOExt clienteCompletoBaseDTO) throws URISyntaxException, OctagonBusinessException;

}
