package com.octagon.gestionclientes.web.dto;

import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.dto.UserDTO;

import javax.validation.constraints.NotNull;

public class UserClienteBaseDTO {

    @NotNull
    UserDTO userDTO;
    @NotNull
    ClienteBaseDTO clienteBaseDTO;

    public UserClienteBaseDTO() {
    }

    public UserClienteBaseDTO(@NotNull UserDTO userDTO, @NotNull ClienteBaseDTO clienteBaseDTO) {
        this.userDTO = userDTO;
        this.clienteBaseDTO = clienteBaseDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public ClienteBaseDTO getClienteBaseDTO() {
        return clienteBaseDTO;
    }

    public void setClienteBaseDTO(ClienteBaseDTO clienteBaseDTO) {
        this.clienteBaseDTO = clienteBaseDTO;
    }
}
