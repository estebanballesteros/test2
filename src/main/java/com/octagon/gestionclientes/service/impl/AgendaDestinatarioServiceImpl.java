package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.domain.AgendaDestinatario;
import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.repository.AgendaDestinatarioRepository;
import com.octagon.gestionclientes.service.AgendaDestinatarioService;
import com.octagon.gestionclientes.service.ClienteBaseService;
import com.octagon.gestionclientes.service.dto.AgendaDestinatarioDTO;
import com.octagon.gestionclientes.service.mapper.AgendaDestinatarioMapper;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing AgendaDestinatario.
 */
@Service
@Transactional
public class AgendaDestinatarioServiceImpl implements AgendaDestinatarioService {

    private final Logger log = LoggerFactory.getLogger(AgendaDestinatarioServiceImpl.class);

    private final AgendaDestinatarioRepository agendaDestinatarioRepository;

    @Autowired
    private ClienteBaseService clienteBaseService;

    private static final String USER_AGENDA_REGISTRO = "User";

    private final AgendaDestinatarioMapper agendaDestinatarioMapper;

    public AgendaDestinatarioServiceImpl(AgendaDestinatarioRepository agendaDestinatarioRepository, AgendaDestinatarioMapper agendaDestinatarioMapper) {
        this.agendaDestinatarioRepository = agendaDestinatarioRepository;
        this.agendaDestinatarioMapper = agendaDestinatarioMapper;
    }

    /**
     * Save a agendaDestinatario.
     *
     * @param agendaDestinatarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgendaDestinatarioDTO save(AgendaDestinatarioDTO agendaDestinatarioDTO) throws OctagonBusinessException {
        log.debug("Request to save AgendaDestinatario : {}", agendaDestinatarioDTO);
        AgendaDestinatario agendaDestinatario = agendaDestinatarioMapper.toEntity(agendaDestinatarioDTO);
        //Solo verifico si existe si es es un POST
        if (agendaDestinatario.getId() == null) {
            List<AgendaDestinatario> list = agendaDestinatarioRepository.findByDniAndClienteBase(agendaDestinatario.getDni(), agendaDestinatario.getClienteBase());
            if (!list.isEmpty()) {
                throw new OctagonBusinessException(ResponseStatus.DESTINATARIO_EXIST);
            }
        }
        agendaDestinatario = agendaDestinatarioRepository.save(agendaDestinatario);
        return agendaDestinatarioMapper.toDto(agendaDestinatario);
    }

    /**
     * Get all the agendaDestinatarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgendaDestinatarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgendaDestinatarios");
        return agendaDestinatarioRepository.findAll(pageable)
            .map(agendaDestinatarioMapper::toDto);
    }


    /**
     * Get one agendaDestinatario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AgendaDestinatarioDTO> findOne(Long id) {
        log.debug("Request to get AgendaDestinatario : {}", id);
        return agendaDestinatarioRepository.findById(id)
            .map(agendaDestinatarioMapper::toDto);
    }
    /**
     * Get the ClienteBase by dni
     *
     * @param dni
     * @return ClienteBaseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public List<AgendaDestinatario> findByDniAndClienteBase(Long dni, ClienteBase clienteBase) {
        return agendaDestinatarioRepository.findByDniAndClienteBase(dni, clienteBase);
    }

    /**
     * Delete the agendaDestinatario by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgendaDestinatario : {}", id);
        agendaDestinatarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendaDestinatarioDTO> findByclienteBaseId(Long clienteBase, Pageable pageable) {
        log.debug("Request to get AgendaDestinatario : {}", clienteBase);
       return agendaDestinatarioRepository.findByclienteBaseId(clienteBase, pageable)
        .map(agendaDestinatarioMapper::toDto);

    }

}
