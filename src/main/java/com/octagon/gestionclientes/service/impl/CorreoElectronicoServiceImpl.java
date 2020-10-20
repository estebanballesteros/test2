package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.domain.CorreoElectronico;
import com.octagon.gestionclientes.repository.CorreoElectronicoRepository;
import com.octagon.gestionclientes.service.CorreoElectronicoService;
import com.octagon.gestionclientes.service.dto.CorreoElectronicoDTO;
import com.octagon.gestionclientes.service.mapper.CorreoElectronicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CorreoElectronico.
 */
@Service
@Transactional
public class CorreoElectronicoServiceImpl implements CorreoElectronicoService {

    private final Logger log = LoggerFactory.getLogger(CorreoElectronicoServiceImpl.class);

    private final CorreoElectronicoRepository correoElectronicoRepository;

    private final CorreoElectronicoMapper correoElectronicoMapper;

    public CorreoElectronicoServiceImpl(CorreoElectronicoRepository correoElectronicoRepository, CorreoElectronicoMapper correoElectronicoMapper) {
        this.correoElectronicoRepository = correoElectronicoRepository;
        this.correoElectronicoMapper = correoElectronicoMapper;
    }

    /**
     * Save a correoElectronico.
     *
     * @param correoElectronicoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorreoElectronicoDTO save(CorreoElectronicoDTO correoElectronicoDTO) {
        log.debug("Request to save CorreoElectronico : {}", correoElectronicoDTO);
        CorreoElectronico correoElectronico = correoElectronicoMapper.toEntity(correoElectronicoDTO);
        correoElectronico = correoElectronicoRepository.save(correoElectronico);
        return correoElectronicoMapper.toDto(correoElectronico);
    }

    /**
     * Get all the correoElectronicos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorreoElectronicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorreoElectronicos");
        return correoElectronicoRepository.findAll(pageable)
            .map(correoElectronicoMapper::toDto);
    }


    /**
     * Get one correoElectronico by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorreoElectronicoDTO> findOne(Long id) {
        log.debug("Request to get CorreoElectronico : {}", id);
        return correoElectronicoRepository.findById(id)
            .map(correoElectronicoMapper::toDto);
    }

    /**
     * Delete the correoElectronico by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorreoElectronico : {}", id);
        correoElectronicoRepository.deleteById(id);
    }
}
