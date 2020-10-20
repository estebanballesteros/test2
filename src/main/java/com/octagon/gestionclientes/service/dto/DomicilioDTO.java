package com.octagon.gestionclientes.service.dto;

import com.octagon.gestionclientes.config.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Domicilio entity.
 */
public class DomicilioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = Constants.ONLY_LETTERS_REGEX)
    private String pais;

    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = Constants.ONLY_LETTERS_REGEX)
    private String provincia;

    @NotNull
    @Size(min = 3, max = 40)
    private String localidad;

    @Size(max = 40)
    private String barrio;

    @NotNull
    @Size(min = 1, max = 240)
    private String calle;

    @NotNull
    @Size(min = 1, max = 10)
    private String numeroCalle;

    @Size(max = 240)
    @Pattern(regexp = Constants.ONLY_NUMBERS_REGEX)
    private String piso;

    @Size(max = 240)
    private String departamento;

    @NotNull
    @Size(min = 3, max = 10)
    private Integer codigoPostal;

    private Boolean principal;

    @Size(min = 1, max = 240)
    private String coordenadaX;

    @Size(min = 1, max = 240)
    private String coordenadaY;

    private String observacion;

    @Size(min = 1, max = 240)
    private String entreCalleA;

    @Size(min = 1, max = 240)
    private String entreCalleB;

    private Integer codTablaTipoDomicilio;

    private Integer codItemTipoDomicilio;

    private Long tipoDomicilioId;

    private Long clienteCompletoId;

    private Long domicilioId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(String coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public String getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(String coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEntreCalleA() {
        return entreCalleA;
    }

    public void setEntreCalleA(String entreCalleA) {
        this.entreCalleA = entreCalleA;
    }

    public String getEntreCalleB() {
        return entreCalleB;
    }

    public void setEntreCalleB(String entreCalleB) {
        this.entreCalleB = entreCalleB;
    }

    public Integer getCodTablaTipoDomicilio() {
        return codTablaTipoDomicilio;
    }

    public void setCodTablaTipoDomicilio(Integer codTablaTipoDomicilio) {
        this.codTablaTipoDomicilio = codTablaTipoDomicilio;
    }

    public Integer getCodItemTipoDomicilio() {
        return codItemTipoDomicilio;
    }

    public void setCodItemTipoDomicilio(Integer codItemTipoDomicilio) {
        this.codItemTipoDomicilio = codItemTipoDomicilio;
    }

    public Long getTipoDomicilioId() {
        return tipoDomicilioId;
    }

    public void setTipoDomicilioId(Long tipoDomicilioId) {
        this.tipoDomicilioId = tipoDomicilioId;
    }

    public Long getClienteCompletoId() {
        return clienteCompletoId;
    }

    public void setClienteCompletoId(Long clienteCompletoId) {
        this.clienteCompletoId = clienteCompletoId;
    }

    public Long getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(Long parametroId) {
        this.domicilioId = parametroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomicilioDTO domicilioDTO = (DomicilioDTO) o;
        if (domicilioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domicilioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DomicilioDTO{" +
            "id=" + getId() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", barrio='" + getBarrio() + "'" +
            ", calle='" + getCalle() + "'" +
            ", numeroCalle='" + getNumeroCalle() + "'" +
            ", piso='" + getPiso() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", codigoPostal=" + getCodigoPostal() +
            ", principal='" + isPrincipal() + "'" +
            ", coordenadaX='" + getCoordenadaX() + "'" +
            ", coordenadaY='" + getCoordenadaY() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", entreCalleA='" + getEntreCalleA() + "'" +
            ", entreCalleB='" + getEntreCalleB() + "'" +
            ", codTablaTipoDomicilio=" + getCodTablaTipoDomicilio() +
            ", codItemTipoDomicilio=" + getCodItemTipoDomicilio() +
            ", clienteCompleto=" + getClienteCompletoId() +
            ", domicilio=" + getDomicilioId() +
            "}";
    }
}
