package com.octagon.gestionclientes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the EstadoClienteHistorico entity.
 */
public class EstadoClienteHistoricoDTO implements Serializable {

    private Long id;

    private Instant fecha;

    private Integer codTablaEstadoCliente;

    private Integer codItemEstadoCliente;


    private Long clienteBaseId;

    private Long estadoClienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Integer getCodTablaEstadoCliente() {
        return codTablaEstadoCliente;
    }

    public void setCodTablaEstadoCliente(Integer codTablaEstadoCliente) {
        this.codTablaEstadoCliente = codTablaEstadoCliente;
    }

    public Integer getCodItemEstadoCliente() {
        return codItemEstadoCliente;
    }

    public void setCodItemEstadoCliente(Integer codItemEstadoCliente) {
        this.codItemEstadoCliente = codItemEstadoCliente;
    }

    public Long getClienteBaseId() {
        return clienteBaseId;
    }

    public void setClienteBaseId(Long clienteBaseId) {
        this.clienteBaseId = clienteBaseId;
    }

    public Long getEstadoClienteId() {
        return estadoClienteId;
    }

    public void setEstadoClienteId(Long parametroId) {
        this.estadoClienteId = parametroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstadoClienteHistoricoDTO estadoClienteHistoricoDTO = (EstadoClienteHistoricoDTO) o;
        if (estadoClienteHistoricoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estadoClienteHistoricoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstadoClienteHistoricoDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", codTablaEstadoCliente=" + getCodTablaEstadoCliente() +
            ", codItemEstadoCliente=" + getCodItemEstadoCliente() +
            ", clienteBase=" + getClienteBaseId() +
            ", estadoCliente=" + getEstadoClienteId() +
            "}";
    }
}
