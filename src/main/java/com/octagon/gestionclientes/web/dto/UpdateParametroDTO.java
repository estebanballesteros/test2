package com.octagon.gestionclientes.web.dto;

import javax.validation.constraints.NotNull;

public class UpdateParametroDTO {
    private Long id;

    private String descripcionLarga;

    @NotNull
    private String descripcionCorta;

    private Integer codigoTabla;

    private Integer codigoItem;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
}
