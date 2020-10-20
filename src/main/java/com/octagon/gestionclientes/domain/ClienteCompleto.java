package com.octagon.gestionclientes.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A ClienteCompleto.
 */
@Entity
@Table(name = "cliente_completo")
public class ClienteCompleto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

    @Column(name = "fecha_baja")
    private Instant fechaBaja;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "cuil_cuit")
    private Long cuilCuit;

    @Column(name = "info_adicional")
    private String infoAdicional;

    @Column(name = "pep")
    private Boolean pep;

    @Column(name = "participa_fidelizar")
    private Boolean participaFidelizar;

    @OneToOne(cascade = {CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(unique = true)
    private ClienteBase clienteBase;

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "clienteCompleto", orphanRemoval = true)
    private Set<Domicilio> domicilios = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Column(name = "cod_tabla_tipo_persona")
    private Integer codTablaTipoPersona;

    @Column(name = "cod_item_tipo_persona")
    private Integer codItemTipoPersona;

    @ManyToOne
    @JsonIgnoreProperties("clienteCompletos")
    private Parametro tipoPersona;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "cod_tabla_genero")
    private Integer codTablaGenero;

    @Column(name = "cod_item_genero")
    private Integer codItemGenero;

    @ManyToOne
    @JsonIgnoreProperties("clienteCompletos")
    private Parametro genero;

    @Column(name = "nombre_apoderado")
    private String nombreApoderado;

    @Column(name = "apellido_apoderado")
    private String apellidoApoderado;

    @Column(name = "cuit_apoderado")
    private Long cuitApoderado;

    @OneToMany(mappedBy = "clienteCompleto")
    private Set<Documento> documentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public ClienteCompleto fechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Instant getFechaBaja() {
        return fechaBaja;
    }

    public ClienteCompleto fechaBaja(Instant fechaBaja) {
        this.fechaBaja = fechaBaja;
        return this;
    }

    public void setFechaBaja(Instant fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public ClienteCompleto fechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public ClienteCompleto razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getCuilCuit() {
        return cuilCuit;
    }

    public ClienteCompleto cuilCuit(Long cuilCuit) {
        this.cuilCuit = cuilCuit;
        return this;
    }

    public void setCuilCuit(Long cuilCuit) {
        this.cuilCuit = cuilCuit;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public ClienteCompleto infoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
        return this;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public Boolean isPep() {
        return pep;
    }

    public ClienteCompleto pep(Boolean pep) {
        this.pep = pep;
        return this;
    }

    public void setPep(Boolean pep) {
        this.pep = pep;
    }

    public Boolean isParticipaFidelizar() {
        return participaFidelizar;
    }

    public ClienteCompleto participaFidelizar(Boolean participaFidelizar) {
        this.participaFidelizar = participaFidelizar;
        return this;
    }

    public void setParticipaFidelizar(Boolean participaFidelizar) {
        this.participaFidelizar = participaFidelizar;
    }

    public Set<Domicilio> getDomicilios() {
        return domicilios;
    }

    public ClienteCompleto domicilios(Set<Domicilio> domicilios) {
        this.domicilios = domicilios;
        return this;
    }

    public ClienteCompleto addDomicilios(Domicilio domicilio) {
        this.domicilios.add(domicilio);
        domicilio.setClienteCompleto(this);
        return this;
    }

    public ClienteCompleto removeDomicilios(Domicilio domicilio) {
        this.domicilios.remove(domicilio);
        domicilio.setClienteCompleto(null);
        return this;
    }

    public void setDomicilios(Set<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Parametro getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(Parametro tipoPersona) {
        this.tipoPersona = tipoPersona;
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

    public ClienteBase getClienteBase() {
      return clienteBase;
    }

    public void setClienteBase(ClienteBase clienteBase) {
      this.clienteBase = clienteBase;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Parametro getGenero() {
        return genero;
    }

    public void setGenero(Parametro genero) {
        this.genero = genero;
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

    public Set<Documento> getDocumentos() {
        return documentos;
    }

    public ClienteCompleto documentos(Set<Documento> documentos) {
        this.documentos = documentos;
        return this;
    }

    public ClienteCompleto addDocumento(Documento documento) {
        this.documentos.add(documento);
        documento.setClienteCompleto(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClienteCompleto clienteCompleto = (ClienteCompleto) o;
        if (clienteCompleto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteCompleto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteCompleto{" +
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
            "}";
    }
}
