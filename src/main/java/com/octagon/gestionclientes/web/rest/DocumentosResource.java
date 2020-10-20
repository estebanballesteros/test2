package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.service.DocumentosService;
import com.octagon.gestionclientes.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.octagon.gestionclientes.service.dto.DocumentoDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.service.utils.TipoArchivoInvalidoException;


import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class DocumentosResource {

    private final Logger log = LoggerFactory.getLogger(DocumentosResource.class);

    private static final String ENTITY_NAME = "DocumentosResource";

    private final DocumentosService documentoService;

    public DocumentosResource(DocumentosService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/documentos")
    public ResponseEntity<ResponseDTO> createDocumento(@RequestParam Long clienteId, @RequestParam("file") MultipartFile documento) throws Exception {
        log.debug("REST request to save Documento: {} ", documento.getOriginalFilename());
        ResponseDTO responseDTO = documentoService.save(clienteId, documento);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/documentos")
    public ResponseEntity<ResponseDTO<List<DocumentoDTO>>> getAllDocumentos(@RequestParam Long clienteId) throws Exception {
        log.debug("REST request to get all Documentos");
        ResponseDTO<List<DocumentoDTO>> documentos = documentoService.findAllByClienteCompleto(clienteId);
        return ResponseEntity.ok().body(documentos);
    }

    @DeleteMapping("/documentos")
    public ResponseEntity<ResponseDTO> deleteDocumento(@RequestParam Long id) throws Exception {
        log.debug("REST request to delete Documento : {}", id);
        ResponseDTO responseDTO = documentoService.delete(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDTO> error(NoSuchElementException e) {
        ResponseDTO responseDTO = new ResponseDTO(false, ResponseStatus.NOT_FOUND.getCode(),
            ResponseStatus.ENTITY_NOT_FOUND.getMessage(), ResponseStatus.ENTITY_NOT_FOUND.getDescription());

        return ResponseEntity.status(404).body(responseDTO);
    }

    @ExceptionHandler(TipoArchivoInvalidoException.class)
    public ResponseEntity<ResponseDTO> error(TipoArchivoInvalidoException e) {
        ResponseDTO responseDTO = new ResponseDTO(false, ResponseStatus.BAD_REQUEST.getCode(),
            ResponseStatus.TIPO_ARCHIVO_INVALIDO.getMessage(), ResponseStatus.TIPO_ARCHIVO_INVALIDO.getDescription());

        return ResponseEntity.status(400).body(responseDTO);
    }
}
