package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.domain.Parametro;
import com.octagon.gestionclientes.repository.ParametroRepository;
import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.dto.*;
import com.octagon.gestionclientes.service.mapper.ParametroMapper;
import com.octagon.gestionclientes.service.utils.Constants;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import com.octagon.gestionclientes.web.rest.errors.CustomParameterizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Parametro.
 */
@Service
@Transactional
public class ParametroServiceImpl implements ParametroService {

    private final Logger log = LoggerFactory.getLogger(ParametroServiceImpl.class);

    private final ParametroRepository parametroRepository;

    private final ParametroMapper parametroMapper;

    private static final String NOT_EXISTS_PARAMETRO = "Not exists Parametro: ";

    public ParametroServiceImpl(ParametroRepository parametroRepository, ParametroMapper parametroMapper) {
        this.parametroRepository = parametroRepository;
        this.parametroMapper = parametroMapper;
    }

    /**
     * Save a parametro.
     *
     * @param parametroDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParametroDTO save(ParametroDTO parametroDTO) {
        log.debug("Request to save Parametro : {}", parametroDTO);
        Parametro parametro = parametroMapper.toEntity(parametroDTO);
        parametro = parametroRepository.save(parametro);
        return parametroMapper.toDto(parametro);
    }

    /**
     * Get all the parametros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParametroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parametros");
        return parametroRepository.findAll(pageable)
            .map(parametroMapper::toDto);
    }


    /**
     * Get one parametro by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParametroDTO> findOne(Long id) {
        log.debug("Request to get Parametro : {}", id);
        return parametroRepository.findById(id)
            .map(parametroMapper::toDto);
    }

    /**
     * Delete the parametro by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parametro : {}", id);
        parametroRepository.deleteById(id);
    }


    /**
     * Get the parametro by descripcionLarga.
     *
     * @param descripcionLarga the descripcionLarga of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParametroDTO> findByDescripcionLarga(String descripcionLarga) {
        log.debug("Request to get Parametro : {}", descripcionLarga);
        return parametroRepository.findByDescripcionLarga(descripcionLarga)
            .map(parametroMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public LinkedList<ParametroDTO> findAll() {
        log.debug("Request to get all Parametros");
        return parametroRepository.findAll().stream()
            .map(parametroMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Setea el Pararametro de un Objeto(ClienteBaseDTO, Cliente CompletoDTO) solo recibiendo la DescripcionLarga de 'Parametro'
     *
     * @param objectDTO
     * @param parametro
     * @param descripcionLarga
     * @return Objeto(ClienteBaseDTO, Cliente CompletoDTO)
     */
    @Override
    public Object setParametro(Object objectDTO, String parametro, String descripcionLarga) {
        Optional<ParametroDTO> optionalParametroDTO = findByDescripcionLarga(descripcionLarga);
        if (!optionalParametroDTO.isPresent()) {
            throw new BadRequestAlertException(NOT_EXISTS_PARAMETRO, descripcionLarga, " descripcionLarga");
        }
        if (objectDTO instanceof ClienteBaseDTO) {
            switch (parametro) {
                case Constants.ESTADO_CLIENTE:
                    ((ClienteBaseDTO) objectDTO).setEstadoClienteId(optionalParametroDTO.get().getId());
                    ((ClienteBaseDTO) objectDTO).setCodTablaEstadoCliente(optionalParametroDTO.get().getCodigoTabla());
                    ((ClienteBaseDTO) objectDTO).setCodItemEstadoCliente(optionalParametroDTO.get().getCodigoItem());
                    break;
                case Constants.TIPO_CLIENTE:
                    ((ClienteBaseDTO) objectDTO).setTipoClienteId(optionalParametroDTO.get().getId());
                    ((ClienteBaseDTO) objectDTO).setCodTablaTipoCliente(optionalParametroDTO.get().getCodigoTabla());
                    ((ClienteBaseDTO) objectDTO).setCodItemTipoCliente(optionalParametroDTO.get().getCodigoItem());
                    break;
                default:
                    throw new CustomParameterizedException(NOT_EXISTS_PARAMETRO, parametro, descripcionLarga);
            }
        } else if (objectDTO instanceof ClienteCompletoDTO) {
            switch (parametro) {
                case Constants.TIPO_PERSONA:
                    ((ClienteCompletoDTO) objectDTO).setTipoPersonaId(optionalParametroDTO.get().getId());
                    ((ClienteCompletoDTO) objectDTO).setCodTablaTipoPersona(optionalParametroDTO.get().getCodigoTabla());
                    ((ClienteCompletoDTO) objectDTO).setCodItemTipoPersona(optionalParametroDTO.get().getCodigoItem());
                    break;
                case Constants.TIPO_GENERO:
                    ((ClienteCompletoDTO) objectDTO).setTipoGeneroId(optionalParametroDTO.get().getId());
                    ((ClienteCompletoDTO) objectDTO).setCodTablaGenero(optionalParametroDTO.get().getCodigoTabla());
                    ((ClienteCompletoDTO) objectDTO).setCodItemGenero(optionalParametroDTO.get().getCodigoItem());
                    break;
                default:
                    throw new CustomParameterizedException(NOT_EXISTS_PARAMETRO, parametro, descripcionLarga);
            }
        } else if (objectDTO instanceof NumeroTelefonoDTO) {
            switch (parametro) {
                case Constants.TIPO_TELEFONO:
                    ((NumeroTelefonoDTO) objectDTO).setTipoTelefonoId(optionalParametroDTO.get().getId());
                    ((NumeroTelefonoDTO) objectDTO).setCodTablaTipoTelefono(optionalParametroDTO.get().getCodigoTabla());
                    ((NumeroTelefonoDTO) objectDTO).setCodItemTipoTelefono(optionalParametroDTO.get().getCodigoItem());

                    break;
                default:
                    throw new CustomParameterizedException(NOT_EXISTS_PARAMETRO, parametro, descripcionLarga);
            }
        } else if (objectDTO instanceof DomicilioDTO) {
            switch (parametro) {
                case Constants.TIPO_DOMICILIO:
                    ((DomicilioDTO) objectDTO).setTipoDomicilioId(optionalParametroDTO.get().getId());
                    ((DomicilioDTO) objectDTO).setCodItemTipoDomicilio(optionalParametroDTO.get().getCodigoItem());
                    ((DomicilioDTO) objectDTO).setCodTablaTipoDomicilio(optionalParametroDTO.get().getCodigoTabla());

                    break;
                default:
                    throw new CustomParameterizedException(NOT_EXISTS_PARAMETRO, parametro, descripcionLarga);
            }
        }

        return objectDTO;

    }


    /**
     * Retorn the parametro by codigoTabla and codigoItem if exists
     *
     * @param codigoTabla
     * @param codigoItem
     * @return ParametroDTO
     */
    public ParametroDTO getParametroByCodigoTablaAndCodigoItem(Integer codigoTabla, Integer codigoItem) {
        Optional<Parametro> optionalParametro = parametroRepository.findByCodigoTablaAndCodigoItem(codigoTabla, codigoItem);
        if (!optionalParametro.isPresent()) {
            throw new CustomParameterizedException(NOT_EXISTS_PARAMETRO + "with the codigo Tabla ", codigoTabla.toString(), "and codigo item " + codigoItem.toString());

        }
        return parametroMapper.toDto(optionalParametro.get());
    }
}
