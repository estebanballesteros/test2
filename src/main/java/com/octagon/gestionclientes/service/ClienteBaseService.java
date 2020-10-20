package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.service.dto.ActivarTelefonoDTO;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.dto.ClienteDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClienteBase.
 */
public interface ClienteBaseService {

    /**
     * Save a clienteBase.
     *
     * @param clienteBaseDTO the entity to save
     * @return the persisted entity
     */
    ClienteBaseDTO save(ClienteBaseDTO clienteBaseDTO);

    /**
     * Get all the clienteBases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClienteBaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clienteBase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClienteBaseDTO> findOne(Long id);

    /**
     * Delete the "id" clienteBase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * Return if the clients exists and if is Moneyfi(Registrado)
     *
     * @param dni
     * @return true or false
     */
    Boolean existsClienteBaseRegistrado(String dni);

    /**
     * Get the ClienteBase by IdUsuario
     *
     * @param idUsuario
     * @return ClienteBaseDTO
     */
    Optional<ClienteBaseDTO> findByIdUsuario(Long idUsuario);

    /**
     * Get the ClienteBase by dni
     * @param dni
     * @return ClienteBaseDTO
     */
    Optional<ClienteBaseDTO> findByDni(String dni);

    ClienteBaseDTO activarTelefono(ActivarTelefonoDTO activarTelefonoDTO);

    /**
     * Activate the cliente by id: activate cliente base, activate cuenta related, send sms to cliente.
     *
     * @param id
     * @return ResponseDTO
     */
    ClienteBaseDTO activarCliente(Long id) throws OctagonBusinessException;

    ResponseDTO<ClienteDTO> getOrCreateClienteBase(ClienteDTO clienteDTO);

    ResponseDTO<ClienteBaseDTO> getClienteBaseByDni(String dni);

    /**
     * Activate or Disactive Cliente Base
     *
     * @param id
     * @param activate
     * @return ClienteBase updated
     * @throws OctagonBusinessException
     */
    ClienteBase activateCliente(Long id, boolean activate) throws OctagonBusinessException;


    }
