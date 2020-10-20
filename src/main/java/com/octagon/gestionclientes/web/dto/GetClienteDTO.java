package com.octagon.gestionclientes.web.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GetClienteDTO implements Serializable {
    private Long id;

    private Long clienteBaseId;

    private Instant fechaRegistro;

    private Instant fechaBaja;

    private Date fechaNacimiento;

    private String razonSocial;

    private Long cuilCuit;

    private String infoAdicional;

    private Boolean pep;

    private Boolean participaFidelizar;

    private String alias;

    private String nombre;

    private String apellido;

    private String nacionalidad;

    private Long dni;

    private String nombreApoderado;;

    private String apellidoApoderado;

    private Long cuitApoderado;


    private Set<GetNumeroTelefonoDTO> telefonos = new HashSet<>();

    private Set<GetCorreoElectronicoDTO> correos = new HashSet<>();

    private Set<GetDomicilioDTO> domicilios = new HashSet<>();

    private GetParametroDTO tipoPersona;

    private GetParametroDTO estado;

    private GetParametroDTO tipoCliente;

    private GetParametroDTO genero;

    private String authorityName;

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

    public Long getClienteBaseId() {
        return clienteBaseId;
    }

    public void setClienteBaseId(Long clienteBaseId) {
        this.clienteBaseId = clienteBaseId;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public GetParametroDTO getGenero() {
        return genero;
    }

    public void setGenero(GetParametroDTO genero) {
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

    public Set<GetNumeroTelefonoDTO> getTelefonos() {
        return this.telefonos;
    }

    public void setTelefonos(Set<GetNumeroTelefonoDTO> telefonos) {
        this.telefonos = telefonos;
    }

    public void addTelefono(GetNumeroTelefonoDTO numeroTelefono) {
        this.telefonos.add(numeroTelefono);
    }

    public Set<GetCorreoElectronicoDTO> getCorreos() {
        return this.correos;
    }

    public void setCorreos(Set<GetCorreoElectronicoDTO> correos) {
        this.correos = correos;
    }

    public void addCorreo(GetCorreoElectronicoDTO correoElectronico) {
        this.correos.add(correoElectronico);
    }

    public Set<GetDomicilioDTO> getDomicilios() {
        return this.domicilios;
    }

    public void setDomicilios(Set<GetDomicilioDTO> domicilios) {
        this.domicilios = domicilios;
    }

    public void addDomicilio(GetDomicilioDTO domicilio) {
        this.domicilios.add(domicilio);
    }

    public GetParametroDTO getEstado() {
        return this.estado;
    }

    public void setEstado(GetParametroDTO estado) {
        this.estado = estado;
    }

    public GetParametroDTO getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(GetParametroDTO tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public GetParametroDTO getTipoPersona() {
        return this.tipoPersona;
    }

    public void setTipoPersona(GetParametroDTO tipoPersona) {
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

    public String getNombreApoderado() {
        return nombreApoderado;
    }

    public void setNombreApoderado(String nombreApoderado) {
        this.nombreApoderado = nombreApoderado;
    }

    public String getApellidoApoderado() {
        return apellidoApoderado;
    }

    public void setApellidoApoderado(String apellidoApoderado) {
        this.apellidoApoderado = apellidoApoderado;
    }

    public Long getCuitApoderado() {
        return cuitApoderado;
    }

    public void setCuitApoderado(Long cuitApoderado) {
        this.cuitApoderado = cuitApoderado;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
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
        return "GetClienteDTO";
        /*return "ClienteCompletoDTO{" +
            "id=" + getId() +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", cuilCuit=" + getCuilCuit() +
            ", infoAdicional='" + getInfoAdicional() + "'" +
            ", genero='" + getGenero() + "'" +
            ", pep='" + isPep() + "'" +
            ", participaFidelizar='" + isParticipaFidelizar() + "'" +
            "}";*/
    }
}
