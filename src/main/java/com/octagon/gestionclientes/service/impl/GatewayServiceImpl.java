package com.octagon.gestionclientes.service.impl;

import com.octagon.gestionclientes.client.Gateway;
import com.octagon.gestionclientes.service.GatewayService;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.dto.UserDTO;
import com.octagon.gestionclientes.service.utils.ApiException;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.octagon.gestionclientes.security.SecurityUtils;

import java.util.Optional;


@Service
public class GatewayServiceImpl implements GatewayService {

    private final Logger log = LoggerFactory.getLogger(GatewayServiceImpl.class);

    @Autowired
    Gateway gateway;

    /**
     * Obtengo el usuario logeado
     *
     * @return UserDTO
     * @throws OctagonBusinessException
     */
    @Override
    public UserDTO getUserCurrent() throws OctagonBusinessException {
        ResponseDTO<UserDTO> msGatewayResponseDTO = null;
        Optional<String> userCurrent = SecurityUtils.getCurrentUserLogin();
        if (userCurrent.isPresent()) {
            try {
                msGatewayResponseDTO = gateway.getUserByDni(userCurrent.get());
            } catch (ApiException e) {
                throw new OctagonBusinessException(e.getCode(), e.getMessage(), e.getDescription());
            } catch (Exception e) {
                log.error("Ha ocurrido un error al intentar recuperar datos del usuario en sesion. Error: {}", e);
                throw new OctagonBusinessException(ResponseStatus.ERROR_CALL_GATEWAY);
            }
        } else
            log.error("User no identified");

        return msGatewayResponseDTO.getResponseData();
    }
}
