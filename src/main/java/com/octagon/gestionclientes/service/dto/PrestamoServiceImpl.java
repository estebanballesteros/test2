package com.octagon.gestionclientes.service.dto;

import com.octagon.gestionclientes.client.MsPrestamos;
import com.octagon.gestionclientes.service.PrestamoService;
import com.octagon.gestionclientes.service.utils.ApiException;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final Logger log = LoggerFactory.getLogger(PrestamoServiceImpl.class);

    @Autowired
    private MsPrestamos msPrestamos;

    @Override
    public List<PrestamoDTO> getPrestamosAprobadosOpOctagonByDNI(String dniCliente) throws OctagonBusinessException {
        return null;
    }

    @Override
    public void liquidarPrestamo(Long prestamoId) throws OctagonBusinessException {

        try {
            ResponseDTO<PrestamoDTO> responseDTO = msPrestamos.liquidarPrestamo(prestamoId);
            if (responseDTO.getStatus()){
                log.info("Response Message: " , responseDTO.getMessage());
                log.info("Prestamo liquidado: {}", prestamoId);
            }else{
                log.error("Error - Response Message: " , responseDTO.getMessage());
                throw new OctagonBusinessException(responseDTO.getCode(), responseDTO.getMessage(), responseDTO.getDescription());
            }
        }catch (ApiException e){
            log.error("Error - Response Message: " , e.getMessage());
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        }catch (Exception e){
            log.error("Ha ocurrido un error en la comunicacion con el MS Prestamos");
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_MS_PRESTAMO);
        }
    }

    @Override
    public List<PrestamoDTO> getPrestamosAprobadosSupOctagonByDNI(String dniCliente) throws OctagonBusinessException {
        try {
            ResponseDTO<List<PrestamoDTO>> responseDTO = msPrestamos.getPRestamosAprobadosSupOctagonByDNI(dniCliente);
            if (responseDTO.getStatus())
                return responseDTO.getResponseData();
            throw new OctagonBusinessException(responseDTO.getCode(), responseDTO.getMessage(), responseDTO.getDescription());
        }catch (ApiException e){
            throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
        }catch (Exception e){
            throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_MS_PRESTAMO);
        }
    }
}
