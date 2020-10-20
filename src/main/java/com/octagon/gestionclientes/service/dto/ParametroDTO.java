package com.octagon.gestionclientes.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Parametro entity.
 */
public class ParametroDTO implements Serializable {

    private Long id;

    private Integer codigoTabla;

    private Integer codigoItem;

    private String descripcionLarga;

    private String descripcionCorta;

    private String grupo1;

    private String grupo2;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoTabla() {
        return codigoTabla;
    }

    public void setCodigoTabla(Integer codigoTabla) {
        this.codigoTabla = codigoTabla;
    }

    public Integer getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(Integer codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getGrupo1() {
        return grupo1;
    }

    public void setGrupo1(String grupo1) {
        this.grupo1 = grupo1;
    }

    public String getGrupo2() {
        return grupo2;
    }

    public void setGrupo2(String grupo2) {
        this.grupo2 = grupo2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParametroDTO parametroDTO = (ParametroDTO) o;
        if (parametroDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parametroDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParametroDTO{" +
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
