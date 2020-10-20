package com.octagon.gestionclientes.service.dto;

import javax.validation.Valid;
import java.util.List;

public class UserRespuestasDTO {
    @Valid
    private UserDTO userDTO;

    private List<UserPreguntaSecretaDTO> listaRespuestas;

    public UserRespuestasDTO() {

    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

}
