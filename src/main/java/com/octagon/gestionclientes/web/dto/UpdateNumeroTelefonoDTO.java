package com.octagon.gestionclientes.web.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UpdateNumeroTelefonoDTO {
    private Long id;

    @NotNull
    private String numeroTelefono;

    @NotNull
    private Boolean principal;

    @NotNull
    private Boolean validado;

    @NotNull
    @Valid
    private UpdateParametroDTO tipoTelefono;

    private Boolean eliminar;

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Boolean isValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public UpdateParametroDTO getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(UpdateParametroDTO tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }
}
