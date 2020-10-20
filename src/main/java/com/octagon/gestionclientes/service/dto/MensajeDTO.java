package com.octagon.gestionclientes.service.dto;

import java.time.ZonedDateTime;

public class MensajeDTO {

    private String accion;
    private String numeroCelular;

    private Long id;

    private String mensaje;


    private String estado;

    private String numero;

    private Long idMensajeProveedor;

    private ZonedDateTime fechaCreacion;

    private String mensajeErrorProveedor;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getIdMensajeProveedor() {
        return idMensajeProveedor;
    }

    public void setIdMensajeProveedor(Long idMensajeProveedor) {
        this.idMensajeProveedor = idMensajeProveedor;
    }

    public ZonedDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMensajeErrorProveedor() {
        return mensajeErrorProveedor;
    }

    public void setMensajeErrorProveedor(String mensajeErrorProveedor) {
        this.mensajeErrorProveedor = mensajeErrorProveedor;
    }
}
