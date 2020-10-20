package com.octagon.gestionclientes.web.dto;

public class CuentaDTOExt {

    private Long id;
    private String numero;
    private String fechaCierre;
    private Integer codTablaEstado;
    private Integer codEstado;
    private Integer codTablaTipo;
    private Integer codTipo;
    private Integer codTablaTipoLimite;
    private Integer codTipoLimite;
    private Double limite;
    private Integer codTablaMoneda;
    private Integer codMoneda;
    private Long estadoCuentaId;
    private Long tipoCuentaId;
    private Long monedaCuentaId;
    private Double saldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Integer getCodTablaEstado() {
        return codTablaEstado;
    }

    public void setCodTablaEstado(Integer codTablaEstado) {
        this.codTablaEstado = codTablaEstado;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public Integer getCodTablaTipo() {
        return codTablaTipo;
    }

    public void setCodTablaTipo(Integer codTablaTipo) {
        this.codTablaTipo = codTablaTipo;
    }

    public Integer getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(Integer codTipo) {
        this.codTipo = codTipo;
    }

    public Integer getCodTablaTipoLimite() {
        return codTablaTipoLimite;
    }

    public void setCodTablaTipoLimite(Integer codTablaTipoLimite) {
        this.codTablaTipoLimite = codTablaTipoLimite;
    }

    public Integer getCodTipoLimite() {
        return codTipoLimite;
    }

    public void setCodTipoLimite(Integer codTipoLimite) {
        this.codTipoLimite = codTipoLimite;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Integer getCodTablaMoneda() {
        return codTablaMoneda;
    }

    public void setCodTablaMoneda(Integer codTablaMoneda) {
        this.codTablaMoneda = codTablaMoneda;
    }

    public Integer getCodMoneda() {
        return codMoneda;
    }

    public void setCodMoneda(Integer codMoneda) {
        this.codMoneda = codMoneda;
    }

    public Long getEstadoCuentaId() {
        return estadoCuentaId;
    }

    public void setEstadoCuentaId(Long estadoCuentaId) {
        this.estadoCuentaId = estadoCuentaId;
    }

    public Long getTipoCuentaId() {
        return tipoCuentaId;
    }

    public void setTipoCuentaId(Long tipoCuentaId) {
        this.tipoCuentaId = tipoCuentaId;
    }

    public Long getMonedaCuentaId() {
        return monedaCuentaId;
    }

    public void setMonedaCuentaId(Long monedaCuentaId) {
        this.monedaCuentaId = monedaCuentaId;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
