package com.octagon.gestionclientes.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the NumeroTelefono entity.
 */
public class NumeroTelefonoDTO implements Serializable {

    private Long id;

    private String numeroTelefono;

    private Boolean principal;

    private Boolean validado = false;

    private Long clienteBaseId;

    private Integer codTablaTipoTelefono;

    private Integer codItemTipoTelefono;

    private Long tipoTelefonoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Boolean isValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Long getClienteBaseId() {
        return clienteBaseId;
    }

    public void setClienteBaseId(Long clienteBaseId) {
        this.clienteBaseId = clienteBaseId;
    }

    public Integer getCodTablaTipoTelefono() {
        return codTablaTipoTelefono;
    }

    public void setCodTablaTipoTelefono(Integer codTablaTipoTelefono) {
        this.codTablaTipoTelefono = codTablaTipoTelefono;
    }

    public Integer getCodItemTipoTelefono() {
        return codItemTipoTelefono;
    }

    public void setCodItemTipoTelefono(Integer codItemTipoTelefono) {
        this.codItemTipoTelefono = codItemTipoTelefono;
    }

    public Long getTipoTelefonoId() {
        return tipoTelefonoId;
    }

    public void setTipoTelefonoId(Long tipoTelefonoId) {
        this.tipoTelefonoId = tipoTelefonoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumeroTelefonoDTO numeroTelefonoDTO = (NumeroTelefonoDTO) o;
        if (numeroTelefonoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numeroTelefonoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NumeroTelefonoDTO{" +
            "id=" + getId() +
            ", numeroTelefono='" + getNumeroTelefono() + "'" +
            ", principal='" + isPrincipal() + "'" +
            ", validado='" + isValidado() + "'" +
            ", clienteBase=" + getClienteBaseId() +
            ", tipoTelefonoId" + getTipoTelefonoId() +
            "}";
    }
}
