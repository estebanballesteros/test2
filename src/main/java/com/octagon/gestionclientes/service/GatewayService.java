package com.octagon.gestionclientes.service;


import com.octagon.gestionclientes.service.dto.UserDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;

public interface GatewayService {

    UserDTO getUserCurrent() throws OctagonBusinessException;

}
