package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.ClienteService;
import com.octagon.gestionclientes.service.dto.FiltroClienteDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.dto.GetClienteDTO;
import com.octagon.gestionclientes.web.dto.RespuestaDTO;
import com.octagon.gestionclientes.web.dto.UpdateClienteDTO;
import com.octagon.gestionclientes.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
/**
 * REST controller for managing ClienteCompleto.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PutMapping("")
    public ResponseEntity<RespuestaDTO> updateCliente(@Valid
        @RequestBody UpdateClienteDTO updateClienteDTO) {

        log.debug("REST request to update ClienteCompleto : {}", updateClienteDTO);

        RespuestaDTO updateClienteDTORes = clienteService
            .updateCliente(updateClienteDTO);

        return ResponseEntity.ok().body(updateClienteDTORes);
    }

    /**
     * Obtengo todos los cliente filtrados y paginados
     *
     * @param filtroClienteDTO
     * @param pageable
     * @return ResponseDTO<Page < GetClienteDTO>>
     */
    @GetMapping("")
    @ApiOperation(value = "Obtengo los clientes filtrados y paginados por tipo de persona", consumes = "BackOffice")
    @PreAuthorize("hasPermission('ClienteResource','findClientesByTipoPersona')")
    public ResponseEntity<ResponseDTO<Page<GetClienteDTO>>> findClientesByTipoPersona(FiltroClienteDTO filtroClienteDTO, Pageable pageable) {
        log.debug("REST request to get a page of Usuarios Fianciera Filter by {}", filtroClienteDTO);
        Page<GetClienteDTO> page;
        try {
            page = clienteService.findClientesByTipoPersona(filtroClienteDTO, pageable);
        } catch (OctagonBusinessException e) {
            return ResponseEntity.ok(e.getResponseDTO());
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(false, ex));
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "");
        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(true, ResponseStatus.OK, page.getContent()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetClienteDTO> getCliente(@PathVariable Long id) {
        log.debug("REST request to get ClienteCompleto : {}", id);
        Optional<GetClienteDTO> clienteDTO = clienteService.findOneClienteCompleto(id);
        return ResponseUtil.wrapOrNotFound(clienteDTO);
    }




}
