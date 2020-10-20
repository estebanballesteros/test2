package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.service.dto.DocumentoDTO;
import org.springframework.web.multipart.MultipartFile;
import com.octagon.gestionclientes.service.dto.ResponseDTO;

import java.util.Optional;
import java.util.List;

public interface DocumentosService {

    ResponseDTO save(Long clienteId, MultipartFile comprobante) throws Exception;

    ResponseDTO<List<DocumentoDTO>> findAllByClienteCompleto(Long id) throws Exception;

    ResponseDTO delete(Long id) throws Exception;
}
