package com.octagon.gestionclientes.service.dto;

    import io.swagger.annotations.ApiParam;

    import java.util.Set;

public class FiltroClienteDTO {
    /**
     * Descripcion Corta Tipo Persona
     */
    @ApiParam(value = "Tipo de persona Descripcion Larga", allowableValues = "Física,Jurídica")
    Set<String> tipoPersona;

    public Set<String> getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(Set<String> tipoPersona) {
        this.tipoPersona = tipoPersona;
    }


}
