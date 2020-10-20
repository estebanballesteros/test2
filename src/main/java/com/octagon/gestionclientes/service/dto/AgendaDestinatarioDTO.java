package com.octagon.gestionclientes.service.dto;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AgendaDestinatario entity.
 */
public class AgendaDestinatarioDTO implements Serializable {

    private Long id;
    @NotNull
    private Long dni;

    private String alias;
    @NotNull
    private String telefono;
    @NotNull
    private Boolean favorito;

    private Long clienteBaseId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean isFavorito() { return favorito; }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Long getClienteBaseId() {
        return clienteBaseId;
    }

    public void setClienteBaseId(Long clienteBaseId) {
        this.clienteBaseId = clienteBaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgendaDestinatarioDTO agendaDestinatarioDTO = (AgendaDestinatarioDTO) o;
        if (agendaDestinatarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agendaDestinatarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgendaDestinatarioDTO{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", alias='" + getAlias() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", favorito='" + isFavorito() + "'" +
            ", clienteBase=" + getClienteBaseId() +
            "}";
    }
}
