package com.octagon.gestionclientes.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AgendaDestinatario.
 */
@Entity
@Table(name = "agenda_destinatario")
public class AgendaDestinatario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dni")
    private Long dni;

    @Column(name = "alias")
    private String alias;

    @Column(name = "telefono")
    private String telefono;

    @Column(name="favorito")
    private Boolean favorito;

    @ManyToOne
    @JsonIgnoreProperties("agenda")
    private ClienteBase clienteBase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDni() {
        return dni;
    }

    public AgendaDestinatario dni(Long dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getAlias() {
        return alias;
    }

    public AgendaDestinatario alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public AgendaDestinatario favorito(Boolean favorito){
        this.favorito = favorito;
        return this;
    }

    public AgendaDestinatario clienteBase(ClienteBase clienteBase) {
        this.clienteBase = clienteBase;
        return this;
    }

    public ClienteBase getClienteBase() {
        return clienteBase;
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
        AgendaDestinatario agendaDestinatario = (AgendaDestinatario) o;
        if (agendaDestinatario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agendaDestinatario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgendaDestinatario{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", alias='" + getAlias() + "'" +
            ", favorito='"+isFavorito()+ "'"+
            ", telefono='" + getTelefono() + "'" +
            "}";
    }

}
