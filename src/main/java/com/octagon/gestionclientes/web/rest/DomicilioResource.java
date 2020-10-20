package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.DomicilioService;
import com.octagon.gestionclientes.service.dto.DomicilioDTO;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Domicilio.
 */
@RestController
@RequestMapping("/api")
public class DomicilioResource {

    private final Logger log = LoggerFactory.getLogger(DomicilioResource.class);

    private static final String ENTITY_NAME = "gestionclientesDomicilio";

    private final DomicilioService domicilioService;

    public DomicilioResource(DomicilioService domicilioService) {
        this.domicilioService = domicilioService;
    }

    /**
     * POST  /domicilios : Create a new domicilio.
     *
     * @param domicilioDTO the domicilioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new domicilioDTO, or with status 400 (Bad Request) if the domicilio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/domicilios")
    public ResponseEntity<DomicilioDTO> createDomicilio(@RequestBody DomicilioDTO domicilioDTO) throws URISyntaxException {
        log.debug("REST request to save Domicilio : {}", domicilioDTO);
        if (domicilioDTO.getId() != null) {
            throw new BadRequestAlertException("A new domicilio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DomicilioDTO result = domicilioService.save(domicilioDTO);
        return ResponseEntity.created(new URI("/api/domicilios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /domicilios : Updates an existing domicilio.
     *
     * @param domicilioDTO the domicilioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated domicilioDTO,
     * or with status 400 (Bad Request) if the domicilioDTO is not valid,
     * or with status 500 (Internal Server Error) if the domicilioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/domicilios")
    public ResponseEntity<DomicilioDTO> updateDomicilio(@RequestBody DomicilioDTO domicilioDTO) {
        log.debug("REST request to update Domicilio : {}", domicilioDTO);
        if (domicilioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DomicilioDTO result = domicilioService.save(domicilioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, domicilioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /domicilios : get all the domicilios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of domicilios in body
     */
    @GetMapping("/domicilios")
    public ResponseEntity<List<DomicilioDTO>> getAllDomicilios(Pageable pageable) {
        log.debug("REST request to get a page of Domicilios");
        Page<DomicilioDTO> page = domicilioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/domicilios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /domicilios/:id : get the "id" domicilio.
     *
     * @param id the id of the domicilioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domicilioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/domicilios/{id}")
    public ResponseEntity<DomicilioDTO> getDomicilio(@PathVariable Long id) {
        log.debug("REST request to get Domicilio : {}", id);
        Optional<DomicilioDTO> domicilioDTO = domicilioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domicilioDTO);
    }

    /**
     * DELETE  /domicilios/:id : delete the "id" domicilio.
     *
     * @param id the id of the domicilioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/domicilios/{id}")
    public ResponseEntity<Void> deleteDomicilio(@PathVariable Long id) {
        log.debug("REST request to delete Domicilio : {}", id);
        domicilioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
