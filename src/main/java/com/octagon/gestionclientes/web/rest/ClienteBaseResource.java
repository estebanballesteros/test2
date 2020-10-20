package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.service.ClienteBaseService;
import com.octagon.gestionclientes.service.dto.ActivarTelefonoDTO;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.dto.ClienteDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.Constants;
import com.octagon.gestionclientes.service.utils.ErrorUtils;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import com.octagon.gestionclientes.web.rest.errors.CustomParameterizedException;
import com.octagon.gestionclientes.web.rest.util.HeaderUtil;
import com.octagon.gestionclientes.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClienteBase.
 */
@RestController
@RequestMapping("/api")
public class ClienteBaseResource {

    private final Logger log = LoggerFactory.getLogger(ClienteBaseResource.class);

    private static final String ENTITY_NAME = "gestionclientesClienteBase";

    private final ClienteBaseService clienteBaseService;


    public ClienteBaseResource(ClienteBaseService clienteBaseService) {
        this.clienteBaseService = clienteBaseService;

    }


    /**clienteclientecomple
     * PUT  /cliente-bases : Updates an existing clienteBase.
     *
     * @param clienteBaseDTO the clienteBaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clienteBaseDTO,
     * or with status 400 (Bad Request) if the clienteBaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the clienteBaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cliente-bases")
    public ResponseEntity<ClienteBaseDTO> updateClienteBase(@RequestBody ClienteBaseDTO clienteBaseDTO) {
        log.debug("REST request to update ClienteBase : {}", clienteBaseDTO);
        if (clienteBaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClienteBaseDTO result = clienteBaseService.save(clienteBaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clienteBaseDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/cliente-bases/activar-telefono")
    public ResponseEntity<ClienteBaseDTO> activarTelefonoCliente(@RequestBody ActivarTelefonoDTO activarTelefonoDTO) {
        log.debug("REST request to activar telefono ClienteBase : {}", activarTelefonoDTO);
        ClienteBaseDTO result = null;
        try{
            result = clienteBaseService.activarTelefono(activarTelefonoDTO);
        }catch (CustomParameterizedException  e){
            log.error(e.getMessage());
            throw new BadRequestAlertException(e.getMessage(), ClienteBase.class.getSimpleName(),e.getParameters().get("message").toString());

        }catch (Exception e){
            throw new BadRequestAlertException(e.getMessage(),e.getCause().getMessage(),"No se pudo activar el telefono");
        }
        return ResponseEntity.ok().body(result);
    }
    /**
     * GET  /cliente-bases : get all the clienteBases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clienteBases in body
     */
    @GetMapping("/cliente-bases")
    public ResponseEntity<List<ClienteBaseDTO>> getAllClienteBases(Pageable pageable) {
        log.debug("REST request to get a page of ClienteBases");
        Page<ClienteBaseDTO> page = clienteBaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cliente-bases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cliente-bases/:id : get the "id" clienteBase.
     *
     * @param id the id of the clienteBaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clienteBaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cliente-bases/{id}")
    public ResponseEntity<ClienteBaseDTO> getClienteBase(@PathVariable Long id) {
        log.debug("REST request to get ClienteBase : {}", id);
        Optional<ClienteBaseDTO> clienteBaseDTO = clienteBaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteBaseDTO);
    }

    /**
     * DELETE  /cliente-bases/:id : delete the "id" clienteBase.
     *
     * @param id the id of the clienteBaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cliente-bases/{id}")
    public ResponseEntity<Void> deleteClienteBase(@PathVariable Long id) {
        log.debug("REST request to delete ClienteBase : {}", id);
        clienteBaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/cliente-bases/exists-registrado/{dni}")
    public ResponseEntity<Boolean> existsRegistrado(@PathVariable String dni) {
        log.debug("REST request to get if exitst ClienteBase registrado : {}", dni);
        boolean existRegistrado = clienteBaseService.existsClienteBaseRegistrado(dni);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("existsRegistrado", dni)).body(existRegistrado);
    }

    /**
     * GET  /cliente-bases/idUsuario/:idUsuario: get the clienteBase by idUsuario.
     *
     * @param idUsuario the id of the clienteBaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clienteBaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cliente-bases/idUsuario/{idUsuario}")
    public ResponseEntity<ClienteBaseDTO> getClienteBaseByIdUsuario(@PathVariable Long idUsuario) {
        log.debug("REST request to get ClienteBase : {}", idUsuario);
        Optional<ClienteBaseDTO> clienteBaseDTO;
        try {
            clienteBaseDTO = clienteBaseService.findByIdUsuario(idUsuario);
        } catch (Exception e) {
            throw new BadRequestAlertException("getClienteBaseByIdUsuario Exception", ENTITY_NAME, e.getMessage());
        }
        return ResponseUtil.wrapOrNotFound(clienteBaseDTO);
    }

    /**
     * CALL FROM 'APP MOBILE and ATM FROM 'CREATE TRANSFERENCIA'
     * GET  /cliente-bases/: get the clienteBase by dni if exits otherwise create it.
     *
     * @param clienteDTO
     * @return he ResponseEntity with status 200 (OK) and with body the clienteBaseDTO.
     */
    @PostMapping("/cliente-bases")
    public ResponseEntity<ResponseDTO<ClienteDTO>> createClienteBase(@Valid @RequestBody ClienteDTO clienteDTO, @ApiIgnore final Errors errors) throws URISyntaxException {
        log.debug("REST request to get ClienteBase if exists : {}", clienteDTO.getDni(), "or Create it");
        String descriptionErrors = "";
        if (!Constants.APP.equalsIgnoreCase(clienteDTO.getOrigenRegistro()) &&
            !Constants.ATM.equalsIgnoreCase(clienteDTO.getOrigenRegistro()) &&
            !Constants.BO.equalsIgnoreCase(clienteDTO.getOrigenRegistro())) {
            descriptionErrors = ResponseStatus.INVALID_CUENTA_ORIGEN.getDescription();
        }
        if (Constants.APP.equalsIgnoreCase(clienteDTO.getOrigenRegistro()) && (clienteDTO.getAlias().equals("") || clienteDTO.getAlias() == null)) {
            descriptionErrors = descriptionErrors.concat(ResponseStatus.INAVALID_ALIAS.getDescription());
        }
        if (errors.hasErrors() || !descriptionErrors.equals("")) {
            descriptionErrors = ErrorUtils.getDescriptionErrors(errors).concat(descriptionErrors);
            log.error("Errors REST request to get ClienteBase if exists or Create it: {}", descriptionErrors);
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(false, ResponseStatus.BAD_REQUEST, ResponseStatus.BAD_REQUEST.getMessage().concat(descriptionErrors)));
        }

        ResponseDTO<ClienteDTO> responseDTO = clienteBaseService.getOrCreateClienteBase(clienteDTO);
        if (Boolean.FALSE.equals(responseDTO.getStatus())) {
            return ResponseEntity.unprocessableEntity().body(responseDTO);
        }
        return ResponseEntity.created(new URI("/api/cliente-bases/create" + responseDTO.getResponseData().getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, responseDTO.getResponseData().getId().toString()))
            .body(responseDTO);

    }


    /**
     * PUT  /cliente-bases/activate/{id} : Activate an existing clienteBase.
     *
     * @param id
     * @return the ResponseEntity with status 200 (OK) and with body the updated clienteBaseDTO,
     * or with status 400 (Bad Request) if the clienteBaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the clienteBaseDTO couldn't be updated
     */
    @PutMapping("/cliente-bases/activate/{id}")
    public ResponseEntity<ResponseDTO> activateCliente(@PathVariable Long id) {
        log.debug("REST request to activateCliente : {}", id);
        ClienteBaseDTO clienteBaseDTO;
        try {
            clienteBaseDTO = clienteBaseService.activarCliente(id);
        } catch (OctagonBusinessException e) {
            return ResponseEntity.ok(e.getResponseDTO());
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(false, ex));
        }

        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString());
        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(true, ResponseStatus.OK, clienteBaseDTO));

    }



    /**
     * GET  /cliente-bases/dni/:dni
     *
     * @param dni the dni of the clienteBaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clienteBaseDTO.
     */
    @GetMapping("/cliente-bases/dni/{dni}")
    public ResponseEntity<ResponseDTO> getClienteBaseByDni(@PathVariable String dni) {
        log.debug("REST request to get ClienteBase with DNI: {}", dni);

        ResponseDTO responseDTO = clienteBaseService.getClienteBaseByDni(dni);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert(ENTITY_NAME, dni.toString())).body(responseDTO);
    }

}
