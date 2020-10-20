package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.ClienteCompletoService;
import com.octagon.gestionclientes.service.dto.ClienteCompletoDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.dto.ClienteBaseCompletoDTOExt;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import com.octagon.gestionclientes.web.rest.util.HeaderUtil;
import com.octagon.gestionclientes.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing ClienteCompleto.
 */
@RestController
@RequestMapping("/api")
public class ClienteCompletoResource {

    private final Logger log = LoggerFactory.getLogger(ClienteCompletoResource.class);

    private static final String ENTITY_NAME = "gestionclientesClienteCompleto";

    private final ClienteCompletoService clienteCompletoService;

    public ClienteCompletoResource(ClienteCompletoService clienteCompletoService) {
        this.clienteCompletoService = clienteCompletoService;
    }

    /**
     * POST  /cliente-completos : Create a new clienteCompleto.
     *
     * @param clienteCompletoDTO the clienteCompletoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clienteCompletoDTO, or with status 400 (Bad Request) if the clienteCompleto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cliente-completos")
    public ResponseEntity<ClienteCompletoDTO> createClienteCompleto(@RequestBody @Valid ClienteCompletoDTO clienteCompletoDTO) throws URISyntaxException {
        log.debug("REST request to save ClienteCompleto : {}", clienteCompletoDTO);
        if (clienteCompletoDTO.getId() != null) {
            throw new BadRequestAlertException("A new clienteCompleto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteCompletoDTO result = clienteCompletoService.save(clienteCompletoDTO);
        return ResponseEntity.created(new URI("/api/cliente-completos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /cliente-completos : Create a new clienteCompleto And base.
     *
     * @param clienteCompletoBaseDTO the clienteCompletoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ClienteBase and ClienteCompleto, or with status 400 (Bad Request) if the clienteCompleto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cliente-completo-base-cuenta")
    public ResponseEntity<ClienteBaseCompletoDTOExt> createClienteCompletoBaseCuenta(@Valid @RequestBody ClienteBaseCompletoDTOExt clienteCompletoBaseDTO) throws URISyntaxException {
        log.debug("REST request to save ClienteCompletoBase : {}", clienteCompletoBaseDTO);
        if (clienteCompletoBaseDTO.getIdClienteCompleto() != null) {
            throw new BadRequestAlertException("A new clienteCompletoBaseDTO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteBaseCompletoDTOExt result = new ClienteBaseCompletoDTOExt();
        try {
            result = clienteCompletoService.createClienteCompletoBaseCuenta(clienteCompletoBaseDTO);
        } catch (OctagonBusinessException obe) {
            return ResponseEntity.unprocessableEntity().body(null);
        }

        return ResponseEntity.created(new URI("/api/cliente-completos-base/" + result.getIdClienteCompleto()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getIdClienteCompleto().toString()))
            .body(result);
    }

    /**
     * PUT  /cliente-completos : Updates an existing clienteCompleto.
     *
     * @param clienteCompletoDTO the clienteCompletoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clienteCompletoDTO,
     * or with status 400 (Bad Request) if the clienteCompletoDTO is not valid,
     * or with status 500 (Internal Server Error) if the clienteCompletoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cliente-completos")
    public ResponseEntity<ClienteCompletoDTO> updateClienteCompleto(@RequestBody ClienteCompletoDTO clienteCompletoDTO) {
        log.debug("REST request to update ClienteCompleto : {}", clienteCompletoDTO);
        if (clienteCompletoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClienteCompletoDTO result = clienteCompletoService.save(clienteCompletoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clienteCompletoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cliente-completos : get all the clienteCompletos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clienteCompletos in body
     */
    @GetMapping("/cliente-completos")
    public ResponseEntity<List<ClienteCompletoDTO>> getAllClienteCompletos(Pageable pageable) {
        log.debug("REST request to get a page of ClienteCompletos");
        Page<ClienteCompletoDTO> page = clienteCompletoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cliente-completos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cliente-completos/:id : get the "id" clienteCompleto.
     *
     * @param id the id of the clienteCompletoDTO to retrieve
     * @return the ResponseEntity with a ResponseDTO in the body that contains status 200 (OK) and in the responseData the clienteCompletoDTO,
     * otherwise the ResponseDTO containsstatus 404 (Not Found)
     */
    @GetMapping("/cliente-completos/{id}")
    public ResponseEntity<ResponseDTO> getClienteCompleto(@PathVariable Long id) {
        log.debug("REST request to get ClienteCompleto : {}", id);
        ResponseDTO responseDTO = clienteCompletoService.findOne(id);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }


    /**
     * DELETE  /cliente-completos/:id : delete the "id" clienteCompleto.
     *
     * @param id the id of the clienteCompletoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cliente-completos/{id}")
    public ResponseEntity<Void> deleteClienteCompleto(@PathVariable Long id) {
        log.debug("REST request to delete ClienteCompleto : {}", id);
        clienteCompletoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
