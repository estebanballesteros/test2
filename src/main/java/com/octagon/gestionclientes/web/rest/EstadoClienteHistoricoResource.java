package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.EstadoClienteHistoricoService;
import com.octagon.gestionclientes.service.dto.EstadoClienteHistoricoDTO;
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
 * REST controller for managing EstadoClienteHistorico.
 */
@RestController
@RequestMapping("/api")
public class EstadoClienteHistoricoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoClienteHistoricoResource.class);

    private static final String ENTITY_NAME = "gestionclientesEstadoClienteHistorico";

    private final EstadoClienteHistoricoService estadoClienteHistoricoService;

    public EstadoClienteHistoricoResource(EstadoClienteHistoricoService estadoClienteHistoricoService) {
        this.estadoClienteHistoricoService = estadoClienteHistoricoService;
    }

    /**
     * POST  /estado-cliente-historicos : Create a new estadoClienteHistorico.
     *
     * @param estadoClienteHistoricoDTO the estadoClienteHistoricoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estadoClienteHistoricoDTO, or with status 400 (Bad Request) if the estadoClienteHistorico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estado-cliente-historicos")
    public ResponseEntity<EstadoClienteHistoricoDTO> createEstadoClienteHistorico(@RequestBody EstadoClienteHistoricoDTO estadoClienteHistoricoDTO) throws URISyntaxException {
        log.debug("REST request to save EstadoClienteHistorico : {}", estadoClienteHistoricoDTO);
        if (estadoClienteHistoricoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadoClienteHistorico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoClienteHistoricoDTO result = estadoClienteHistoricoService.save(estadoClienteHistoricoDTO);
        return ResponseEntity.created(new URI("/api/estado-cliente-historicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estado-cliente-historicos : Updates an existing estadoClienteHistorico.
     *
     * @param estadoClienteHistoricoDTO the estadoClienteHistoricoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estadoClienteHistoricoDTO,
     * or with status 400 (Bad Request) if the estadoClienteHistoricoDTO is not valid,
     * or with status 500 (Internal Server Error) if the estadoClienteHistoricoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estado-cliente-historicos")
    public ResponseEntity<EstadoClienteHistoricoDTO> updateEstadoClienteHistorico(@RequestBody EstadoClienteHistoricoDTO estadoClienteHistoricoDTO) {
        log.debug("REST request to update EstadoClienteHistorico : {}", estadoClienteHistoricoDTO);
        if (estadoClienteHistoricoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoClienteHistoricoDTO result = estadoClienteHistoricoService.save(estadoClienteHistoricoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estadoClienteHistoricoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estado-cliente-historicos : get all the estadoClienteHistoricos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estadoClienteHistoricos in body
     */
    @GetMapping("/estado-cliente-historicos")
    public ResponseEntity<List<EstadoClienteHistoricoDTO>> getAllEstadoClienteHistoricos(Pageable pageable) {
        log.debug("REST request to get a page of EstadoClienteHistoricos");
        Page<EstadoClienteHistoricoDTO> page = estadoClienteHistoricoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estado-cliente-historicos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /estado-cliente-historicos/:id : get the "id" estadoClienteHistorico.
     *
     * @param id the id of the estadoClienteHistoricoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estadoClienteHistoricoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estado-cliente-historicos/{id}")
    public ResponseEntity<EstadoClienteHistoricoDTO> getEstadoClienteHistorico(@PathVariable Long id) {
        log.debug("REST request to get EstadoClienteHistorico : {}", id);
        Optional<EstadoClienteHistoricoDTO> estadoClienteHistoricoDTO = estadoClienteHistoricoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoClienteHistoricoDTO);
    }

    /**
     * DELETE  /estado-cliente-historicos/:id : delete the "id" estadoClienteHistorico.
     *
     * @param id the id of the estadoClienteHistoricoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estado-cliente-historicos/{id}")
    public ResponseEntity<Void> deleteEstadoClienteHistorico(@PathVariable Long id) {
        log.debug("REST request to delete EstadoClienteHistorico : {}", id);
        estadoClienteHistoricoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
