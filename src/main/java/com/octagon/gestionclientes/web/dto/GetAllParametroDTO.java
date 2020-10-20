package com.octagon.gestionclientes.web.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GetAllParametroDTO implements Serializable {
    private Set<GetParametroDTO> tipoPersona = new HashSet<>();
    private Set<GetParametroDTO> estadoCliente = new HashSet<>();
    private Set<GetParametroDTO> tipoTelefono = new HashSet<>();
    private Set<GetParametroDTO> tipoDomicilio = new HashSet<>();
    private Set<GetParametroDTO> tipoCliente = new HashSet<>();
    private Set<GetParametroDTO> genero = new HashSet<>();

    public Set<GetParametroDTO> getTipoPersona() {
        return this.tipoPersona;
    }

    public void addTipoPersona(GetParametroDTO tipoPersona) {
        this.tipoPersona.add(tipoPersona);
    }

    public Set<GetParametroDTO> getEstadoCliente() {
        return this.estadoCliente;
    }

    public void addEstadoCliente(GetParametroDTO estadoCliente) {
        this.estadoCliente.add(estadoCliente);
    }

    public Set<GetParametroDTO> getTipoTelefono() {
        return this.tipoTelefono;
    }

    public void addTipoTelefono(GetParametroDTO tipoTelefono) {
        this.tipoTelefono.add(tipoTelefono);
    }

    public Set<GetParametroDTO> getTipoDomicilio() {
        return this.tipoDomicilio;
    }

    public void addTipoDomicilio(GetParametroDTO tipoDomicilio) {
        this.tipoDomicilio.add(tipoDomicilio);
    }

    public Set<GetParametroDTO> getTipoCliente() {
        return this.tipoCliente;
    }

    public void addTipoCliente(GetParametroDTO tipoCliente) {
        this.tipoCliente.add(tipoCliente);
    }

    public Set<GetParametroDTO> getGenero() {
        return this.genero;
    }

    public void addGenero(GetParametroDTO genero) {
        this.genero.add(genero);
    }
}
