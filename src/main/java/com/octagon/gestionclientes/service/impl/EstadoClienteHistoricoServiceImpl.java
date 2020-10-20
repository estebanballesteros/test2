package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.EstadoClienteHistorico;
import com.octagon.gestionclientes.repository.EstadoClienteHistoricoRepository;
import com.octagon.gestionclientes.service.EstadoClienteHistoricoService;
import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.dto.EstadoClienteHistoricoDTO;
import com.octagon.gestionclientes.service.dto.ParametroDTO;
import com.octagon.gestionclientes.service.mapper.EstadoClienteHistoricoMapper;
import com.octagon.gestionclientes.service.mapper.ParametroMapper;
import com.octagon.gestionclientes.service.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing EstadoClienteHistorico.
 */
@Service
@Transactional
public class EstadoClienteHistoricoServiceImpl implements EstadoClienteHistoricoService {

    private final Logger log = LoggerFactory.getLogger(EstadoClienteHistoricoServiceImpl.class);

    private final EstadoClienteHistoricoRepository estadoClienteHistoricoRepository;

    private final EstadoClienteHistoricoMapper estadoClienteHistoricoMapper;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private ParametroMapper parametroMapper;

    public EstadoClienteHistoricoServiceImpl(EstadoClienteHistoricoRepository estadoClienteHistoricoRepository, EstadoClienteHistoricoMapper estadoClienteHistoricoMapper) {
        this.estadoClienteHistoricoRepository = estadoClienteHistoricoRepository;
        this.estadoClienteHistoricoMapper = estadoClienteHistoricoMapper;
    }

    /**
     * Save a estadoClienteHistorico.
     *
     * @param estadoClienteHistoricoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstadoClienteHistoricoDTO save(EstadoClienteHistoricoDTO estadoClienteHistoricoDTO) {
        log.debug("Request to save EstadoClienteHistorico : {}", estadoClienteHistoricoDTO);
        EstadoClienteHistorico estadoClienteHistorico = estadoClienteHistoricoMapper.toEntity(estadoClienteHistoricoDTO);
        estadoClienteHistorico = estadoClienteHistoricoRepository.save(estadoClienteHistorico);
        return estadoClienteHistoricoMapper.toDto(estadoClienteHistorico);
    }

    /**
     * Get all the estadoClienteHistoricos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstadoClienteHistoricoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoClienteHistoricos");
        return estadoClienteHistoricoRepository.findAll(pageable)
            .map(estadoClienteHistoricoMapper::toDto);
    }


    /**
     * Get one estadoClienteHistorico by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoClienteHistoricoDTO> findOne(Long id) {
        log.debug("Request to get EstadoClienteHistorico : {}", id);
        return estadoClienteHistoricoRepository.findById(id)
            .map(estadoClienteHistoricoMapper::toDto);
    }

    /**
     * Delete the estadoClienteHistorico by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoClienteHistorico : {}", id);
        estadoClienteHistoricoRepository.deleteById(id);
    }

    @Override
    public EstadoClienteHistorico createEstadoHistorico(Integer nuevoEstado, ClienteBase clienteBase) {
        ParametroDTO parametroDTO = parametroService.getParametroByCodigoTablaAndCodigoItem(Constants.COD_TABLA_ESTADO_CLIENTE, nuevoEstado);
        EstadoClienteHistorico estadoClienteHistorico = new EstadoClienteHistorico();
        return estadoClienteHistorico
            .fecha(Instant.now())
            .codTablaEstadoCliente(parametroDTO.getCodigoTabla())
            .codItemEstadoCliente(parametroDTO.getCodigoItem())
            .estadoCliente(parametroMapper.toEntity(parametroDTO))
            .clienteBase(clienteBase);

    }


}
