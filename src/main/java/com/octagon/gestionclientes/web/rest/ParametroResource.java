package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.dto.ParametroDTO;
import com.octagon.gestionclientes.service.utils.Constants;
import com.octagon.gestionclientes.web.dto.GetAllParametroDTO;
import com.octagon.gestionclientes.web.dto.GetParametroDTO;
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
 * REST controller for managing Parametro.
 */
@RestController
@RequestMapping("/api")
public class ParametroResource {

    private final Logger log = LoggerFactory.getLogger(ParametroResource.class);

    private static final String ENTITY_NAME = "gestionclientesParametro";

    private final ParametroService parametroService;

    public ParametroResource(ParametroService parametroService) {
        this.parametroService = parametroService;
    }

    /**
     * POST  /parametros : Create a new parametro.
     *
     * @param parametroDTO the parametroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametroDTO, or with status 400 (Bad Request) if the parametro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametros")
    public ResponseEntity<ParametroDTO> createParametro(@RequestBody ParametroDTO parametroDTO) throws URISyntaxException {
        log.debug("REST request to save Parametro : {}", parametroDTO);
        if (parametroDTO.getId() != null) {
            throw new BadRequestAlertException("A new parametro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametroDTO result = parametroService.save(parametroDTO);
        return ResponseEntity.created(new URI("/api/parametros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametros : Updates an existing parametro.
     *
     * @param parametroDTO the parametroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametroDTO,
     * or with status 400 (Bad Request) if the parametroDTO is not valid,
     * or with status 500 (Internal Server Error) if the parametroDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametros")
    public ResponseEntity<ParametroDTO> updateParametro(@RequestBody ParametroDTO parametroDTO) {
        log.debug("REST request to update Parametro : {}", parametroDTO);
        if (parametroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametroDTO result = parametroService.save(parametroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametros : get all the parametros.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametros in body
     */
    @GetMapping("/allparametros")
    public ResponseEntity<List<ParametroDTO>> getAllParametros(Pageable pageable) {
        log.debug("REST request to get a page of Parametros");
        Page<ParametroDTO> page = parametroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametros");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /parametros/:id : get the "id" parametro.
     *
     * @param id the id of the parametroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parametros/{id}")
    public ResponseEntity<ParametroDTO> getParametro(@PathVariable Long id) {
        log.debug("REST request to get Parametro : {}", id);
        Optional<ParametroDTO> parametroDTO = parametroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametroDTO);
    }

    /**
     * DELETE  /parametros/:id : delete the "id" parametro.
     *
     * @param id the id of the parametroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametros/{id}")
    public ResponseEntity<Void> deleteParametro(@PathVariable Long id) {
        log.debug("REST request to delete Parametro : {}", id);
        parametroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/parametros")
    public ResponseEntity<GetAllParametroDTO> getParametros() {
        log.debug("REST request to get GetAllParametroDTO");
        GetAllParametroDTO parametrosDTOExt = new GetAllParametroDTO();
        for(ParametroDTO parametroDTO: parametroService.findAll()) {
            GetParametroDTO parametro = new GetParametroDTO();
            parametro.setId(parametroDTO.getId());
            parametro.setCodigoTabla(parametroDTO.getCodigoTabla());
            parametro.setCodigoItem(parametroDTO.getCodigoItem());
            parametro.setDescripcionCorta(parametroDTO.getDescripcionCorta());
            parametro.setDescripcionLarga(parametroDTO.getDescripcionLarga());
            switch(parametroDTO.getCodigoTabla()) {
                case Constants.COD_TABLA_ESTADO_CLIENTE:
                    parametrosDTOExt.addEstadoCliente(parametro);
                    break;
                case Constants.COD_TABLA_TIPO_PERSONA:
                    parametrosDTOExt.addTipoPersona(parametro);
                    break;
                case Constants.COD_TABLA_TIPO_TELEFONO:
                    parametrosDTOExt.addTipoTelefono(parametro);
                    break;
                case Constants.COD_TABLA_TIPO_DOMICILIO:
                    parametrosDTOExt.addTipoDomicilio(parametro);
                    break;
                case Constants.COD_TABLA_TIPO_CLIENTE:
                    parametrosDTOExt.addTipoCliente(parametro);
                    break;
                case Constants.COD_TABLA_GENERO:
                    parametrosDTOExt.addGenero(parametro);
                    break;
            }
        }
        return ResponseEntity.ok().body(parametrosDTOExt);
    }
}
