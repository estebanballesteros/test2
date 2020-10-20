package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.service.dto.CorreoElectronicoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CorreoElectronico.
 */
public interface CorreoElectronicoService {

    /**
     * Save a correoElectronico.
     *
     * @param correoElectronicoDTO the entity to save
     * @return the persisted entity
     */
    CorreoElectronicoDTO save(CorreoElectronicoDTO correoElectronicoDTO);

    /**
     * Get all the correoElectronicos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorreoElectronicoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" correoElectronico.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CorreoElectronicoDTO> findOne(Long id);

    /**
     * Delete the "id" correoElectronico.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

}
