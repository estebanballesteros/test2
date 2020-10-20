package com.octagon.gestionclientes.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Domicilio.
 */
@Entity
@Table(name = "domicilio")
public class Domicilio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pais")
    private String pais;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "barrio")
    private String barrio;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numero_calle")
    private String numeroCalle;

    @Column(name = "piso")
    private String piso;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "codigo_postal")
    private Integer codigoPostal;

    @Column(name = "principal")
    private Boolean principal;

    @Column(name = "coordenada_x")
    private String coordenadaX;

    @Column(name = "coordenada_y")
    private String coordenadaY;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "entre_calle_a")
    private String entreCalleA;

    @Column(name = "entre_calle_b")
    private String entreCalleB;

    @Column(name = "cod_tabla_tipo_domicilio")
    private Integer codTablaTipoDomicilio;

    @Column(name = "cod_item_tipo_domicilio")
    private Integer codItemTipoDomicilio;

    @ManyToOne
    @JsonIgnoreProperties("domicilios")
    private ClienteCompleto clienteCompleto;

    @ManyToOne
    @JsonIgnoreProperties("domicilios")
    private Parametro tipoDomicilio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public Domicilio pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public Domicilio provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Domicilio localidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public Domicilio barrio(String barrio) {
        this.barrio = barrio;
        return this;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCalle() {
        return calle;
    }

    public Domicilio calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroCalle() {
        return numeroCalle;
    }

    public Domicilio numeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
        return this;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public String getPiso() {
        return piso;
    }

    public Domicilio piso(String piso) {
        this.piso = piso;
        return this;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public Domicilio departamento(String departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public Domicilio codigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public Domicilio principal(Boolean principal) {
        this.principal = principal;
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getCoordenadaX() {
        return coordenadaX;
    }

    public Domicilio coordenadaX(String coordenadaX) {
        this.coordenadaX = coordenadaX;
        return this;
    }

    public void setCoordenadaX(String coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public String getCoordenadaY() {
        return coordenadaY;
    }

    public Domicilio coordenadaY(String coordenadaY) {
        this.coordenadaY = coordenadaY;
        return this;
    }

    public void setCoordenadaY(String coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getObservacion() {
        return observacion;
    }

    public Domicilio observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEntreCalleA() {
        return entreCalleA;
    }

    public Domicilio entreCalleA(String entreCalleA) {
        this.entreCalleA = entreCalleA;
        return this;
    }

    public void setEntreCalleA(String entreCalleA) {
        this.entreCalleA = entreCalleA;
    }

    public String getEntreCalleB() {
        return entreCalleB;
    }

    public Domicilio entreCalleB(String entreCalleB) {
        this.entreCalleB = entreCalleB;
        return this;
    }

    public void setEntreCalleB(String entreCalleB) {
        this.entreCalleB = entreCalleB;
    }

    public Integer getCodTablaTipoDomicilio() {
        return codTablaTipoDomicilio;
    }

    public Domicilio codTablaTipoDomicilio(Integer codTablaTipoDomicilio) {
        this.codTablaTipoDomicilio = codTablaTipoDomicilio;
        return this;
    }

    public void setCodTablaTipoDomicilio(Integer codTablaTipoDomicilio) {
        this.codTablaTipoDomicilio = codTablaTipoDomicilio;
    }

    public Integer getCodItemTipoDomicilio() {
        return codItemTipoDomicilio;
    }

    public Domicilio codItemTipoDomicilio(Integer codItemTipoDomicilio) {
        this.codItemTipoDomicilio = codItemTipoDomicilio;
        return this;
    }

    public void setCodItemTipoDomicilio(Integer codItemTipoDomicilio) {
        this.codItemTipoDomicilio = codItemTipoDomicilio;
    }

    public ClienteCompleto getClienteCompleto() {
        return clienteCompleto;
    }

    public Domicilio clienteCompleto(ClienteCompleto clienteCompleto) {
        this.clienteCompleto = clienteCompleto;
        return this;
    }

    public void setClienteCompleto(ClienteCompleto clienteCompleto) {
        this.clienteCompleto = clienteCompleto;
    }

    public Parametro getTipoDomicilio() {
        return tipoDomicilio;
    }

    public Domicilio tipoDomicilio(Parametro tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
        return this;
    }

    public void setTipoDomicilio(Parametro tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
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
        Domicilio domicilio = (Domicilio) o;
        if (domicilio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domicilio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Domicilio{" +
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
            "}";
    }
}
