package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.client.Gateway;
import com.octagon.gestionclientes.client.MsCuentaTransaccion;
import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.repository.ClienteBaseRepository;
import com.octagon.gestionclientes.repository.ClienteCompletoRepository;
import com.octagon.gestionclientes.repository.DomicilioRepository;
import com.octagon.gestionclientes.repository.ParametroRepository;
import com.octagon.gestionclientes.service.ClienteBaseService;
import com.octagon.gestionclientes.service.ClienteCompletoService;
import com.octagon.gestionclientes.service.CorreoElectronicoService;
import com.octagon.gestionclientes.service.DomicilioService;
import com.octagon.gestionclientes.service.NumeroTelefonoService;
import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.PrestamoService;
import com.octagon.gestionclientes.service.dto.AltaCuentaDTO;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.dto.ClienteCompletoDTO;
import com.octagon.gestionclientes.service.dto.CorreoElectronicoDTO;
import com.octagon.gestionclientes.service.dto.DomicilioDTO;
import com.octagon.gestionclientes.service.dto.NumeroTelefonoDTO;
import com.octagon.gestionclientes.service.dto.PrestamoDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.dto.UserDTO;
import com.octagon.gestionclientes.service.dto.UserRespuestasDTO;
import com.octagon.gestionclientes.service.mapper.ClienteBaseMapper;
import com.octagon.gestionclientes.service.mapper.ClienteCompletoMapper;
import com.octagon.gestionclientes.service.mapper.DomicilioMapper;
import com.octagon.gestionclientes.service.mapper.NumeroTelefonoMapper;
import com.octagon.gestionclientes.service.utils.Constants;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.dto.ClienteBaseCompletoDTOExt;
import com.octagon.gestionclientes.web.dto.CuentaDTOExt;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Service Implementation for managing ClienteCompleto.
 */
@Service
@Transactional
public class ClienteCompletoServiceImpl implements ClienteCompletoService {

    private final Logger log = LoggerFactory.getLogger(ClienteCompletoServiceImpl.class);

    @Autowired
    private ClienteBaseService clienteBaseService;

    @Autowired
    private Gateway gateway;

    @Autowired
    private MsCuentaTransaccion msCuentaTransaccion;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private ClienteBaseRepository clienteBaseRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private DomicilioMapper domicilioMapper;

    private final ClienteCompletoRepository clienteCompletoRepository;

    private final ClienteCompletoMapper clienteCompletoMapper;

    @Autowired
    private ClienteBaseMapper clienteBaseMapper;

    @Autowired
    private NumeroTelefonoMapper numeroTelefonoMapper;

    @Autowired
    private NumeroTelefonoService numeroTelefonoService;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private CorreoElectronicoService correoElectronicoService;

    @Autowired
    private PrestamoService prestamoService;

    private Boolean telefonoPrincipal = true;

    private Boolean telefonoValidado = false;


    public ClienteCompletoServiceImpl(ClienteCompletoRepository clienteCompletoRepository, ClienteCompletoMapper clienteCompletoMapper) {
        this.clienteCompletoRepository = clienteCompletoRepository;
        this.clienteCompletoMapper = clienteCompletoMapper;
    }

    /**
     * Create Cliente Base and Cliente Complete
     *
     * @param clienteCompletoBaseDTO
     * @return
     */
    @Override
    public ClienteBaseCompletoDTOExt createClienteCompletoBaseCuenta(ClienteBaseCompletoDTOExt clienteCompletoBaseDTO) throws OctagonBusinessException {

        UserDTO userDTO = new UserDTO();
        String identificacion;
        String email;

        Boolean clienteBaseExistente = false;

        if (Constants.FISICA.equalsIgnoreCase(clienteCompletoBaseDTO.getTipoPersona()) || clienteCompletoBaseDTO.getTipoPersona() == null || clienteCompletoBaseDTO.getTipoPersona().isEmpty()) {
            /** Validate user exists */
            if (clienteCompletoBaseDTO.getDni() == null) {
                throw new BadRequestAlertException("Invalid userId", "User can not be ", "null");
            }
            userDTO = gateway.getUser(clienteCompletoBaseDTO.getDni());
            email = userDTO.getEmail();
            identificacion = userDTO.getLogin();
            if (userDTO == null) {
                throw new BadRequestAlertException("User Gateway", ClienteBase.class.getSimpleName(), "id unexists");
            }
        } else if (Constants.JURIDICA.equalsIgnoreCase(clienteCompletoBaseDTO.getTipoPersona())) {
            if (clienteCompletoBaseDTO.getCuilCuit() == null || clienteCompletoBaseDTO.getRazonSocial() == null || clienteCompletoBaseDTO.getRazonSocial().isEmpty() ||
                clienteCompletoBaseDTO.getNombreApoderado() == null || clienteCompletoBaseDTO.getNombreApoderado().isEmpty() ||
                clienteCompletoBaseDTO.getApellidoApoderado() == null || clienteCompletoBaseDTO.getApellidoApoderado().isEmpty() ||
                clienteCompletoBaseDTO.getCuitApoderado() == null) {
                throw new BadRequestAlertException("Informacion incorrecta", "User can not be ", "null");
            }
            identificacion = clienteCompletoBaseDTO.getCuilCuit().toString();
            email = clienteCompletoBaseDTO.getEmail();
            UserRespuestasDTO userRespuestasDTO = new UserRespuestasDTO();
            userDTO.setLogin(identificacion);
            userDTO.setCelular(clienteCompletoBaseDTO.getTelefono().get(0).getNumeroTelefono());
            userDTO.setEmail(email);
            userDTO.setPassword("admin");
            userDTO.setTipoPersona(Constants.JURIDICA);
            userRespuestasDTO.setUserDTO(userDTO);
            try {
                ResponseDTO<UserDTO> responseDTO = gateway.createUser(userRespuestasDTO);
                userDTO = responseDTO.getResponseData();
                log.debug("user created : {}", userDTO);

            } catch (OctagonBusinessException e) {
                throw e;
            } catch (Exception e) {
                log.debug("user created exception : {}", userDTO);
                //TODO handle exception
            }

        } else {
            throw new BadRequestAlertException("Invalid userId", "User can not be ", "null");
        }

        log.debug("Request to save ClienteBase and ClienteCompleto : {}", clienteCompletoBaseDTO);
        /** Create Cliente Base */
        ClienteBaseDTO clienteBaseDTO = new ClienteBaseDTO(identificacion);

        //Verifico si existe un cliente NN (Prospect)
        if (!clienteBaseService.existsClienteBaseRegistrado(identificacion)) {
            //Obtengo datos del cliente previamente dado de alta
            Optional<ClienteBaseDTO> clienteBaseDTOOptional = clienteBaseService.findByDni(identificacion);
            if (clienteBaseDTOOptional.isPresent()) {
                clienteBaseDTO = clienteBaseDTOOptional.get();
                clienteBaseExistente = true;
            }
        }

        if (Constants.FISICA.equalsIgnoreCase(clienteCompletoBaseDTO.getTipoPersona()) || clienteCompletoBaseDTO.getTipoPersona() == null || clienteCompletoBaseDTO.getTipoPersona().isEmpty()) {
            clienteBaseDTO.setNombre(clienteCompletoBaseDTO.getNombre());
            clienteBaseDTO.setApellido(clienteCompletoBaseDTO.getApellido());
            clienteBaseDTO.setOrigenRegistro(Constants.APP);
            clienteBaseDTO.setAlias(clienteCompletoBaseDTO.getAlias());
            /** Always when create a cliente is ACTIVO */
            clienteBaseDTO = (ClienteBaseDTO) parametroService.setParametro(clienteBaseDTO, Constants.ESTADO_CLIENTE, Constants.INACTIVO);
            clienteBaseDTO = (ClienteBaseDTO) parametroService.setParametro(clienteBaseDTO, Constants.TIPO_CLIENTE, Constants.REGISTRADO);
        } else {
            clienteBaseDTO.setOrigenRegistro(Constants.BO);
            /** Always when create a cliente is ACTIVO */
            clienteBaseDTO = (ClienteBaseDTO) parametroService.setParametro(clienteBaseDTO, Constants.ESTADO_CLIENTE, Constants.ACTIVO);
            clienteBaseDTO = (ClienteBaseDTO) parametroService.setParametro(clienteBaseDTO, Constants.TIPO_CLIENTE, Constants.REGISTRADO);
        }

        /**
         * Si no existe un cliente base, que puede ser un NN, se asume que es la primera vez que se esta registrando
         */
        if (!clienteBaseExistente)
            clienteBaseDTO.setId(clienteCompletoBaseDTO.getIdClienteBase());

        clienteBaseDTO.setIdUsuario(clienteCompletoBaseDTO.getIdUsuario());
        clienteBaseDTO.setFechaRegistro(Instant.now());
        clienteBaseDTO.setIdUsuario(userDTO.getId());
        clienteBaseDTO = clienteBaseService.save(clienteBaseDTO);

        /** Create Cliente Completo */
        ClienteCompletoDTO clienteCompletoDTO = new ClienteCompletoDTO();

        clienteCompletoDTO.setId(clienteCompletoBaseDTO.getIdClienteCompleto());
        clienteCompletoDTO.setClienteBaseId(clienteBaseDTO.getId());
        clienteCompletoDTO.setFechaRegistro(Instant.now());
        clienteCompletoDTO.setFechaNacimiento(clienteCompletoBaseDTO.getFechaNacimiento());
        clienteCompletoDTO.setInfoAdicional(clienteCompletoBaseDTO.getInfoAdicional());

        if (Constants.FISICA.equalsIgnoreCase(clienteCompletoBaseDTO.getTipoPersona())||clienteCompletoBaseDTO.getTipoPersona()==null||clienteCompletoBaseDTO.getTipoPersona().isEmpty()) {
            clienteCompletoDTO.setPep(clienteCompletoBaseDTO.getPep());
            clienteCompletoDTO.setNacionalidad(clienteCompletoBaseDTO.getNacionalidad());
            clienteCompletoDTO.setParticipaFidelizar(clienteCompletoBaseDTO.getParticipaFidelizar());
            clienteCompletoDTO = (ClienteCompletoDTO) parametroService.setParametro(clienteCompletoDTO, Constants.TIPO_PERSONA, Constants.FISICA);
            clienteCompletoDTO = (ClienteCompletoDTO) parametroService.setParametro(clienteCompletoDTO, Constants.TIPO_GENERO, clienteCompletoBaseDTO.getGenero());
        } else {
            clienteCompletoDTO.setRazonSocial(clienteCompletoBaseDTO.getRazonSocial());
            clienteCompletoDTO.setCuilCuit(clienteCompletoBaseDTO.getCuilCuit());
            clienteCompletoDTO.setNombreApoderado(clienteCompletoBaseDTO.getNombreApoderado());
            clienteCompletoDTO.setApellidoApoderado(clienteCompletoBaseDTO.getApellidoApoderado());
            clienteCompletoDTO.setCuitApoderado(clienteCompletoBaseDTO.getCuitApoderado());
            clienteCompletoDTO = (ClienteCompletoDTO) parametroService.setParametro(clienteCompletoDTO, Constants.TIPO_PERSONA, Constants.JURIDICA);
        }

        clienteCompletoDTO = save(clienteCompletoDTO);

        if (Constants.FISICA.equalsIgnoreCase(clienteCompletoBaseDTO.getTipoPersona()))
            callAltaCuentaMoneyFi(clienteBaseDTO.getId());
         else
            callAltaCuentaPrestamo(clienteBaseDTO.getId());


        //Si el cliente que se esta dando de alta posee prestamos aprobados y depositados, procedo a cambiar el estado de los prestamos a LIQUIDADO
        if (clienteBaseExistente)
            liquidPrestamo(clienteBaseDTO);

        Boolean telefonoPrinc = false;
        for (NumeroTelefonoDTO numeroTelefonoDTOs : clienteCompletoBaseDTO.getTelefono()) {
            NumeroTelefonoDTO numeroTelefonoDTO = new NumeroTelefonoDTO();
            numeroTelefonoDTO.setNumeroTelefono(numeroTelefonoDTOs.getNumeroTelefono());
            numeroTelefonoDTO.setClienteBaseId(clienteBaseDTO.getId());
            numeroTelefonoDTO.setPrincipal(numeroTelefonoDTOs.isPrincipal());
            if (numeroTelefonoDTOs.isPrincipal() != null && numeroTelefonoDTOs.isPrincipal() && telefonoPrinc) {
                numeroTelefonoDTO.setPrincipal(false);
            }
            if (clienteCompletoBaseDTO.getTelefono().get(clienteCompletoBaseDTO.getTelefono().size() - 1).equals(numeroTelefonoDTOs) && !telefonoPrinc) {
                numeroTelefonoDTO.setPrincipal(true);
            }
            numeroTelefonoDTO.setValidado(false);
            numeroTelefonoDTO = (NumeroTelefonoDTO) parametroService.setParametro(numeroTelefonoDTO, Constants.TIPO_TELEFONO, Constants.TELEFONO_MOVIL);

            numeroTelefonoService.save(numeroTelefonoDTO);
            telefonoPrinc = true;
        }

        Boolean domicilioPrinc = false;
        for (DomicilioDTO domicilioDTOs : clienteCompletoBaseDTO.getDomicilioDTO()) {
            DomicilioDTO domicilioDTO = new DomicilioDTO();
            domicilioDTO = (DomicilioDTO) parametroService.setParametro(domicilioDTOs, Constants.TIPO_DOMICILIO, Constants.DOMICILIO_REAL);
            domicilioDTO.setPais(domicilioDTOs.getPais());
            domicilioDTO.setProvincia(domicilioDTOs.getProvincia());
            domicilioDTO.setLocalidad(domicilioDTOs.getLocalidad());
            if (domicilioDTOs.getBarrio() != null) {
                domicilioDTO.setBarrio(domicilioDTOs.getBarrio());
            }
            domicilioDTO.setCalle(domicilioDTOs.getCalle());
            domicilioDTO.setNumeroCalle(domicilioDTOs.getNumeroCalle());
            domicilioDTO.setCodigoPostal(domicilioDTOs.getCodigoPostal());
            domicilioDTO.setClienteCompletoId(clienteCompletoDTO.getId());
            if (domicilioDTOs.isPrincipal() != null && domicilioDTOs.isPrincipal() && domicilioPrinc) {
                domicilioDTO.setPrincipal(false);
            }
            if (clienteCompletoBaseDTO.getInfoAdicional() != null && !clienteCompletoBaseDTO.getInfoAdicional().isEmpty()) {
                domicilioDTO.setObservacion(clienteCompletoBaseDTO.getInfoAdicional());
            }
            domicilioService.save(domicilioDTO);
            domicilioPrinc = true;
        }

        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        correoElectronicoDTO.setClienteBaseId(clienteBaseDTO.getId());
        correoElectronicoDTO.setCorreoElectronico(email);
        correoElectronicoDTO.setPrincipal(true);
        correoElectronicoDTO.setValidado(true);
        correoElectronicoService.save(correoElectronicoDTO);


        ClienteBaseCompletoDTOExt clienteBaseCompletoDTOExt = clienteCompletoMapper.toDto(clienteBaseDTO, clienteCompletoDTO, Constants.INACTIVO, Constants.REGISTRADO);

        return clienteBaseCompletoDTOExt;
    }

    /**
     * Save a clienteCompleto.
     *
     * @param clienteCompletoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClienteCompletoDTO save(ClienteCompletoDTO clienteCompletoDTO) {
        log.debug("Request to save ClienteCompleto : {}", clienteCompletoDTO);
        ClienteCompleto clienteCompleto = clienteCompletoMapper.toEntity(clienteCompletoDTO);
        clienteCompleto = clienteCompletoRepository.save(clienteCompleto);
        return clienteCompletoMapper.toDto(clienteCompleto);
    }

    /**
     * Get all the clienteCompletos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteCompletoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteCompletos");
        return clienteCompletoRepository.findAll(pageable)
            .map(clienteCompletoMapper::toDto);
    }


    /**
     * Get ResponseDTO with one clienteCompleto by id.
     *
     * @param id the id of the entity
     * @return ResponseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseDTO findOne(Long id) {
        log.debug("Request to get ClienteCompleto : {}", id);
        Optional<ClienteCompletoDTO> optionalClienteCompletoDTO = clienteCompletoRepository.findById(id)
            .map(clienteCompletoMapper::toDto);
        if (!optionalClienteCompletoDTO.isPresent()) {
            return new ResponseDTO(false, ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND.getMessage(), ResponseStatus.NOT_FOUND.getDescription());
        }
        ResponseDTO responseDTO = new ResponseDTO(true, ResponseStatus.OK.getCode(), ResponseStatus.OK.getMessage(), ResponseStatus.OK.getDescription());
        responseDTO.setResponseData(optionalClienteCompletoDTO.get());
        return responseDTO;
    }

    /**
     * Delete the clienteCompleto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClienteCompleto : {}", id);
        clienteCompletoRepository.deleteById(id);
    }

    private CuentaDTOExt callAltaCuentaMoneyFi(Long clienteId) {
        log.debug("Request to save callAltaCuentaPrincipal clienteId : {}", clienteId);
        AltaCuentaDTO altaCuentaDTO = new AltaCuentaDTO();
        altaCuentaDTO.setClienteId(clienteId);
        return msCuentaTransaccion.altaCuentaMoneyFi(altaCuentaDTO).getResponseData();
    }

    private CuentaDTOExt callAltaCuentaPrestamo(Long clienteId) {
        log.debug("Request to save callAltaCuentaPrincipal clienteId : {}", clienteId);
        AltaCuentaDTO altaCuentaDTO = new AltaCuentaDTO();
        altaCuentaDTO.setClienteId(clienteId);
        return msCuentaTransaccion.altaCuentaPrestamo(altaCuentaDTO).getResponseData();
    }


    private void liquidPrestamo(ClienteBaseDTO clienteBaseDTO){
        List<PrestamoDTO> prestamosPendientesLiquidar = null;
        try{
            prestamosPendientesLiquidar = prestamoService.getPrestamosAprobadosSupOctagonByDNI(clienteBaseDTO.getDni());
            if (!Objects.isNull(prestamosPendientesLiquidar) && prestamosPendientesLiquidar.size() > 0) {
                for (PrestamoDTO prestamoDTO : prestamosPendientesLiquidar) {
                    try{
                        prestamoService.liquidarPrestamo(prestamoDTO.getId());
                    }catch (OctagonBusinessException e){
                        //TODO - Ver como manejar estos tipos de excepciones durante el Onboarding. Supongo que lo ideal es NO INTERRUMPIR el proceso de Onboarding
                    }catch (Exception e){
                        //TODO - Ver como manejar estos tipos de excepciones durante el Onboarding. Supongo que lo ideal es NO INTERRUMPIR el proceso de Onboarding
                    }
                }
            }
        }catch (OctagonBusinessException e){
            //TODO
            log.error("Error OctagonBusinessException: {}", e);
        }catch (Exception e){
            //TODO
            log.error("Error Exception: {}", e);
        }
    }
}
