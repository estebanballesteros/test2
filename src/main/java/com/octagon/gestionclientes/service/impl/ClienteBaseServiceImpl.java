package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.client.MsCuentaTransaccion;
import com.octagon.gestionclientes.client.MsMensajeria;
import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.EstadoClienteHistorico;
import com.octagon.gestionclientes.domain.NumeroTelefono;
import com.octagon.gestionclientes.domain.Parametro;
import com.octagon.gestionclientes.repository.ClienteBaseRepository;
import com.octagon.gestionclientes.repository.NumeroTelefonoRepository;
import com.octagon.gestionclientes.repository.ParametroRepository;
import com.octagon.gestionclientes.service.ClienteBaseService;
import com.octagon.gestionclientes.service.NumeroTelefonoService;
import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.dto.*;
import com.octagon.gestionclientes.service.mapper.ClienteBaseMapper;
import com.octagon.gestionclientes.service.mapper.EstadoClienteHistoricoMapper;
import com.octagon.gestionclientes.service.mapper.ParametroMapper;
import com.octagon.gestionclientes.service.utils.Constants;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import com.octagon.gestionclientes.web.rest.errors.CustomParameterizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing ClienteBase.
 */
@Service
@Transactional
public class ClienteBaseServiceImpl implements ClienteBaseService {

    private final Logger log = LoggerFactory.getLogger(ClienteBaseServiceImpl.class);

    private static final String CLIENTE_BASE = "ClienteBase";
    private static final String REQUEST_TO_GET_CLIENTE_BASE = "Request to get ClienteBase : {}";

    private final ClienteBaseRepository clienteBaseRepository;

    private final ClienteBaseMapper clienteBaseMapper;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private EstadoClienteHistoricoServiceImpl estadoClienteHistoricoService;

    @Autowired
    private MsCuentaTransaccion msCuentaTransaccion;

    @Autowired
    private MsMensajeria msMensajeria;

    @Autowired
    private ParametroMapper parametroMapper;

    @Autowired
    private NumeroTelefonoService numeroTelefonoService;

    @Autowired
    private NumeroTelefonoRepository numeroTelefonoRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private EstadoClienteHistoricoMapper estadoClienteHistoricoMapper;

    public ClienteBaseServiceImpl(ClienteBaseRepository clienteBaseRepository, ClienteBaseMapper clienteBaseMapper) {
        this.clienteBaseRepository = clienteBaseRepository;
        this.clienteBaseMapper = clienteBaseMapper;
    }

    /**
     * Save a clienteBase.
     *
     * @param clienteBaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClienteBaseDTO save(ClienteBaseDTO clienteBaseDTO) {
        log.debug("Request to save ClienteBase : {}", clienteBaseDTO);
        Optional<ClienteBase> optionalClienteBase = clienteBaseRepository.findByDni(clienteBaseDTO.getDni());
        ParametroDTO estadoClienteActivo;
        if (clienteBaseDTO.getId() == null && optionalClienteBase.isPresent()) {
            throw new BadRequestAlertException("A new clienteBase cannot already have this DNI", CLIENTE_BASE, "dni exists");
        }
        clienteBaseDTO.setFechaRegistro(Instant.now());
        ClienteBase clienteBase = clienteBaseMapper.toEntity(clienteBaseDTO);
        if(Constants.ATM.equalsIgnoreCase(clienteBaseDTO.getOrigenRegistro())) {
            /** Crete Historico Cliente */
            estadoClienteActivo = parametroService.getParametroByCodigoTablaAndCodigoItem(Constants.COD_TABLA_ESTADO_CLIENTE, Constants.COD_ITEM_ESTADO_CLIENTE_ACTIVO);
        } else {
            /** Crete Historico Cliente */
            estadoClienteActivo = parametroService.getParametroByCodigoTablaAndCodigoItem(Constants.COD_TABLA_ESTADO_CLIENTE, Constants.COD_ITEM_ESTADO_CLIENTE_INACTIVO);
        }
        EstadoClienteHistorico estadoClienteHistorico = estadoClienteHistoricoService.createEstadoHistorico(estadoClienteActivo.getCodigoItem(), clienteBase);
        clienteBase.addEstadosHistoricos(estadoClienteHistorico);
        /** Save the entitiy and return the DTO */
        clienteBase = clienteBaseRepository.save(clienteBase);
        return clienteBaseMapper.toDto(clienteBase);
    }

    /**
     * Get all the clienteBases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteBaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteBases");
        return clienteBaseRepository.findAll(pageable)
            .map(clienteBaseMapper::toDto);
    }


    /**
     * Get one clienteBase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteBaseDTO> findOne(Long id) {
        log.debug(REQUEST_TO_GET_CLIENTE_BASE, id);
        return clienteBaseRepository.findById(id)
            .map(clienteBaseMapper::toDto);
    }

    /**
     * Delete the clienteBase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClienteBase : {}", id);
        clienteBaseRepository.deleteById(id);
    }

    /**
     * Return if the clients exits and if is Moneyfi(Registrado)
     *
     * @param dni
     * @return true or false
     */
    @Override
    public Boolean existsClienteBaseRegistrado(String dni) {
        Optional<ClienteBase> optionalClienteBase = clienteBaseRepository.findByDni(dni);
        if (!optionalClienteBase.isPresent()) {
            return false;
        }
        if (optionalClienteBase.get().getTipoCliente() == null) {
            return false;
        }

        return (Constants.REGISTRADO.equals(optionalClienteBase.get().getTipoCliente().getDescripcionLarga()));

    }


    /**
     * Get the ClienteBase by IdUsuario
     *
     * @param idUsuario
     * @return ClienteBaseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteBaseDTO> findByIdUsuario(Long idUsuario) {
        log.debug(REQUEST_TO_GET_CLIENTE_BASE, idUsuario);
        return clienteBaseRepository.findByIdUsuario(idUsuario)
            .map(clienteBaseMapper::toDto);
    }

    /**
     * Get the ClienteBase by dni
     *
     * @param dni
     * @return ClienteBaseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteBaseDTO> findByDni(String dni) {
        log.debug(REQUEST_TO_GET_CLIENTE_BASE, dni);
        return clienteBaseRepository.findByDni(dni)
            .map(clienteBaseMapper::toDto);
    }

    @Override
    public ClienteBaseDTO activarTelefono(ActivarTelefonoDTO activarTelefonoDTO) {
        Optional<ClienteBase> clienteBaseOptional = clienteBaseRepository.findByIdUsuario(activarTelefonoDTO.getIdUsuario());
        if(!clienteBaseOptional.isPresent()){
            //El cliente no existe
            throw new CustomParameterizedException("Error al activar telefono","No existe el cliente.");
        }
        ClienteBase clienteBase = clienteBaseOptional.get();
        if(!clienteBase.getNumerosTelefonos().isEmpty()){
            //Valido si nos enviaron cual es el numero a validar sino, activo el que tenga ya que es la primer activacion.
            if (activarTelefonoDTO.getTelefono() != null){
                clienteBase.getNumerosTelefonos().forEach(numero -> {
                    if(numero.getNumeroTelefono() != null && numero.getNumeroTelefono().equals(activarTelefonoDTO.getTelefono())){
                        numero.setValidado(true);
                    }
                });
            }else{
                if(clienteBase.getNumerosTelefonos().size() > 1){
                    throw new CustomParameterizedException("Error al activar telefono","El cliente tiene mas de un telefono.");
                }
                clienteBase.getNumerosTelefonos().forEach(numeroTelefono -> numeroTelefono.setValidado(true));


            }
            clienteBaseRepository.save(clienteBase);
        }else{
            // el cliente no tiene telefono para activar.
            throw new CustomParameterizedException("Error al activar telefono","El cliente no tiene telefono registrado.");
        }

        return clienteBaseMapper.toDto(clienteBaseOptional.get());
    }



    /**
     * Activate the cliente by id: activate cliente base, activate cuenta related, send sms to cliente.
     *
     * @param id
     * @return ResponseDTO
     */
    @Override
    public ClienteBaseDTO activarCliente(Long id) throws OctagonBusinessException {

        ClienteBase clienteBase = activateCliente(id, true);
        log.info("Request to call msCuentaTransaccion 'activarCuenta' clienteId : {}", clienteBase.getId());

        CuentaDTO cuentaDTO = msCuentaTransaccion.activarCuenta(clienteBase.getId());
        if (cuentaDTO.getId() == null) {
            throw new OctagonBusinessException(ResponseStatus.ERROR_ACCOUNT_ACTIVATION);
        }

        /** Set sms to ClientBase */
        NumeroTelefono numeroTelefonoPrincipal = numeroTelefonoService.getNumeroTelefonoPrincipal(clienteBase);
        String notSendingSMS = "No se envio el sms,";
        String reponseDescription;
        if (numeroTelefonoPrincipal != null && numeroTelefonoPrincipal.getNumeroTelefono() != null) {
            MensajeDTO mensajeDTO = new MensajeDTO();
            mensajeDTO.setAccion(Constants.CUENTA_ACTIVA);
            mensajeDTO.setNumeroCelular(numeroTelefonoPrincipal.getNumeroTelefono());
            log.info("Request to call msMensajeria 'createMensaje' clienteId : {}", clienteBase.getId());
            try {
                MensajeDTO mensajeResponse = msMensajeria.createMensaje(mensajeDTO);
                reponseDescription = mensajeResponse.getEstado();
                log.info("Response to 'createMensaje' {}", reponseDescription);

            } catch (Exception e) {
                reponseDescription = e.getCause().getMessage();
                log.info("Response to 'createMensaje' with error {} {}", notSendingSMS, reponseDescription);

            }
        }

        ClienteBaseDTO clienteBaseDTO = clienteBaseMapper.toDto(clienteBase);
        return clienteBaseDTO;
    }


    /**
     * Activate or Disactive Cliente Base
     *
     * @param id
     * @param activate
     * @return ClienteBase updated
     * @throws OctagonBusinessException
     */
    public ClienteBase activateCliente(Long id, boolean activate) throws OctagonBusinessException {
        Optional<ClienteBase> optionalClienteBase = clienteBaseRepository.findById(id);
        if (!optionalClienteBase.isPresent()) {
            throw new OctagonBusinessException(ResponseStatus.ENTITY_NOT_FOUND, ResponseStatus.ENTITY_NOT_FOUND.getDescription().concat(CLIENTE_BASE));
        }
        ClienteBase clienteBase = optionalClienteBase.get();

        Optional<Parametro> optionalParametroEstado;
        if (activate) {
            optionalParametroEstado = parametroRepository.findByCodigoTablaAndDescripcionLarga(Constants.COD_TABLA_ESTADO_CLIENTE, Constants.ACTIVO);
            if (optionalParametroEstado.isPresent() && optionalParametroEstado.get().getCodigoItem().equals(clienteBase.getCodItemEstadoCliente())) {
                throw new OctagonBusinessException(ResponseStatus.ALREADY_ACTIVATED);
            }
        } else {
            optionalParametroEstado = parametroRepository.findByCodigoTablaAndDescripcionLarga(Constants.COD_TABLA_ESTADO_CLIENTE, Constants.INACTIVO);
            if (optionalParametroEstado.isPresent() && optionalParametroEstado.get().getCodigoItem().equals(clienteBase.getCodItemEstadoCliente())) {
                throw new OctagonBusinessException(ResponseStatus.ALREADY_INACTIVATED);
            }
        }
        Parametro parametroEstado = optionalParametroEstado.get();
        clienteBase.setEstadoCliente(parametroEstado);
        clienteBaseRepository.save(clienteBase);

        /** Set estadoClienteHistorico */
        EstadoClienteHistorico estadoClienteHistorico = estadoClienteHistoricoService.createEstadoHistorico(parametroEstado.getCodigoItem(), clienteBase);
        clienteBase.addEstadosHistoricos(estadoClienteHistorico);
        clienteBase = clienteBaseRepository.save(clienteBase);

        return clienteBase;
    }

    /**
     * Get Cliente Base if exists otherwise create one
     *
     * @param clienteDTO
     * @return ResponseDTO<ClienteDTO>
     */
    public ResponseDTO<ClienteDTO> getOrCreateClienteBase(ClienteDTO clienteDTO) {
        Optional<ClienteBaseDTO> optionalClienteBaseDTO = findByDni(clienteDTO.getDni());
        ResponseDTO responseDTO;
        if (optionalClienteBaseDTO.isPresent()) {
            responseDTO = new ResponseDTO(true, ResponseStatus.OK);
            responseDTO.setResponseData(new ClienteDTO(optionalClienteBaseDTO.get(), clienteDTO.getNumeroTelefono()));
            return responseDTO;
        }

        clienteDTO = saveClienteBaseAndNumeroTelefono(clienteDTO);
        responseDTO = new ResponseDTO(true, ResponseStatus.CREATED);
        responseDTO.setResponseData(clienteDTO);
        return responseDTO;

    }


    /**
     * Save Cliente Base(Alias and dni) and Número Teléfono
     *
     * @param clienteBaseNumeroTelefonoDTO
     * @return ClienteDTO
     */
    public ClienteDTO saveClienteBaseAndNumeroTelefono(ClienteDTO clienteBaseNumeroTelefonoDTO) {
        log.debug("Request to saveClienteBaseAndNumeroTelefono ClienteDTO : {}", clienteBaseNumeroTelefonoDTO);
        /** Create Cliente BaseDTO */
        ClienteBaseDTO clienteBaseDTO = new ClienteBaseDTO();
        clienteBaseDTO.setDni(clienteBaseNumeroTelefonoDTO.getDni());
        clienteBaseDTO.setAlias(clienteBaseNumeroTelefonoDTO.getAlias());
        clienteBaseDTO.setOrigenRegistro(Constants.ATM);
        /** Set Tipo Cliente: Always when create a cliente Base is NN(PROSPECT) */
        clienteBaseDTO = (ClienteBaseDTO) parametroService.setParametro(clienteBaseDTO, Constants.TIPO_CLIENTE, Constants.PROSPECT);
        /** Set Estado Cliente: Always when create a cliente is ACTIVO */
        parametroService.setParametro(clienteBaseDTO, Constants.ESTADO_CLIENTE, Constants.ACTIVO);
        clienteBaseDTO = save(clienteBaseDTO);
        /** Create NumeroTelefono */
        NumeroTelefono numeroTelefono = new NumeroTelefono();
        numeroTelefono.setNumeroTelefono(clienteBaseNumeroTelefonoDTO.getNumeroTelefono());
        numeroTelefono.setPrincipal(true);//? validate tel
        numeroTelefono.setClienteBase(clienteBaseMapper.toEntity(clienteBaseDTO));
        Parametro parametroTipoTelefono = parametroMapper.toEntity(parametroService.getParametroByCodigoTablaAndCodigoItem(Constants.COD_TABLA_TIPO_TELEFONO, Constants.COD_ITEM_TIPO_TELEFONO_MOVIL));
        numeroTelefono.setTipoTelefono(parametroTipoTelefono);
        numeroTelefono = numeroTelefonoRepository.save(numeroTelefono);
        /** Create ClienteDTO */
        clienteBaseNumeroTelefonoDTO = new ClienteDTO(clienteBaseDTO, numeroTelefono.getNumeroTelefono());
        return clienteBaseNumeroTelefonoDTO;

    }

    @Override
    public ResponseDTO<ClienteBaseDTO> getClienteBaseByDni(String dni) {

        if(Objects.isNull(dni)) {
            log.error("DNI is NULL");
            return new ResponseDTO<>(false,
                ResponseStatus.INVALID_DNI.getCode(),
                ResponseStatus.INVALID_DNI.getMessage(),
                ResponseStatus.INVALID_DNI.getDescription());

        }

        Optional<ClienteBaseDTO> optionalClienteBaseDTO = this.findByDni(dni);

        if (!optionalClienteBaseDTO.isPresent()) {
            log.error("Client with DNI {} not found", dni);
            return new ResponseDTO<>(false,
                ResponseStatus.NOT_FOUND.getCode(),
                ResponseStatus.NOT_FOUND.getMessage(),
                ResponseStatus.NOT_FOUND.getDescription());

        }

        ClienteBaseDTO clienteBaseDTO = optionalClienteBaseDTO.get();

        ResponseDTO responseDTO = new ResponseDTO<>(true,
            ResponseStatus.OK.getCode(),
            ResponseStatus.OK.getMessage(),
            ResponseStatus.OK.getDescription());

        log.info("Returning cliente with dni {}: {}", dni, clienteBaseDTO);
        responseDTO.setResponseData(clienteBaseDTO);

        return responseDTO;
    }


}
