package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.NumeroTelefono;
import com.octagon.gestionclientes.repository.NumeroTelefonoRepository;
import com.octagon.gestionclientes.service.NumeroTelefonoService;
import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.dto.NumeroTelefonoDTO;
import com.octagon.gestionclientes.service.mapper.NumeroTelefonoMapper;
import com.octagon.gestionclientes.service.mapper.ParametroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NumeroTelefono.
 */
@Service
@Transactional
public class NumeroTelefonoServiceImpl implements NumeroTelefonoService {

    private final Logger log = LoggerFactory.getLogger(NumeroTelefonoServiceImpl.class);

    private final NumeroTelefonoRepository numeroTelefonoRepository;

    private final NumeroTelefonoMapper numeroTelefonoMapper;

    private final ParametroMapper parametroMapper;

    private final ParametroService parametroService;

    public NumeroTelefonoServiceImpl(NumeroTelefonoRepository numeroTelefonoRepository, NumeroTelefonoMapper numeroTelefonoMapper, ParametroMapper parametroMapper, ParametroService parametroService) {
        this.numeroTelefonoRepository = numeroTelefonoRepository;
        this.numeroTelefonoMapper = numeroTelefonoMapper;
        this.parametroMapper = parametroMapper;
        this.parametroService = parametroService;
    }

    /**
     * Save a numeroTelefono.
     *
     * @param numeroTelefonoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NumeroTelefonoDTO save(NumeroTelefonoDTO numeroTelefonoDTO) {

        //TODO improve in ticket #12298
        log.debug("Request to save NumeroTelefono : {}", numeroTelefonoDTO);
        NumeroTelefono numeroTelefono = numeroTelefonoMapper.toEntity(numeroTelefonoDTO);
        numeroTelefono = numeroTelefonoRepository.save(numeroTelefono);
        return numeroTelefonoMapper.toDto(numeroTelefono);
    }

    /**
     * Get all the numeroTelefonos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NumeroTelefonoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NumeroTelefonos");
        return numeroTelefonoRepository.findAll(pageable)
            .map(numeroTelefonoMapper::toDto);
    }


    /**
     * Get one numeroTelefono by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NumeroTelefonoDTO> findOne(Long id) {
        log.debug("Request to get NumeroTelefono : {}", id);
        return numeroTelefonoRepository.findById(id)
            .map(numeroTelefonoMapper::toDto);
    }

    /**
     * Delete the numeroTelefono by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NumeroTelefono : {}", id);
        numeroTelefonoRepository.deleteById(id);
    }

    /**
     * Return the princial numeroTelfeno by clienteBase or null if is not exists
     *
     * @param clienteBase NumeroTelefono
     * @return NumeroTelefono
     */
    public NumeroTelefono getNumeroTelefonoPrincipal(ClienteBase clienteBase) {
        Optional<NumeroTelefono> optionalNumeroTelefono =
            clienteBase
                .getNumerosTelefonos()
                .stream()
                .filter(telefono -> telefono.isPrincipal())
                .findFirst();

        return optionalNumeroTelefono != null && optionalNumeroTelefono.isPresent() ? optionalNumeroTelefono.get() : null;

    }

}
