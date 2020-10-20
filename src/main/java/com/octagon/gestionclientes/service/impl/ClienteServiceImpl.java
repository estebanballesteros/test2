package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.client.Gateway;
import com.octagon.gestionclientes.client.MsMensajeria;
import com.octagon.gestionclientes.client.MsPrestamos;
import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.domain.CorreoElectronico;
import com.octagon.gestionclientes.domain.Domicilio;
import com.octagon.gestionclientes.domain.EstadoClienteHistorico;
import com.octagon.gestionclientes.domain.NumeroTelefono;
import com.octagon.gestionclientes.domain.Parametro;
import com.octagon.gestionclientes.repository.ClienteBaseRepository;
import com.octagon.gestionclientes.repository.ClienteCompletoRepository;
import com.octagon.gestionclientes.repository.ParametroRepository;
import com.octagon.gestionclientes.service.ClienteBaseService;
import com.octagon.gestionclientes.service.ClienteService;
import com.octagon.gestionclientes.service.GatewayService;
import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.dto.FiltroClienteDTO;
import com.octagon.gestionclientes.service.dto.ParametroDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.dto.UserDTO;
import com.octagon.gestionclientes.service.dto.UserPortalPagosDTO;
import com.octagon.gestionclientes.service.dto.UsuarioFinancieraDTO;
import com.octagon.gestionclientes.service.mapper.ClienteBaseMapper;
import com.octagon.gestionclientes.service.utils.ApiException;
import com.octagon.gestionclientes.service.utils.Constants;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.dto.GetClienteDTO;
import com.octagon.gestionclientes.web.dto.RespuestaDTO;
import com.octagon.gestionclientes.web.dto.UpdateClienteDTO;
import com.octagon.gestionclientes.web.dto.UpdateCorreoElectronicoDTO;
import com.octagon.gestionclientes.web.dto.UpdateDomicilioDTO;
import com.octagon.gestionclientes.web.dto.UpdateNumeroTelefonoDTO;
import com.octagon.gestionclientes.web.dto.UpdateParametroDTO;
import com.octagon.gestionclientes.web.dto.UserClienteBaseDTO;
import com.octagon.gestionclientes.web.mapper.ClienteMapper;
import com.octagon.gestionclientes.web.mapper.UpdateCorreoElectronicoMapper;
import com.octagon.gestionclientes.web.mapper.UpdateDomicilioMapper;
import com.octagon.gestionclientes.web.mapper.UpdateNumeroTelefonoMapper;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing ClienteCompleto.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteCompletoRepository clienteCompletoRepository;
    @Autowired
    private ParametroRepository parametroRepository;
    @Autowired
    private UpdateDomicilioMapper domicilioMapper;
    @Autowired
    private UpdateCorreoElectronicoMapper correoMapper;
    @Autowired
    private ClienteMapper clienteMapper;
    @Autowired
    private UpdateNumeroTelefonoMapper numeroTelefonoMapper;
    @Autowired
    private GatewayService gatewayService;
    @Autowired
    private Gateway gateway;
    @Autowired
    MsPrestamos msPrestamos;
    @Autowired
    ClienteBaseService clienteBaseService;
    @Autowired
    ClienteBaseRepository clienteBaseRepository;

    @Autowired
    MsMensajeria msMensajeria;

    @Autowired
    ParametroService parametroService;

    @Autowired
    ClienteBaseMapper clienteBaseMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<GetClienteDTO> findOneClienteCompleto(Long clienteCompletoId) {
        log.debug("Request to get ClienteCompleto : {}", clienteCompletoId);
        return clienteCompletoRepository.findById(clienteCompletoId)
            .map(clienteMapper::toDto);
    }

    @Override
    public Page<GetClienteDTO> findClientesByTipoPersona(FiltroClienteDTO filtroClienteDTO, Pageable pageable) {
        log.debug("Request to get all Clientes");

        List<Parametro> tiposPersona = this.getTiposPersona(filtroClienteDTO);

        return clienteCompletoRepository.findAllByTipoPersona(tiposPersona, pageable)
            .map(clienteMapper::toDto);
    }

    /**
     * Obtengo los usuarios(user, clienteBase) de la financiera logeada
     *
     * @return List<UserClienteBaseDTO>
     * @throws OctagonBusinessException
     */
    @Override
    public List<UserClienteBaseDTO> getUsuariosFinanaciera() throws OctagonBusinessException {
        log.debug("Request to get Usuarios Finanaciera");
        ClienteBaseDTO clienteBaseFinanciera =  getFinancieraLogged();

        /**
         * Busco los Ids de los usuarios de esa financiera
         *
         */
        ResponseDTO<List<UsuarioFinancieraDTO>> msPrestamosRsesponseDTO;
        List<UsuarioFinancieraDTO> usuarioFinancieraDTOList;
        try {
            msPrestamosRsesponseDTO = msPrestamos.getUsuariosFinanciera(clienteBaseFinanciera.getId());
            usuarioFinancieraDTOList = msPrestamosRsesponseDTO.getResponseData();
        } catch (ApiException e) {
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        } catch (Exception e) {
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_MS_PRESTAMO);
        }

        if (Boolean.FALSE.equals(msPrestamosRsesponseDTO.getStatus())) {
            throw new OctagonBusinessException(msPrestamosRsesponseDTO.getCode(), msPrestamosRsesponseDTO.getMessage(), msPrestamosRsesponseDTO.getDescription());
        }

        if (usuarioFinancieraDTOList.isEmpty()) {
            throw new OctagonBusinessException(ResponseStatus.NOT_FOUND_CLIENTES);
        }

        /**
         * Obtengo los usuarios de la financiera
         */
        Set<UserClienteBaseDTO> userClienteBaseDTOSList = new HashSet<>();

        for (UsuarioFinancieraDTO usuarioFinancieraDTO : usuarioFinancieraDTOList) {
            ResponseDTO<UserDTO> msGatewayResponseDTO;
            try {
                msGatewayResponseDTO = gateway.getUserById(usuarioFinancieraDTO.getUsuarioId());
            } catch (ApiException e) {
                throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
            } catch (Exception e) {
                throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_GATEWAY);
            }
            if (Boolean.FALSE.equals(msGatewayResponseDTO.getStatus())) {
                throw new OctagonBusinessException(msPrestamosRsesponseDTO.getCode(), msPrestamosRsesponseDTO.getMessage(), msPrestamosRsesponseDTO.getDescription());
            }
            UserDTO userDTO = msGatewayResponseDTO.getResponseData();
            Optional<ClienteBaseDTO> optionalClienteBaseDTO = clienteBaseService.findByIdUsuario(userDTO.getId());
            ClienteBaseDTO clienteBaseDTO = null;
            if (optionalClienteBaseDTO.isPresent()) {
                clienteBaseDTO = optionalClienteBaseDTO.get();
            }
            UserClienteBaseDTO userClienteBaseDTO = new UserClienteBaseDTO(userDTO,clienteBaseDTO);
            userClienteBaseDTOSList.add(userClienteBaseDTO);
        }
        return new ArrayList<>(userClienteBaseDTOSList);


    }


    @Override
    public RespuestaDTO updateCliente(UpdateClienteDTO updateClienteDTO) {
        log.debug("Request to update Cliente : {}", updateClienteDTO);

        // ClienteCompleto
        ClienteCompleto clienteCompleto = clienteCompletoRepository
            .findById(updateClienteDTO.getId()).orElse(null);
        clienteCompleto.setPep(updateClienteDTO.isPep());
        clienteCompleto.setParticipaFidelizar(updateClienteDTO.isParticipaFidelizar());
        clienteCompleto.setInfoAdicional(updateClienteDTO.getInfoAdicional());
        clienteCompleto.setRazonSocial(updateClienteDTO.getRazonSocial());
        clienteCompleto.setCuilCuit(updateClienteDTO.getCuilCuit());
        clienteCompleto.setFechaNacimiento(updateClienteDTO.getFechaNacimiento());
        clienteCompleto.setNacionalidad(updateClienteDTO.getNacionalidad());
        UpdateParametroDTO tipoPersona = updateClienteDTO.getTipoPersona();
        clienteCompleto.setCodTablaTipoPersona(tipoPersona.getCodigoTabla());
        clienteCompleto.setCodItemTipoPersona(tipoPersona.getCodigoItem());
        Parametro tipoPersonaParametro = parametroRepository
            .findByCodigoTablaAndDescripcionCorta(Constants.COD_TABLA_TIPO_PERSONA,
                tipoPersona.getDescripcionCorta()).orElse(null);
        clienteCompleto.setTipoPersona(tipoPersonaParametro);
        UpdateParametroDTO genero = updateClienteDTO.getGenero();
        clienteCompleto.setCodTablaGenero(genero.getCodigoTabla());
        clienteCompleto.setCodItemGenero(genero.getCodigoItem());
        Parametro generoParametro = parametroRepository
            .findByCodigoTablaAndDescripcionCorta(Constants.COD_TABLA_GENERO,
                genero.getDescripcionCorta()).orElse(new Parametro());
        clienteCompleto.setGenero(generoParametro);

        // ClienteBase
        ClienteBase clienteBase = clienteCompleto.getClienteBase();
        clienteBase.setNombre(updateClienteDTO.getNombre());
        clienteBase.setApellido(updateClienteDTO.getApellido());
        clienteBase.setAlias(updateClienteDTO.getAlias());

        UpdateParametroDTO tipoCliente = updateClienteDTO.getTipoCliente();
        clienteBase.setCodTablaTipoCliente(tipoCliente.getCodigoTabla());
        clienteBase.setCodItemTipoCliente(tipoCliente.getCodigoItem());
        Parametro tipoClienteParametro = parametroRepository
            .findByCodigoTablaAndDescripcionCorta(Constants.COD_TABLA_TIPO_CLIENTE,
                tipoCliente.getDescripcionCorta()).orElse(new Parametro());
        clienteBase.setTipoCliente(tipoClienteParametro);

        // Domicilios
        Set<UpdateDomicilioDTO> domiciliosDTO = updateClienteDTO.getDomicilios();
        clienteCompleto.getDomicilios().clear();
        for (UpdateDomicilioDTO domicilioDTO : domiciliosDTO) {
            Domicilio domicilio = domicilioMapper.toEntity(domicilioDTO);
            Parametro tipoDomicilioParametro = parametroRepository
                .findByCodigoTablaAndDescripcionCorta(Constants.COD_TABLA_TIPO_DOMICILIO,
                    domicilioDTO.getTipoDomicilio().getDescripcionCorta()).orElse(new Parametro());
            domicilio.setTipoDomicilio(tipoDomicilioParametro);
            domicilio.setCodTablaTipoDomicilio(tipoDomicilioParametro.getCodigoTabla());
            domicilio.setCodItemTipoDomicilio(tipoDomicilioParametro.getCodigoItem());
            clienteCompleto.addDomicilios(domicilio);
        }

        // Actualiza Correos de cliente y usuario
        Set<UpdateCorreoElectronicoDTO> correosDTO = updateClienteDTO.getCorreos();
        clienteBase.getCorreosElectronicos().clear();
        UserDTO userDTO = gateway.getUser(clienteBase.getDni());
        if (userDTO == null) {
            throw new BadRequestAlertException("User Gateway", ClienteBase.class.getSimpleName(), "id unexists");
        }
        for (UpdateCorreoElectronicoDTO correoDTO : correosDTO) {
            CorreoElectronico correo = correoMapper.toEntity(correoDTO);
            userDTO.setEmail(correo.getCorreoElectronico());
            clienteBase.addCorreosElectronicos(correo);
        }

        // Actualiza Telefonos de cliente y usuario
        Set<UpdateNumeroTelefonoDTO> telefonosDTO = updateClienteDTO.getTelefonos();
        clienteBase.getNumerosTelefonos().clear();
        for (UpdateNumeroTelefonoDTO telefonoDTO : telefonosDTO) {
            NumeroTelefono telefono = numeroTelefonoMapper.toEntity(telefonoDTO);
            Parametro tipoTelefonoParametro = parametroRepository
                .findByCodigoTablaAndDescripcionCorta(Constants.COD_TABLA_TIPO_TELEFONO,
                    telefonoDTO.getTipoTelefono().getDescripcionCorta()).orElse(new Parametro());
            telefono.setTipoTelefono(tipoTelefonoParametro);
            telefono.setCodTablaTipoTelefono(tipoTelefonoParametro.getCodigoTabla());
            telefono.setCodItemTipoTelefono(tipoTelefonoParametro.getCodigoItem());
            clienteBase.addNumerosTelefonos(telefono);
            if (Boolean.TRUE.equals(telefono.isValidado())) {
                userDTO.setCelular(telefono.getNumeroTelefono());
            }
        }

        gateway.updateUser(userDTO);

        // Estados
        UpdateParametroDTO estadoNuevo = updateClienteDTO.getEstado();
        Parametro estadoParametro = parametroRepository
            .findByCodigoTablaAndDescripcionCorta(Constants.COD_TABLA_ESTADO_CLIENTE,
                estadoNuevo.getDescripcionCorta()).orElse(new Parametro());
        Parametro estadoActual = clienteBase.getEstadoCliente();
        if (!estadoParametro.getId().equals(estadoActual.getId())) {
            clienteBase.setCodTablaEstadoCliente(estadoParametro.getCodigoTabla());
            clienteBase.setCodItemEstadoCliente(estadoParametro.getCodigoItem());
            clienteBase.setEstadoCliente(estadoParametro);

            EstadoClienteHistorico estadoHistorico = new EstadoClienteHistorico();
            estadoHistorico.setFecha(Instant.now());
            estadoHistorico.setCodTablaEstadoCliente(estadoParametro.getCodigoTabla());
            estadoHistorico.setCodItemEstadoCliente(estadoParametro.getCodigoItem());
            estadoHistorico.setEstadoCliente(estadoParametro);
            estadoHistorico.setClienteBase(clienteBase);
            clienteBase.addEstadosHistoricos(estadoHistorico);
        }

        clienteCompletoRepository.save(clienteCompleto);

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setStatus(200);
        respuestaDTO.setMensaje("Cliente actualizado");

        return respuestaDTO;
    }

    /**
     * @param userClienteBaseDTO
     * @return
     * @throws OctagonBusinessException
     */
    public UserClienteBaseDTO createUsuarioFinanaciera(UserClienteBaseDTO userClienteBaseDTO) throws OctagonBusinessException {
        log.debug("Request to create UsuarioFinanaciera : {}", userClienteBaseDTO);
         /**
         * Create User to portalPagos (ms-gateway)
         */
        UserPortalPagosDTO userPortalPagosDTO = new UserPortalPagosDTO();
        userPortalPagosDTO.setLogin(String.valueOf(userClienteBaseDTO.getUserDTO().getLogin()));
        userPortalPagosDTO.setEmail(userClienteBaseDTO.getUserDTO().getEmail());
        userPortalPagosDTO.setAuthorities(userClienteBaseDTO.getUserDTO().getAuthorities());
        userPortalPagosDTO.setActivated(Boolean.FALSE);

        UserDTO userDTO;
        ResponseDTO<UserDTO> msGatewayresponseDTO;
        try {
            msGatewayresponseDTO = gateway.createUserPortalPagos(userPortalPagosDTO);
        } catch (ApiException e) {
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        } catch (Exception e) {
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_GATEWAY);
        }
        if (Boolean.FALSE.equals(msGatewayresponseDTO.getStatus())) {
            throw new OctagonBusinessException(msGatewayresponseDTO.getCode(), msGatewayresponseDTO.getMessage(), msGatewayresponseDTO.getDescription());
        }
        userDTO = msGatewayresponseDTO.getResponseData();
        /**
         * Create Cliente Portal Pagos (gestioncliente)
         */
        ClienteBaseDTO clienteBaseDTO = new ClienteBaseDTO();
        clienteBaseDTO.setIdUsuario(userDTO.getId());
        Optional<Parametro> optionalParametro = parametroRepository.findByCodigoTablaAndDescripcionLarga(Constants.COD_TABLA_PORTAL_PAGOS, Constants.PORTAL_PAGOS);
        if (!optionalParametro.isPresent()) {
            throw new OctagonBusinessException(ResponseStatus.PARAMETRO_NOT_FOUND);
        }
        clienteBaseDTO.setOrigenRegistro(Constants.PORTAL_PAGOS);
        clienteBaseDTO.setDni(String.valueOf(userClienteBaseDTO.getClienteBaseDTO().getDni()));
        clienteBaseDTO.setNombre(userClienteBaseDTO.getClienteBaseDTO().getNombre());
        clienteBaseDTO.setApellido(userClienteBaseDTO.getClienteBaseDTO().getApellido());
        clienteBaseDTO = (ClienteBaseDTO) parametroService.setParametro(clienteBaseDTO, Constants.ESTADO_CLIENTE, Constants.INACTIVO);
        clienteBaseDTO = (ClienteBaseDTO) parametroService.setParametro(clienteBaseDTO, Constants.TIPO_CLIENTE, Constants.REGISTRADO);
        clienteBaseDTO = clienteBaseService.save(clienteBaseDTO);

        /**
         * Create Usuario Finanaciera (ms-prestamos)
         */
        UserDTO userFinancieraLogged = gatewayService.getUserCurrent();
        ClienteBaseDTO clienteFinancieraLogged = clienteBaseService.getClienteBaseByDni(userFinancieraLogged.getLogin()).getResponseData();
        ResponseDTO<UsuarioFinancieraDTO> msPrestamosResponseDTO;
        try {
            msPrestamosResponseDTO = msPrestamos.createUsuarioFinanciera(userDTO.getId(), clienteFinancieraLogged.getId());
        } catch (ApiException e) {
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        } catch (Exception e) {
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_MS_PRESTAMO);
        }

        if (Boolean.FALSE.equals(msPrestamosResponseDTO.getStatus())) {
            throw new OctagonBusinessException(msGatewayresponseDTO.getCode(), msGatewayresponseDTO.getMessage(), msGatewayresponseDTO.getDescription());
        }

        UserClienteBaseDTO userClienteBaseCreated = new UserClienteBaseDTO(userDTO,clienteBaseDTO);
        log.debug("UsuarioFinanaciera Created : {}", userClienteBaseCreated);
        return userClienteBaseCreated;
    }

    private List<Parametro> getTiposPersona(FiltroClienteDTO filtroClienteDTO) {
        List<Parametro> tiposPersona = new ArrayList<>();
        /**
         * Obtengo los parametros de tipo de Persona, sino envio datos busco física
         */
        if (filtroClienteDTO.getTipoPersona() == null || filtroClienteDTO.getTipoPersona().isEmpty()) {
            Optional<Parametro> optionalFisica = parametroRepository.findByCodigoTablaAndDescripcionLarga(Constants.COD_TABLA_TIPO_PERSONA, Constants.FISICA);
            if (optionalFisica.isPresent()) {
                tiposPersona.add(optionalFisica.get());
            }
        } else {
            filtroClienteDTO.getTipoPersona().stream()
                .forEach(tipoPersonaSearch ->
                    tiposPersona.add(parametroRepository.findByCodigoTablaAndDescripcionLarga(Constants.COD_TABLA_TIPO_PERSONA, tipoPersonaSearch).get()));
        }
        log.debug("tiposPersona {}", tiposPersona);

        return tiposPersona;
    }

    /**
     * Obtengo los usuarios de la financiera filtrado por dni
     * @param dni
     * @return
     * @throws OctagonBusinessException
     */
    public UserClienteBaseDTO getUsuariosFinanacieraByDni(String dni) throws OctagonBusinessException {

        /**
         * Obtengo la financiera logeada (cliente)
         */
        ClienteBaseDTO financieraDTO = getFinancieraLogged();
        /**
         * Obtengo el usuario solicitado por dni
         */
        UserDTO userDTO = getUserByDni(dni);

        /**
         * Validar que el dni ingresado corresponda a un usuario de la financiera logeada
         */
        validateUserMatchWithFinanciera(financieraDTO.getId(), userDTO.getId());

        /**
         * Obtengo el cliente solicitado por dni
         */
        Optional<ClienteBaseDTO> optionalClienteBaseDTO = clienteBaseService.findByIdUsuario(userDTO.getId());
        ClienteBaseDTO clienteBaseDTO = null;
        if (optionalClienteBaseDTO.isPresent()) {
            clienteBaseDTO = optionalClienteBaseDTO.get();
        }
        /**
         * Creo el DTO : UserClienteBaseDTO
         */
        UserClienteBaseDTO userClienteBaseCreated = new UserClienteBaseDTO(userDTO,clienteBaseDTO);
        log.debug("UsuarioFinanaciera Created : {}", userClienteBaseCreated);
        return userClienteBaseCreated;

    }

    /**
     * Obtengo el cliente y user logeado (cliente Financiera)
     *
     * @return
     */
    private ClienteBaseDTO getFinancieraLogged() throws OctagonBusinessException {
        /**
         * Obtengo el usuario logeado(clienteFinanciera)
         */
        UserDTO userCurrent = gatewayService.getUserCurrent();
        log.debug("userCurrent {}", userCurrent.getId());
        /**
         * Busco el cliente del usuario logeado (clienteFinanciera)
         */
        Optional<ClienteBaseDTO> optionalClienteBase = clienteBaseService.findByIdUsuario(userCurrent.getId());
        if (!optionalClienteBase.isPresent()) {
            throw new OctagonBusinessException(ResponseStatus.ENTITY_NOT_FOUND, "cliente user id ".concat(userCurrent.getId().toString()));
        }
        return optionalClienteBase.get();
    }

    public UserClienteBaseDTO activateUsuarioFinancieraByDni(String dni, Boolean activate) throws OctagonBusinessException {
        /**
         * Obtengo la financiera logeada (cliente)
         */
        ClienteBaseDTO financieraDTO = getFinancieraLogged();
        /**
         * Obtengo el usuario solicitado por dni
         */
        UserDTO userDTO = getUserByDni(dni);

        /**
         * Activate/Desactivate user
         */

        ResponseDTO<UserDTO> msGatewayResponseDTO;
        try {
            msGatewayResponseDTO = gateway.activateUserPortalPagos(userDTO.getLogin(),activate);
        } catch (ApiException e) {
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        } catch (Exception e) {
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_GATEWAY);
        }

        if (Boolean.FALSE.equals(msGatewayResponseDTO.getStatus()) || msGatewayResponseDTO.getResponseData() == null) {
            throw new OctagonBusinessException(msGatewayResponseDTO.getCode(), msGatewayResponseDTO.getMessage(), msGatewayResponseDTO.getDescription());
        }

        UserDTO userDTOUpdated = msGatewayResponseDTO.getResponseData();

        /**
         * Validar que el dni ingresado corresponda a un usuario de la financiera logeada
         */
        validateUserMatchWithFinanciera(financieraDTO.getId(), userDTO.getId());

        /**
         * Obtengo el cliente solicitado por dni
         */
        Optional<ClienteBase> optionalClienteBaseDTO = clienteBaseRepository.findByIdUsuario(userDTO.getId());
        ClienteBase clienteBase = null;
        if (optionalClienteBaseDTO.isPresent()) {
            clienteBase = optionalClienteBaseDTO.get();
        }
        /**
         * Activate/Desactivate cliente
         */
        ClienteBase clienteBaseUpdated = clienteBaseService.activateCliente(clienteBase.getId(), activate);
        ClienteBaseDTO clienteBaseDTOUpdated = clienteBaseMapper.toDto(clienteBaseUpdated);

        UserClienteBaseDTO userClienteBaseDTO = new UserClienteBaseDTO(userDTOUpdated,clienteBaseDTOUpdated);
        return userClienteBaseDTO;
    }


    /**
     * Obtengo el usuario solicitado por dni
     *
     * @param dni
     * @return UserDTO
     * @throws OctagonBusinessException
     */
    private UserDTO getUserByDni(String dni) throws OctagonBusinessException {
        ResponseDTO<UserDTO> msGatewayResponseDTO;
        try {
            msGatewayResponseDTO = gateway.getUserByDni(dni);
        } catch (ApiException e) {
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        } catch (Exception e) {
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_GATEWAY);
        }

        if (Boolean.FALSE.equals(msGatewayResponseDTO.getStatus()) || msGatewayResponseDTO.getResponseData() == null) {
            throw new OctagonBusinessException(msGatewayResponseDTO.getCode(), msGatewayResponseDTO.getMessage(), msGatewayResponseDTO.getDescription());
        }

        return msGatewayResponseDTO.getResponseData();
    }


    /**
     * Validar que el dni ingresado corresponda a un usuario de la financiera logeada
     *
     * @param financieraId
     * @param userId
     * @return Boolean
     * @throws OctagonBusinessException
     */
    private Boolean validateUserMatchWithFinanciera(Long financieraId, Long userId) throws OctagonBusinessException {
        ResponseDTO msPrestamosResponseDTO;
        try {
            msPrestamosResponseDTO = msPrestamos.getUsuarioFinanciera(financieraId, userId);
        } catch (ApiException e) {
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        } catch (Exception e) {
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_MS_PRESTAMO);
        }

        if (Boolean.FALSE.equals(msPrestamosResponseDTO.getStatus()) || msPrestamosResponseDTO.getResponseData() == null) {
            throw new OctagonBusinessException(msPrestamosResponseDTO.getCode(), msPrestamosResponseDTO.getMessage(), msPrestamosResponseDTO.getDescription());
        }
        return true;

    }

}

