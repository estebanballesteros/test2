package com.octagon.gestionclientes.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NumeroTelefono.
 */
@Entity
@Table(name = "numero_telefono")
public class NumeroTelefono implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_telefono")
    private String numeroTelefono;

    @Column(name = "principal")
    private Boolean principal;

    @Column(name = "validado")
    private Boolean validado;

    @ManyToOne
    @JsonIgnoreProperties("numerosTelefonos")
    private ClienteBase clienteBase;

    @ManyToOne
    private Parametro tipoTelefono;

    @Column(name = "cod_tabla_tipo_telefono")
    private Integer codTablaTipoTelefono;

    @Column(name = "cod_item_tipo_telefono")
    private Integer codItemTipoTelefono;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public NumeroTelefono numeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
        return this;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public NumeroTelefono principal(Boolean principal) {
        this.principal = principal;
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Boolean isValidado() {
        return validado;
    }

    public NumeroTelefono validado(Boolean validado) {
        this.validado = validado;
        return this;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public ClienteBase getClienteBase() {
        return clienteBase;
    }

    public NumeroTelefono clienteBase(ClienteBase clienteBase) {
        this.clienteBase = clienteBase;
        return this;
    }

    public void setClienteBase(ClienteBase clienteBase) {
        this.clienteBase = clienteBase;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Parametro getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(Parametro tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
        setCodItemTipoTelefono(tipoTelefono.getCodigoItem());
        setCodTablaTipoTelefono(tipoTelefono.getCodigoTabla());
    }

    public void setCodTablaTipoTelefono(Integer codTablaTipoTelefono) {
        this.codTablaTipoTelefono = codTablaTipoTelefono;
    }

    public Integer getCodTablaTipoTelefono() {
        return codTablaTipoTelefono;
    }

    public void setCodItemTipoTelefono(Integer codItemTipoTelefono) {
        this.codItemTipoTelefono = codItemTipoTelefono;
    }

    public Integer getCodItemTipoTelefono() {
        return codItemTipoTelefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NumeroTelefono numeroTelefono = (NumeroTelefono) o;
        if (numeroTelefono.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numeroTelefono.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NumeroTelefono{" +
            "id=" + getId() +
            ", numeroTelefono='" + getNumeroTelefono() + "'" +
            ", principal='" + isPrincipal() + "'" +
            ", validado='" + isValidado() + "'" +
            "}";
    }
}
