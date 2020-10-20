package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.AgendaDestinatarioService;
import com.octagon.gestionclientes.service.ClienteBaseService;
import com.octagon.gestionclientes.service.dto.AgendaDestinatarioDTO;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.ErrorUtils;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import com.octagon.gestionclientes.web.rest.util.HeaderUtil;
import com.octagon.gestionclientes.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AgendaDestinatario.
 */
@RestController
@RequestMapping("/api")
public class AgendaDestinatarioResource {

    private final Logger log = LoggerFactory.getLogger(AgendaDestinatarioResource.class);

    private static final String ENTITY_NAME = "gestionclientesAgendaDestinatario";

    private final AgendaDestinatarioService agendaDestinatarioService;

    @Autowired
    private ClienteBaseService clienteBaseService;

    public AgendaDestinatarioResource(AgendaDestinatarioService agendaDestinatarioService) {
        this.agendaDestinatarioService = agendaDestinatarioService;
    }

    /**
     * POST  /agenda-destinatarios : Create a new agendaDestinatario.
     *
     * @param agendaDestinatarioDTO the agendaDestinatarioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agendaDestinatarioDTO, or with status 400 (Bad Request) if the agendaDestinatario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agenda-destinatarios")
    public ResponseEntity<ResponseDTO<AgendaDestinatarioDTO>> createAgendaDestinatario(@RequestBody @Validated AgendaDestinatarioDTO agendaDestinatarioDTO, @ApiIgnore final Errors errors) throws URISyntaxException {
        log.debug("REST request to save AgendaDestinatario : {}", agendaDestinatarioDTO);
        try {
            ErrorUtils.Validate(errors);

            if (agendaDestinatarioDTO.getId() != null) {
                throw new OctagonBusinessException(ResponseStatus.BAD_REQUEST,"A new agendaDestinatario cannot already have an ID");
            }
            if (agendaDestinatarioDTO.getTelefono() == null || agendaDestinatarioDTO.getTelefono().isEmpty()) {
                throw new OctagonBusinessException(ResponseStatus.BAD_REQUEST,"A new agendaDestinatario no longer have an empty phone");
            }

            AgendaDestinatarioDTO result = agendaDestinatarioService.save(agendaDestinatarioDTO);
            ResponseDTO<AgendaDestinatarioDTO> response = new ResponseDTO<>(result);
            return ResponseEntity.created(new URI("/api/agenda-destinatarios/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                    .body(response);

        }catch (OctagonBusinessException e)
        {
            log.error("OctagonBusinessException {}", e.getDescription());
            return ResponseEntity.ok().body(e.getResponseDTO());
        }
        catch (Exception e)
        {
            log.error("OctagonBusinessException {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(e));
        }
    }

    /**
     * PUT  /agenda-destinatarios : Updates an existing agendaDestinatario.
     *
     * @param agendaDestinatarioDTO the agendaDestinatarioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agendaDestinatarioDTO,
     * or with status 400 (Bad Request) if the agendaDestinatarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the agendaDestinatarioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agenda-destinatarios")
    public ResponseEntity<ResponseDTO<AgendaDestinatarioDTO>> updateAgendaDestinatario(@RequestBody @Validated AgendaDestinatarioDTO agendaDestinatarioDTO, @ApiIgnore final Errors errors) {
        log.debug("REST request to update AgendaDestinatario : {}", agendaDestinatarioDTO);
        try {
            //Validación parámetros de entrada
            ErrorUtils.Validate(errors);
            if (agendaDestinatarioDTO.getId() == null) {
                throw new OctagonBusinessException(ResponseStatus.INVALID_ID);
            }
            if (agendaDestinatarioDTO.getTelefono() == null || agendaDestinatarioDTO.getTelefono().isEmpty()) {
                throw new OctagonBusinessException(ResponseStatus.BAD_REQUEST," Telefono is required");
            }
            //Guardar destinatario
            AgendaDestinatarioDTO result = agendaDestinatarioService.save(agendaDestinatarioDTO);
            ResponseDTO<AgendaDestinatarioDTO> response = new ResponseDTO<>(result);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agendaDestinatarioDTO.getId().toString()))
                    .body(response);
        }
        catch (OctagonBusinessException e)
        {
            log.error("OctagonBusinessException {}", e.getDescription());
            return ResponseEntity.ok().body(e.getResponseDTO());
        }
        catch (Exception e)
        {
            log.error("Exception {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(e));
        }
    }

    /**
     * GET  /agenda-destinatarios : get all the agendaDestinatarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agendaDestinatarios in body
     */
    @GetMapping("/agenda-destinatarios")
    public ResponseEntity<List<AgendaDestinatarioDTO>> getAllAgendaDestinatarios(Pageable pageable) {
        log.debug("REST request to get a page of AgendaDestinatarios");
        Page<AgendaDestinatarioDTO> page = agendaDestinatarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agenda-destinatarios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /agenda-destinatarios/:id : get the "id" agendaDestinatario.
     *
     * @param id the id of the agendaDestinatarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agendaDestinatarioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agenda-destinatarios/{id}")
    public ResponseEntity<AgendaDestinatarioDTO> getAgendaDestinatario(@PathVariable Long id) {
        log.debug("REST request to get AgendaDestinatario : {}", id);
        Optional<AgendaDestinatarioDTO> agendaDestinatarioDTO = agendaDestinatarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendaDestinatarioDTO);
    }

    /**
     * DELETE  /agenda-destinatarios/:id : delete the "id" agendaDestinatario.
     *
     * @param id the id of the agendaDestinatarioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agenda-destinatarios/{id}")
    public ResponseEntity<Void> deleteAgendaDestinatario(@PathVariable Long id) {
        log.debug("REST request to delete AgendaDestinatario : {}", id);
        agendaDestinatarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /cliente-bases/idUsuario/:idUsuario: get the clienteBase by idUsuario.
     *
     * @param clienteBaseId the id of the AgendaDestinatarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clienteBaseDTO, or with status 404 (Not Found)
     */
    //TODO No "integre" ResponseDTO por falta de tiempo
    @GetMapping("/agenda-destinatarios-cliente/{clienteBaseId}")
    public ResponseEntity<List<AgendaDestinatarioDTO>> findByclienteBaseId(@PathVariable Long clienteBaseId, Pageable pageable) {
        log.debug("REST request to get ClienteBase : {}", clienteBaseId);
        Page<AgendaDestinatarioDTO> listaUserAgenda = agendaDestinatarioService.findByclienteBaseId(clienteBaseId, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(listaUserAgenda, "/api/agenda-destinatarios");
        return ResponseEntity.ok().headers(headers).body(listaUserAgenda.getContent());
    }


    /**
     * @param clienteBaseDni the id of the AgendaDestinatarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clienteBaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agenda-destinatarios-cliente/dni/{clienteBaseDni}")
    public ResponseEntity<ResponseDTO<List<AgendaDestinatarioDTO>>> findByDni(@PathVariable Long clienteBaseDni, Pageable pageable) {
        log.debug("REST request to get ClienteBase : {}", clienteBaseDni);
        if (clienteBaseDni == null) {
            return new ResponseEntity<>(new ResponseDTO<>(false, ResponseStatus.INVALID_DNI),HttpStatus.OK);
        }
        Optional<ClienteBaseDTO> clienteBase;
        try {
            clienteBase = clienteBaseService.findByDni(clienteBaseDni.toString());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(false, ResponseStatus.NOT_FOUND),HttpStatus.OK);
        }

        if(!clienteBase.isPresent()) {
            return new ResponseEntity<>(new ResponseDTO<>(false, ResponseStatus.DNI_NOT_FOUND),HttpStatus.OK);
        }

        Page<AgendaDestinatarioDTO> listaAgendaDestinatario = agendaDestinatarioService.findByclienteBaseId(clienteBase.get().getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(listaAgendaDestinatario, "/api/agenda-destinatarios");

        ResponseDTO responseDTO = new ResponseDTO<>(true, ResponseStatus.AGENDA_OBTAINED);
        responseDTO.setResponseData(ResponseEntity.ok().headers(headers).body(listaAgendaDestinatario.getContent()));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
