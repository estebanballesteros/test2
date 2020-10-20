package com.octagon.gestionclientes.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A EstadoClienteHistorico.
 */
@Entity
@Table(name = "estado_cliente_historico")
public class EstadoClienteHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "cod_tabla_estado_cliente")
    private Integer codTablaEstadoCliente;

    @Column(name = "cod_item_estado_cliente")
    private Integer codItemEstadoCliente;

    @ManyToOne
    @JsonIgnoreProperties("estadosHistoricos")
    private ClienteBase clienteBase;

    @ManyToOne
    @JsonIgnoreProperties("estadoClienteHistoricos")
    private Parametro estadoCliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public EstadoClienteHistorico fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Integer getCodTablaEstadoCliente() {
        return codTablaEstadoCliente;
    }

    public EstadoClienteHistorico codTablaEstadoCliente(Integer codTablaEstadoCliente) {
        this.codTablaEstadoCliente = codTablaEstadoCliente;
        return this;
    }

    public void setCodTablaEstadoCliente(Integer codTablaEstadoCliente) {
        this.codTablaEstadoCliente = codTablaEstadoCliente;
    }

    public Integer getCodItemEstadoCliente() {
        return codItemEstadoCliente;
    }

    public EstadoClienteHistorico codItemEstadoCliente(Integer codItemEstadoCliente) {
        this.codItemEstadoCliente = codItemEstadoCliente;
        return this;
    }

    public void setCodItemEstadoCliente(Integer codItemEstadoCliente) {
        this.codItemEstadoCliente = codItemEstadoCliente;
    }

    public ClienteBase getClienteBase() {
        return clienteBase;
    }

    public EstadoClienteHistorico clienteBase(ClienteBase clienteBase) {
        this.clienteBase = clienteBase;
        return this;
    }

    public void setClienteBase(ClienteBase clienteBase) {
        this.clienteBase = clienteBase;
    }

    public Parametro getEstadoCliente() {
        return estadoCliente;
    }

    public EstadoClienteHistorico estadoCliente(Parametro parametro) {
        this.estadoCliente = parametro;
        return this;
    }

    public void setEstadoCliente(Parametro parametro) {
        this.estadoCliente = parametro;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EstadoClienteHistorico estadoClienteHistorico = (EstadoClienteHistorico) o;
        if (estadoClienteHistorico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estadoClienteHistorico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstadoClienteHistorico{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", codTablaEstadoCliente=" + getCodTablaEstadoCliente() +
            ", codItemEstadoCliente=" + getCodItemEstadoCliente() +
            "}";
    }
}
