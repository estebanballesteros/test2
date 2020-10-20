package com.octagon.gestionclientes.web.dto;

import javax.validation.constraints.NotNull;

public class UpdateCorreoElectronicoDTO {
    private Long id;

    @NotNull
    private String correoElectronico;

    @NotNull
    private Boolean validado;

    @NotNull
    private Boolean principal;

    private Boolean eliminar;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Boolean isValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
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
