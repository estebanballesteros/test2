package com.octagon.gestionclientes.web.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UpdateClienteDTO implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private Instant fechaRegistro;

    private Instant fechaBaja;

    @NotNull
    private Date fechaNacimiento;

    private String razonSocial;

    private Long cuilCuit;

    private String infoAdicional;

    private Boolean pep;

    private Boolean participaFidelizar;

    @NotNull
    private String alias;

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @NotNull
    private String nacionalidad;

    private Long dni;

    @NotEmpty
    @Valid
    private Set<UpdateNumeroTelefonoDTO> telefonos = new HashSet<>();

    @NotEmpty
    @Valid
    private Set<UpdateCorreoElectronicoDTO> correos = new HashSet<>();

    @NotEmpty
    @Valid
    private Set<UpdateDomicilioDTO> domicilios = new HashSet<>();

    @NotNull
    @Valid
    private UpdateParametroDTO tipoPersona;

    @NotNull
    @Valid
    private UpdateParametroDTO estado;

    @NotNull
    @Valid
    private UpdateParametroDTO tipoCliente;

    @NotNull
    @Valid
    private UpdateParametroDTO genero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Instant getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Instant fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getCuilCuit() {
        return cuilCuit;
    }

    public void setCuilCuit(Long cuilCuit) {
        this.cuilCuit = cuilCuit;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public UpdateParametroDTO getGenero() {
        return genero;
    }

    public void setGenero(UpdateParametroDTO genero) {
        this.genero = genero;
    }

    public Boolean isPep() {
        return pep;
    }

    public void setPep(Boolean pep) {
        this.pep = pep;
    }

    public Boolean isParticipaFidelizar() {
        return participaFidelizar;
    }

    public void setParticipaFidelizar(Boolean participaFidelizar) {
        this.participaFidelizar = participaFidelizar;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Set<UpdateNumeroTelefonoDTO> getTelefonos() {
        return this.telefonos;
    }

    public void setTelefonos(Set<UpdateNumeroTelefonoDTO> telefonos) {
        this.telefonos = telefonos;
    }

    public void addNumeroTelefono(UpdateNumeroTelefonoDTO numeroTelefono) {
        this.telefonos.add(numeroTelefono);
    }

    public Set<UpdateCorreoElectronicoDTO> getCorreos() {
        return this.correos;
    }

    public void setCorreos(Set<UpdateCorreoElectronicoDTO> correos) {
        this.correos = correos;
    }

    public void addCorreoElectronico(UpdateCorreoElectronicoDTO correoElectronico) {
        this.correos.add(correoElectronico);
    }

    public Set<UpdateDomicilioDTO> getDomicilios() {
        return this.domicilios;
    }

    public void setDomicilios(Set<UpdateDomicilioDTO> domicilios) {
        this.domicilios = domicilios;
    }

    public void addDomicilio(UpdateDomicilioDTO domicilio) {
        this.domicilios.add(domicilio);
    }

    public UpdateParametroDTO getEstado() {
        return this.estado;
    }

    public void setEstado(UpdateParametroDTO estado) {
        this.estado = estado;
    }

    public UpdateParametroDTO getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(UpdateParametroDTO tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public UpdateParametroDTO getTipoPersona() {
        return this.tipoPersona;
    }

    public void setTipoPersona(UpdateParametroDTO tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Long getDni() {
        return this.dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GetClienteDTO clienteDTOExt = (GetClienteDTO) o;
        if (clienteDTOExt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTOExt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UpdateClienteDTO";
    }
}
