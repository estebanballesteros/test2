package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.domain.Domicilio;
import com.octagon.gestionclientes.repository.DomicilioRepository;
import com.octagon.gestionclientes.service.DomicilioService;
import com.octagon.gestionclientes.service.dto.DomicilioDTO;
import com.octagon.gestionclientes.service.mapper.DomicilioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Domicilio.
 */
@Service
@Transactional
public class DomicilioServiceImpl implements DomicilioService {

    private final Logger log = LoggerFactory.getLogger(DomicilioServiceImpl.class);

    private final DomicilioRepository domicilioRepository;

    private final DomicilioMapper domicilioMapper;

    public DomicilioServiceImpl(DomicilioRepository domicilioRepository, DomicilioMapper domicilioMapper) {
        this.domicilioRepository = domicilioRepository;
        this.domicilioMapper = domicilioMapper;
    }

    /**
     * Save a domicilio.
     *
     * @param domicilioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DomicilioDTO save(DomicilioDTO domicilioDTO) {
        log.debug("Request to save Domicilio : {}", domicilioDTO);
        Domicilio domicilio = domicilioMapper.toEntity(domicilioDTO);
        domicilio = domicilioRepository.save(domicilio);
        return domicilioMapper.toDto(domicilio);
    }

    /**
     * Get all the domicilios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DomicilioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Domicilios");
        return domicilioRepository.findAll(pageable)
            .map(domicilioMapper::toDto);
    }


    /**
     * Get one domicilio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DomicilioDTO> findOne(Long id) {
        log.debug("Request to get Domicilio : {}", id);
        return domicilioRepository.findById(id)
            .map(domicilioMapper::toDto);
    }

    /**
     * Delete the domicilio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Domicilio : {}", id);
        domicilioRepository.deleteById(id);
    }

    @Override
    public Domicilio getDomicilioPrincipal(ClienteCompleto clienteCompleto) {
        Optional<Domicilio> optionalDomicilio =
            clienteCompleto
                .getDomicilios()
                .stream()
                .filter(domicilio -> domicilio.isPrincipal())
                .findFirst();

        return optionalDomicilio.orElse(null);
    }


}
