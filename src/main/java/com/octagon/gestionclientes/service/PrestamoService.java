package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.service.dto.PrestamoDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;

import java.util.List;

public interface PrestamoService {

    List<PrestamoDTO> getPrestamosAprobadosOpOctagonByDNI(String dniCliente) throws OctagonBusinessException;

    void liquidarPrestamo(Long prestamoId) throws OctagonBusinessException;

    List<PrestamoDTO> getPrestamosAprobadosSupOctagonByDNI(String dniCliente) throws OctagonBusinessException;

}
