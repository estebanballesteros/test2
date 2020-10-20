package com.octagon.gestionclientes.service.dto;

public class AltaCuentaDTO {

    private Long clienteId;
    private String codTitularidad;
    private String codTipoCuenta;
    private String codEstadoCuenta;
    private String codTipoLimite;
    private String codMoneda;
    private Boolean principal;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getCodTitularidad() {
        return codTitularidad;
    }

    public void setCodTitularidad(String codTitularidad) {
        this.codTitularidad = codTitularidad;
    }

    public String getCodTipoCuenta() {
        return codTipoCuenta;
    }

    public void setCodTipoCuenta(String codTipoCuenta) {
        this.codTipoCuenta = codTipoCuenta;
    }

    public String getCodEstadoCuenta() {
        return codEstadoCuenta;
    }

    public void setCodEstadoCuenta(String codEstadoCuenta) {
        this.codEstadoCuenta = codEstadoCuenta;
    }

    public String getCodTipoLimite() {
        return codTipoLimite;
    }

    public void setCodTipoLimite(String codTipoLimite) {
        this.codTipoLimite = codTipoLimite;
    }

    public String getCodMoneda() {
        return codMoneda;
    }

    public void setCodMoneda(String codMoneda) {
        this.codMoneda = codMoneda;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
