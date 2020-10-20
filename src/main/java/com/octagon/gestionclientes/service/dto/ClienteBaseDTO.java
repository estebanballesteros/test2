package com.octagon.gestionclientes.service.dto;


import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the ClienteBase entity.
 */
public class ClienteBaseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 7, max = 8)
    @Column(length = 50, unique = true, nullable = false)
    private String dni;

    @Positive
    private Long idUsuario;
    @Size(min = 1, max = 240)
    private String alias;

    private Instant fechaRegistro;

    private Instant fechaBaja;

    @NotNull
    @Size(min = 1, max = 240)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 240)
    private String apellido;

    @Size(max = 60)
    private String origenRegistro;

    private Integer codTablaTipoCliente;

    private Integer codItemTipoCliente;

    private Integer codTablaEstadoCliente;

    private Integer codItemEstadoCliente;

    private String estadoClienteDescLarga;

    private Long clienteCompletoId;

    private Long tipoClienteId;

    private Long estadoClienteId;

    public ClienteBaseDTO() {
    }

    public ClienteBaseDTO(@NotNull String dni) {
        this.dni = dni;
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

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getOrigenRegistro() {
        return origenRegistro;
    }

    public void setOrigenRegistro(String origenRegistro) {
        this.origenRegistro = origenRegistro;
    }

    public Integer getCodTablaTipoCliente() {
        return codTablaTipoCliente;
    }

    public void setCodTablaTipoCliente(Integer codTablaTipoCliente) {
        this.codTablaTipoCliente = codTablaTipoCliente;
    }

    public Integer getCodItemTipoCliente() {
        return codItemTipoCliente;
    }

    public void setCodItemTipoCliente(Integer codItemTipoCliente) {
        this.codItemTipoCliente = codItemTipoCliente;
    }

    public Integer getCodTablaEstadoCliente() {
        return codTablaEstadoCliente;
    }

    public void setCodTablaEstadoCliente(Integer codTablaEstadoCliente) {
        this.codTablaEstadoCliente = codTablaEstadoCliente;
    }

    public Integer getCodItemEstadoCliente() {
        return codItemEstadoCliente;
    }

    public void setCodItemEstadoCliente(Integer codItemEstadoCliente) {
        this.codItemEstadoCliente = codItemEstadoCliente;
    }

    public String getEstadoClienteDescLarga() {
        return estadoClienteDescLarga;
    }

    public void setEstadoClienteDescLarga(String estadoClienteDescLarga) {
        this.estadoClienteDescLarga = estadoClienteDescLarga;
    }

    public Long getClienteCompletoId() {
        return clienteCompletoId;
    }

    public void setClienteCompletoId(Long clienteCompletoId) {
        this.clienteCompletoId = clienteCompletoId;
    }

    public Long getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(Long parametroId) {
        this.tipoClienteId = parametroId;
    }

    public Long getEstadoClienteId() {
        return estadoClienteId;
    }

    public void setEstadoClienteId(Long estadoClienteId) {
        this.estadoClienteId = estadoClienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteBaseDTO clienteBaseDTO = (ClienteBaseDTO) o;
        if (clienteBaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteBaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteBaseDTO{" +
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
            ", clienteCompleto=" + getClienteCompletoId() +
            ", tipoCliente=" + getTipoClienteId() +
            ", estadoCliente=" + getEstadoClienteId() +
            "}";
    }
}
