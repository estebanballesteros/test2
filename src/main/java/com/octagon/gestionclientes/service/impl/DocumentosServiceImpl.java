package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.client.AlmacenamientoClient;
import com.octagon.gestionclientes.client.dto.MetadataDTO;
import com.octagon.gestionclientes.domain.Documento;
import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.repository.DocumentoRepository;
import com.octagon.gestionclientes.repository.ClienteCompletoRepository;
import com.octagon.gestionclientes.service.DocumentosService;
import com.octagon.gestionclientes.service.dto.DocumentoDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.service.utils.TipoArchivoInvalidoException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.octagon.gestionclientes.client.EntityNotFoundException;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletRequest;

import org.springframework.http.ResponseEntity;

@Service
@Transactional
public class DocumentosServiceImpl implements DocumentosService {

    private final Logger log = LoggerFactory.getLogger(DocumentosServiceImpl.class);

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ClienteCompletoRepository clienteRepository;

    @Autowired
    private AlmacenamientoClient almacenamientoClient;

    private static final List<String> tipoArchivoValido = new ArrayList(
        Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.APPLICATION_PDF_VALUE
        )
    );

    @Override
    public ResponseDTO<DocumentoDTO> save(Long clienteId, MultipartFile documento) throws Exception {
        log.debug("Request to save Documento: {}", documento.getOriginalFilename());
        ClienteCompleto cliente = clienteRepository.findById(clienteId).get();
        validarTipoArchivo(documento);
        Documento documentoNuevo = new Documento();
        documentoNuevo.setPath(randomFilename(documento.getOriginalFilename()));
        documentoNuevo.setClienteCompleto(cliente);
        documentoRepository.save(documentoNuevo);
        MetadataDTO metadata = almacenamientoClient.create(documento, "documento", documentoNuevo.getPath());
        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setId(documentoNuevo.getId());
        documentoDTO.setUrl(metadata.getUrl());
        ResponseDTO responseDTO = new ResponseDTO(true, ResponseStatus.OK.getCode(),
            ResponseStatus.OK.getMessage(), ResponseStatus.OK.getDescription());
        responseDTO.setResponseData(documentoDTO);
        return responseDTO;
    }

    @Override
    public ResponseDTO<List<DocumentoDTO>> findAllByClienteCompleto(Long clienteId) throws Exception {
        log.debug("Request to get all Documentos");
        clienteRepository.findById(clienteId).get();
        List<Documento> documentos = documentoRepository.findAllByClienteCompletoId(clienteId);
        List<DocumentoDTO> documentosDTO = new ArrayList();

        for(Documento documento: documentos) {
            try {
                MetadataDTO metadata = almacenamientoClient.get("documento", documento.getPath());
                DocumentoDTO documentoDTO = new DocumentoDTO();
                documentoDTO.setId(documento.getId());
                documentoDTO.setUrl(metadata.getUrl());
                documentosDTO.add(documentoDTO);
            }catch(EntityNotFoundException e){
                documentoRepository.delete(documento);
            }
        }

        ResponseDTO responseDTO = new ResponseDTO(true, ResponseStatus.OK.getCode(),
            ResponseStatus.OK.getMessage(), ResponseStatus.OK.getDescription());
        responseDTO.setResponseData(documentosDTO);

        return responseDTO;
    }

    @Override
    public ResponseDTO delete(Long id) throws Exception {
        log.debug("Request to delete Documento : {}", id);
        Documento documento = documentoRepository.findById(id).get();
        almacenamientoClient.delete("documento", documento.getPath());
        documentoRepository.deleteById(documento.getId());
        return new ResponseDTO(true, ResponseStatus.OK.getCode(),
            ResponseStatus.OK.getMessage(), ResponseStatus.OK.getDescription());
    }

    private String randomFilename(String filename) {
        return String.format("%s.%s", UUID.randomUUID().toString(), FilenameUtils.getExtension(filename));
    }

    private void validarTipoArchivo(MultipartFile documento) throws TipoArchivoInvalidoException {
        if(!tipoArchivoValido.contains(documento.getContentType()))
            throw new TipoArchivoInvalidoException();
    }
}
