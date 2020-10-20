package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.service.dto.ParametroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Service Interface for managing Parametro.
 */
public interface ParametroService {

    /**
     * Save a parametro.
     *
     * @param parametroDTO the entity to save
     * @return the persisted entity
     */
    ParametroDTO save(ParametroDTO parametroDTO);

    /**
     * Get all the parametros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParametroDTO> findAll(Pageable pageable);


    /**
     * Get the "id" parametro.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParametroDTO> findOne(Long id);

    /**
     * Delete the "id" parametro.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the parametro by descripcionLarga.
     *
     * @param descripcionLarga the descripcionLarga of the entity
     * @return the entity
     */
    Optional<ParametroDTO> findByDescripcionLarga(String descripcionLarga);

    LinkedList<ParametroDTO> findAll();

    Object setParametro(Object objectDTO, String parametro, String descripcionLarga);

    /**
     * Retorn the parametro by codigoTabla and codigoItem if exists
     *
     * @param codigoTabla
     * @param codigoItem
     * @return ParametroDTO
     */
    ParametroDTO getParametroByCodigoTablaAndCodigoItem(Integer codigoTabla, Integer codigoItem);
}
