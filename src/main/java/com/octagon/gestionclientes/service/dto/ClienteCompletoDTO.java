package com.octagon.gestionclientes.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the ClienteCompleto entity.
 */
public class ClienteCompletoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fechaRegistro;

    private Instant fechaBaja;

    @NotNull
    private Date fechaNacimiento;

    @Size(max = 60)
    private String razonSocial;

    private Long cuilCuit;

    @Size(max = 254)
    private String infoAdicional;

    private Integer codTablaGenero;

    private Integer codItemGenero;

    @NotNull
    private Long tipoGeneroId;

    private Boolean pep;

    private Boolean participaFidelizar;

    private Integer codTablaTipoPersona;

    private Integer codItemTipoPersona;

    private Long tipoPersonaId;

    private String nacionalidad;

    private String nombreApoderado;

    private String apellidoApoderado;

    private Long cuitApoderado;


    @NotNull
    @Positive
    private Long clienteBaseId;

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

    public Long getClienteBaseId() {
        return clienteBaseId;
    }

    public void setClienteBaseId(Long clienteBaseId) {
        this.clienteBaseId = clienteBaseId;
    }

    public Integer getCodTablaTipoPersona() {
        return codTablaTipoPersona;
    }

    public void setCodTablaTipoPersona(Integer codTablaTipoPersona) {
        this.codTablaTipoPersona = codTablaTipoPersona;
    }

    public Integer getCodItemTipoPersona() {
        return codItemTipoPersona;
    }

    public void setCodItemTipoPersona(Integer codItemTipoPersona) {
        this.codItemTipoPersona = codItemTipoPersona;
    }

    public Long getTipoPersonaId() {
        return tipoPersonaId;
    }

    public void setTipoPersonaId(Long parametroId) {
        this.tipoPersonaId = parametroId;
    }

    public Integer getCodTablaGenero() {
        return codTablaGenero;
    }

    public void setCodTablaGenero(Integer codTablaGenero) {
        this.codTablaGenero = codTablaGenero;
    }

    public Integer getCodItemGenero() {
        return codItemGenero;
    }

    public void setCodItemGenero(Integer codItemGenero) {
        this.codItemGenero = codItemGenero;
    }

    public Long getTipoGeneroId() {
        return tipoGeneroId;
    }

    public void setTipoGeneroId(Long parametroId) {
        this.tipoGeneroId = parametroId;
    }

    public Boolean getPep() {
        return pep;
    }

    public Boolean getParticipaFidelizar() {
        return participaFidelizar;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteCompletoDTO clienteCompletoDTO = (ClienteCompletoDTO) o;
        if (clienteCompletoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteCompletoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteCompletoDTO{" +
            "id=" + getId() +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", cuilCuit=" + getCuilCuit() +
            ", infoAdicional='" + getInfoAdicional() + "'" +
            ", pep='" + isPep() + "'" +
            ", participaFidelizar='" + isParticipaFidelizar() + "'" +
            ", tipoPersona=" + getTipoPersonaId() +
            ", tipoGenero=" + getTipoGeneroId() +
            "}";
    }
}
