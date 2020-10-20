package com.octagon.gestionclientes.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CorreoElectronico entity.
 */
public class CorreoElectronicoDTO implements Serializable {

    private Long id;

    private String correoElectronico;

    private Boolean validado;

    private Boolean principal;


    private Long clienteBaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Boolean isValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
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

        CorreoElectronicoDTO correoElectronicoDTO = (CorreoElectronicoDTO) o;
        if (correoElectronicoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correoElectronicoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorreoElectronicoDTO{" +
            "id=" + getId() +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", validado='" + isValidado() + "'" +
            ", principal='" + isPrincipal() + "'" +
            ", clienteBase=" + getClienteBaseId() +
            "}";
    }
}
