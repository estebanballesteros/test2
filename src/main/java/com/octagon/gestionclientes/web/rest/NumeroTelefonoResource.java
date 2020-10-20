package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.NumeroTelefonoService;
import com.octagon.gestionclientes.service.dto.NumeroTelefonoDTO;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
import com.octagon.gestionclientes.web.rest.util.HeaderUtil;
import com.octagon.gestionclientes.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NumeroTelefono.
 */
@RestController
@RequestMapping("/api")
public class NumeroTelefonoResource {

    private final Logger log = LoggerFactory.getLogger(NumeroTelefonoResource.class);

    private static final String ENTITY_NAME = "gestionclientesNumeroTelefono";

    private final NumeroTelefonoService numeroTelefonoService;

    public NumeroTelefonoResource(NumeroTelefonoService numeroTelefonoService) {
        this.numeroTelefonoService = numeroTelefonoService;
    }

    /**
     * POST  /numero-telefonos : Create a new numeroTelefono.
     *
     * @param numeroTelefonoDTO the numeroTelefonoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new numeroTelefonoDTO, or with status 400 (Bad Request) if the numeroTelefono has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/numero-telefonos")
    public ResponseEntity<NumeroTelefonoDTO> createNumeroTelefono(@RequestBody @Validated NumeroTelefonoDTO numeroTelefonoDTO) throws URISyntaxException {
        log.debug("REST request to save NumeroTelefono : {}", numeroTelefonoDTO);
        if (numeroTelefonoDTO.getId() != null) {
            throw new BadRequestAlertException("A new numeroTelefono cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumeroTelefonoDTO result = numeroTelefonoService.save(numeroTelefonoDTO);
        return ResponseEntity.created(new URI("/api/numero-telefonos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /numero-telefonos : Updates an existing numeroTelefono.
     *
     * @param numeroTelefonoDTO the numeroTelefonoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated numeroTelefonoDTO,
     * or with status 400 (Bad Request) if the numeroTelefonoDTO is not valid,
     * or with status 500 (Internal Server Error) if the numeroTelefonoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/numero-telefonos")
    public ResponseEntity<NumeroTelefonoDTO> updateNumeroTelefono(@RequestBody NumeroTelefonoDTO numeroTelefonoDTO) {
        log.debug("REST request to update NumeroTelefono : {}", numeroTelefonoDTO);
        if (numeroTelefonoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NumeroTelefonoDTO result = numeroTelefonoService.save(numeroTelefonoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, numeroTelefonoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /numero-telefonos : get all the numeroTelefonos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of numeroTelefonos in body
     */
    @GetMapping("/numero-telefonos")
    public ResponseEntity<List<NumeroTelefonoDTO>> getAllNumeroTelefonos(Pageable pageable) {
        log.debug("REST request to get a page of NumeroTelefonos");
        Page<NumeroTelefonoDTO> page = numeroTelefonoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/numero-telefonos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /numero-telefonos/:id : get the "id" numeroTelefono.
     *
     * @param id the id of the numeroTelefonoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the numeroTelefonoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/numero-telefonos/{id}")
    public ResponseEntity<NumeroTelefonoDTO> getNumeroTelefono(@PathVariable Long id) {
        log.debug("REST request to get NumeroTelefono : {}", id);
        Optional<NumeroTelefonoDTO> numeroTelefonoDTO = numeroTelefonoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(numeroTelefonoDTO);
    }

    /**
     * DELETE  /numero-telefonos/:id : delete the "id" numeroTelefono.
     *
     * @param id the id of the numeroTelefonoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/numero-telefonos/{id}")
    public ResponseEntity<Void> deleteNumeroTelefono(@PathVariable Long id) {
        log.debug("REST request to delete NumeroTelefono : {}", id);
        numeroTelefonoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
