package com.octagon.gestionclientes.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Parametro.
 */
@Entity
@Table(name = "parametro")
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo_tabla")
    private Integer codigoTabla;

    @Column(name = "codigo_item")
    private Integer codigoItem;

    @Column(name = "descripcion_larga")
    private String descripcionLarga;

    @Column(name = "descripcion_corta")
    private String descripcionCorta;

    @Column(name = "grupo_1")
    private String grupo1;

    @Column(name = "grupo_2")
    private String grupo2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoTabla() {
        return codigoTabla;
    }

    public Parametro codigoTabla(Integer codigoTabla) {
        this.codigoTabla = codigoTabla;
        return this;
    }

    public void setCodigoTabla(Integer codigoTabla) {
        this.codigoTabla = codigoTabla;
    }

    public Integer getCodigoItem() {
        return codigoItem;
    }

    public Parametro codigoItem(Integer codigoItem) {
        this.codigoItem = codigoItem;
        return this;
    }

    public void setCodigoItem(Integer codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public Parametro descripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
        return this;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public Parametro descripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
        return this;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getGrupo1() {
        return grupo1;
    }

    public Parametro grupo1(String grupo1) {
        this.grupo1 = grupo1;
        return this;
    }

    public void setGrupo1(String grupo1) {
        this.grupo1 = grupo1;
    }

    public String getGrupo2() {
        return grupo2;
    }

    public Parametro grupo2(String grupo2) {
        this.grupo2 = grupo2;
        return this;
    }

    public void setGrupo2(String grupo2) {
        this.grupo2 = grupo2;
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
        Parametro parametro = (Parametro) o;
        if (parametro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parametro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parametro{" +
            "id=" + getId() +
            ", codigoTabla=" + getCodigoTabla() +
            ", codigoItem=" + getCodigoItem() +
            ", descripcionLarga='" + getDescripcionLarga() + "'" +
            ", descripcionCorta='" + getDescripcionCorta() + "'" +
            ", grupo1='" + getGrupo1() + "'" +
            ", grupo2='" + getGrupo2() + "'" +
            "}";
    }
}
