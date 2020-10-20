package com.octagon.gestionclientes.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A ClienteBase.
 */
@Entity
@Table(name = "cliente_base")
public class ClienteBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "alias")
    private String alias;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

    @Column(name = "fecha_baja")
    private Instant fechaBaja;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "origen_registro")
    private String origenRegistro;

    @Column(name = "cod_tabla_tipo_cliente")
    private Integer codTablaTipoCliente;

    @Column(name = "cod_item_tipo_cliente")
    private Integer codItemTipoCliente;

    @Column(name = "cod_tabla_estado_cliente")
    private Integer codTablaEstadoCliente;

    @Column(name = "cod_item_estado_cliente")
    private Integer codItemEstadoCliente;

    @OneToMany(mappedBy = "clienteBase")
    private Set<AgendaDestinatario> agenda = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "clienteBase")
    private Set<EstadoClienteHistorico> estadosHistoricos = new HashSet<>();

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "clienteBase", orphanRemoval = true)
    private Set<CorreoElectronico> correosElectronicos = new HashSet<>();

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "clienteBase", orphanRemoval = true)
    private Set<NumeroTelefono> numerosTelefonos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clienteBases")
    private Parametro tipoCliente;

    @OneToOne(mappedBy = "clienteBase")
    @JoinColumn(unique = true)
    private ClienteCompleto clienteCompleto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    @ManyToOne
    @JsonIgnoreProperties("clienteBases")
    private Parametro estadoCliente;

    public ClienteBase dni(String dni) {
        this.dni = dni;
        return this;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public ClienteBase idUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAlias() {
        return alias;
    }

    public ClienteBase alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public ClienteBase fechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Instant getFechaBaja() {
        return fechaBaja;
    }

    public ClienteBase fechaBaja(Instant fechaBaja) {
        this.fechaBaja = fechaBaja;
        return this;
    }

    public void setFechaBaja(Instant fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getNombre() {
        return nombre;
    }

    public ClienteBase nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public ClienteBase apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getOrigenRegistro() {
        return origenRegistro;
    }

    public ClienteBase origenRegistro(String origenRegistro) {
        this.origenRegistro = origenRegistro;
        return this;
    }

    public void setOrigenRegistro(String origenRegistro) {
        this.origenRegistro = origenRegistro;
    }

    public Integer getCodTablaTipoCliente() {
        return codTablaTipoCliente;
    }

    public ClienteBase codTablaTipoCliente(Integer codTablaTipoCliente) {
        this.codTablaTipoCliente = codTablaTipoCliente;
        return this;
    }

    public void setCodTablaTipoCliente(Integer codTablaTipoCliente) {
        this.codTablaTipoCliente = codTablaTipoCliente;
    }

    public Integer getCodItemTipoCliente() {
        return codItemTipoCliente;
    }

    public ClienteBase codItemTipoCliente(Integer codItemTipoCliente) {
        this.codItemTipoCliente = codItemTipoCliente;
        return this;
    }

    public void setCodItemTipoCliente(Integer codItemTipoCliente) {
        this.codItemTipoCliente = codItemTipoCliente;
    }

    public Integer getCodTablaEstadoCliente() {
        return codTablaEstadoCliente;
    }

    public ClienteBase codTablaEstadoCliente(Integer codTablaEstadoCliente) {
        this.codTablaEstadoCliente = codTablaEstadoCliente;
        return this;
    }

    public void setCodTablaEstadoCliente(Integer codTablaEstadoCliente) {
        this.codTablaEstadoCliente = codTablaEstadoCliente;
    }

    public Integer getCodItemEstadoCliente() {
        return codItemEstadoCliente;
    }

    public ClienteBase codItemEstadoCliente(Integer codItemEstadoCliente) {
        this.codItemEstadoCliente = codItemEstadoCliente;
        return this;
    }

    public void setCodItemEstadoCliente(Integer codItemEstadoCliente) {
        this.codItemEstadoCliente = codItemEstadoCliente;
    }

    public Set<AgendaDestinatario> getAgenda() {
        return agenda;
    }

    public ClienteBase agenda(Set<AgendaDestinatario> agendaDestinatarios) {
        this.agenda = agendaDestinatarios;
        return this;
    }

    public ClienteBase addAgenda(AgendaDestinatario agendaDestinatario) {
        this.agenda.add(agendaDestinatario);
        agendaDestinatario.setClienteBase(this);
        return this;
    }

    public ClienteBase removeAgenda(AgendaDestinatario agendaDestinatario) {
        this.agenda.remove(agendaDestinatario);
        agendaDestinatario.setClienteBase(null);
        return this;
    }

    public void setAgenda(Set<AgendaDestinatario> agendaDestinatarios) {
        this.agenda = agendaDestinatarios;
    }

    public Set<EstadoClienteHistorico> getEstadosHistoricos() {
        return estadosHistoricos;
    }

    public ClienteBase estadosHistoricos(Set<EstadoClienteHistorico> estadoClienteHistoricos) {
        this.estadosHistoricos = estadoClienteHistoricos;
        return this;
    }

    public ClienteBase addEstadosHistoricos(EstadoClienteHistorico estadoClienteHistorico) {
        this.estadosHistoricos.add(estadoClienteHistorico);
        return this;
    }

    public ClienteBase removeEstadosHistoricos(EstadoClienteHistorico estadoClienteHistorico) {
        this.estadosHistoricos.remove(estadoClienteHistorico);
        estadoClienteHistorico.setClienteBase(null);
        return this;
    }

    public void setEstadosHistoricos(Set<EstadoClienteHistorico> estadoClienteHistoricos) {
        this.estadosHistoricos = estadoClienteHistoricos;
    }

    public Set<CorreoElectronico> getCorreosElectronicos() {
        return correosElectronicos;
    }

    public ClienteBase correosElectronicos(Set<CorreoElectronico> correoElectronicos) {
        this.correosElectronicos = correoElectronicos;
        return this;
    }

    public ClienteBase addCorreosElectronicos(CorreoElectronico correoElectronico) {
        this.correosElectronicos.add(correoElectronico);
        correoElectronico.setClienteBase(this);
        return this;
    }

    public ClienteBase removeCorreosElectronicos(CorreoElectronico correoElectronico) {
        this.correosElectronicos.remove(correoElectronico);
        correoElectronico.setClienteBase(null);
        return this;
    }

    public void setCorreosElectronicos(Set<CorreoElectronico> correoElectronicos) {
        this.correosElectronicos = correoElectronicos;
    }

    public Set<NumeroTelefono> getNumerosTelefonos() {
        return numerosTelefonos;
    }

    public ClienteBase numerosTelefonos(Set<NumeroTelefono> numeroTelefonos) {
        this.numerosTelefonos = numeroTelefonos;
        return this;
    }

    public ClienteBase addNumerosTelefonos(NumeroTelefono numeroTelefono) {
        this.numerosTelefonos.add(numeroTelefono);
        numeroTelefono.setClienteBase(this);
        return this;
    }

    public ClienteBase removeNumerosTelefonos(NumeroTelefono numeroTelefono) {
        this.numerosTelefonos.remove(numeroTelefono);
        numeroTelefono.setClienteBase(null);
        return this;
    }

    public void setNumerosTelefonos(Set<NumeroTelefono> numeroTelefonos) {
        this.numerosTelefonos = numeroTelefonos;
    }

    public Parametro getTipoCliente() {
        return tipoCliente;
    }

    public ClienteBase tipoCliente(Parametro parametro) {
        this.tipoCliente = parametro;
        return this;
    }

    public void setTipoCliente(Parametro parametro) {
        this.tipoCliente = parametro;
        this.codTablaTipoCliente = parametro.getCodigoTabla();
        this.codItemTipoCliente = parametro.getCodigoItem();
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public ClienteCompleto getClienteCompleto() {
        return clienteCompleto;
    }

    public void setClienteCompleto(ClienteCompleto clienteCompleto) {
        this.clienteCompleto = clienteCompleto;
    }

    public Parametro getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(Parametro estadoCliente) {
        this.estadoCliente = estadoCliente;
        this.codTablaEstadoCliente = estadoCliente.getCodigoTabla();
        this.codItemEstadoCliente = estadoCliente.getCodigoItem();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClienteBase clienteBase = (ClienteBase) o;
        if (clienteBase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteBase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteBase{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", idUsuario=" + getIdUsuario() +
            ", alias='" + getAlias() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", origenRegistro='" + getOrigenRegistro() + "'" +
            ", codTablaTipoCliente=" + getCodTablaTipoCliente() +
            ", codItemTipoCliente=" + getCodItemTipoCliente() +
            ", codTablaEstadoCliente=" + getCodTablaEstadoCliente() +
            ", codItemEstadoCliente=" + getCodItemEstadoCliente() +
            "}";
    }
}
