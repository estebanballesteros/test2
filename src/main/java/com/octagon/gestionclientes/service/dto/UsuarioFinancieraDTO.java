package com.octagon.gestionclientes.service.dto;

import java.io.Serializable;

/**
 * A DTO for the UsuarioFinancieraDTO entity.
 */
public class UsuarioFinancieraDTO implements Serializable {

    private Long id;
    private Long usuarioId;
    private Long clienteFinancieraId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getClienteFinancieraId() {
        return clienteFinancieraId;
    }

    public void setClienteFinancieraId(Long clienteFinancieraId) {
        this.clienteFinancieraId = clienteFinancieraId;
    }
}
