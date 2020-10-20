package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.CorreoElectronicoService;
import com.octagon.gestionclientes.service.dto.CorreoElectronicoDTO;
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
 * REST controller for managing CorreoElectronico.
 */
@RestController
@RequestMapping("/api")
public class CorreoElectronicoResource {

    private final Logger log = LoggerFactory.getLogger(CorreoElectronicoResource.class);

    private static final String ENTITY_NAME = "gestionclientesCorreoElectronico";

    private final CorreoElectronicoService correoElectronicoService;

    public CorreoElectronicoResource(CorreoElectronicoService correoElectronicoService) {
        this.correoElectronicoService = correoElectronicoService;
    }

    /**
     * POST  /correo-electronicos : Create a new correoElectronico.
     *
     * @param correoElectronicoDTO the correoElectronicoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correoElectronicoDTO, or with status 400 (Bad Request) if the correoElectronico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correo-electronicos")
    public ResponseEntity<CorreoElectronicoDTO> createCorreoElectronico(@RequestBody CorreoElectronicoDTO correoElectronicoDTO) throws URISyntaxException {
        log.debug("REST request to save CorreoElectronico : {}", correoElectronicoDTO);
        if (correoElectronicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new correoElectronico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorreoElectronicoDTO result = correoElectronicoService.save(correoElectronicoDTO);
        return ResponseEntity.created(new URI("/api/correo-electronicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correo-electronicos : Updates an existing correoElectronico.
     *
     * @param correoElectronicoDTO the correoElectronicoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correoElectronicoDTO,
     * or with status 400 (Bad Request) if the correoElectronicoDTO is not valid,
     * or with status 500 (Internal Server Error) if the correoElectronicoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correo-electronicos")
    public ResponseEntity<CorreoElectronicoDTO> updateCorreoElectronico(@RequestBody CorreoElectronicoDTO correoElectronicoDTO) {
        log.debug("REST request to update CorreoElectronico : {}", correoElectronicoDTO);
        if (correoElectronicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorreoElectronicoDTO result = correoElectronicoService.save(correoElectronicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correoElectronicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correo-electronicos : get all the correoElectronicos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of correoElectronicos in body
     */
    @GetMapping("/correo-electronicos")
    public ResponseEntity<List<CorreoElectronicoDTO>> getAllCorreoElectronicos(Pageable pageable) {
        log.debug("REST request to get a page of CorreoElectronicos");
        Page<CorreoElectronicoDTO> page = correoElectronicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correo-electronicos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /correo-electronicos/:id : get the "id" correoElectronico.
     *
     * @param id the id of the correoElectronicoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correoElectronicoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correo-electronicos/{id}")
    public ResponseEntity<CorreoElectronicoDTO> getCorreoElectronico(@PathVariable Long id) {
        log.debug("REST request to get CorreoElectronico : {}", id);
        Optional<CorreoElectronicoDTO> correoElectronicoDTO = correoElectronicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correoElectronicoDTO);
    }

    /**
     * DELETE  /correo-electronicos/:id : delete the "id" correoElectronico.
     *
     * @param id the id of the correoElectronicoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correo-electronicos/{id}")
    public ResponseEntity<Void> deleteCorreoElectronico(@PathVariable Long id) {
        log.debug("REST request to delete CorreoElectronico : {}", id);
        correoElectronicoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
