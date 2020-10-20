package com.octagon.gestionclientes.service.dto;


import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the ClienteBase entity, include the mobile number setted as principal.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    @NotEmpty
    @Column(length = 50, unique = true, nullable = false)
    private String dni;

    @Positive
    private Long idUsuario;

    @Size(max = 20)

    @Size(max = 240)
    private String alias;

    private Instant fechaRegistro;

    private Instant fechaBaja;

    @Size(max = 20)
    private String nombre;

    @Size(max = 20)
    private String apellido;

    @NotEmpty
    @Size(max = 60)
    private String origenRegistro;

    private Integer codTablaTipoCliente;

    private Integer codItemTipoCliente;

    private Integer codTablaEstadoCliente;

    private Integer codItemEstadoCliente;

    private Long tipoClienteId;

    private Long estadoClienteId;

    //TODO: Se descomenta temporalmente hasta que el ATM permita ingresar numero de telefono
    //@NotEmpty
    private String numeroTelefono;

    public ClienteDTO() {
    }

    public ClienteDTO(@NotNull String dni) {
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

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }


    public ClienteDTO(ClienteBaseDTO clienteBaseDTO, String numeroTelefono) {
        this.id = clienteBaseDTO.getId();
        this.dni = clienteBaseDTO.getDni();
        this.idUsuario = clienteBaseDTO.getIdUsuario();
        this.alias = clienteBaseDTO.getAlias();
        this.fechaRegistro = clienteBaseDTO.getFechaRegistro();
        this.fechaBaja = clienteBaseDTO.getFechaBaja();
        this.nombre = clienteBaseDTO.getNombre();
        this.apellido = clienteBaseDTO.getApellido();
        this.origenRegistro = clienteBaseDTO.getOrigenRegistro();
        this.codTablaTipoCliente = clienteBaseDTO.getCodTablaTipoCliente();
        this.codItemTipoCliente = clienteBaseDTO.getCodItemTipoCliente();
        this.codTablaEstadoCliente = clienteBaseDTO.getCodTablaEstadoCliente();
        this.codItemEstadoCliente = clienteBaseDTO.getCodItemEstadoCliente();
        this.tipoClienteId = clienteBaseDTO.getTipoClienteId();
        this.estadoClienteId = clienteBaseDTO.getEstadoClienteId();
        this.numeroTelefono = numeroTelefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteDTO clienteBaseDTO = (ClienteDTO) o;
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
            ", tipoCliente=" + getTipoClienteId() +
            ", estadoCliente=" + getEstadoClienteId() +
            ", numeroTelefono=" + getNumeroTelefono() +
            "}";
    }
}
