package com.octagon.gestionclientes.service.dto;

import javax.validation.constraints.NotNull;

public class ActivarTelefonoDTO {

    @NotNull
    private Long idUsuario;

    private String telefono;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
