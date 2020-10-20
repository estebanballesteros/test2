package com.octagon.gestionclientes.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorreoElectronico.
 */
@Entity
@Table(name = "correo_electronico")
public class CorreoElectronico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "validado")
    private Boolean validado;

    @Column(name = "principal")
    private Boolean principal;

    @ManyToOne
    @JsonIgnoreProperties("correosElectronicos")
    private ClienteBase clienteBase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public CorreoElectronico correoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
        return this;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Boolean isValidado() {
        return validado;
    }

    public CorreoElectronico validado(Boolean validado) {
        this.validado = validado;
        return this;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public CorreoElectronico principal(Boolean principal) {
        this.principal = principal;
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public ClienteBase getClienteBase() {
        return clienteBase;
    }

    public CorreoElectronico clienteBase(ClienteBase clienteBase) {
        this.clienteBase = clienteBase;
        return this;
    }

    public void setClienteBase(ClienteBase clienteBase) {
        this.clienteBase = clienteBase;
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
        CorreoElectronico correoElectronico = (CorreoElectronico) o;
        if (correoElectronico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correoElectronico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorreoElectronico{" +
            "id=" + getId() +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", validado='" + isValidado() + "'" +
            ", principal='" + isPrincipal() + "'" +
            "}";
    }
}
