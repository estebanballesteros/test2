package com.octagon.gestionclientes.web.dto;
public class GetNumeroTelefonoDTO {
    private Long id;

    private String numeroTelefono;

    private Boolean principal;

    private Boolean validado;

    private GetParametroDTO tipoTelefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public GetParametroDTO getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(GetParametroDTO tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }
}
