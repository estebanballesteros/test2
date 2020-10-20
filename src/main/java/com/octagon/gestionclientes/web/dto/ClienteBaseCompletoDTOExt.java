package com.octagon.gestionclientes.web.dto;

import com.octagon.gestionclientes.config.Constants;
import com.octagon.gestionclientes.service.dto.DomicilioDTO;
import com.octagon.gestionclientes.service.dto.NumeroTelefonoDTO;
import com.octagon.gestionclientes.service.dto.UserDTO;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the ClienteCompleto entity. THIIS IS USED FOR APP MOBILE. KEEP IN MIND REUSE ClienteDTOExt in the future
 */
public class ClienteBaseCompletoDTOExt implements Serializable {

    /**
     * Cliente Base
     */

    private Long idClienteBase;

    private Long idUsuario;

    @Size(min = 1, max = 240)
    private String alias;

    private Instant fechaRegistroClienteBase;

    private Instant fechaBajaClienteBase;

    @Size(min = 1, max = 240)
    private String nombre;

    @Size(min = 1, max = 240)
    private String apellido;

    @Size(max = 60)
    private String origenRegistro;

    private String tipoCliente;

    private String estadoCliente;


    /**
     * Cliente Completo
     */
    private Long idClienteCompleto;
    private Instant fechaRegistroClienteCompleto;

    private Instant fechaBajaClienteCompleto;

    private Date fechaNacimiento;

    @Size(max = 60)
    private String razonSocial;

    private Long cuilCuit;

    @Size(max = 254)
    private String infoAdicional;

    @Size(max = 60)
    private String genero;

    private Boolean pep = false;

    private Boolean participaFidelizar = false;

    private String tipoPersona;

    @Size(min = 3, max = 40)
    @Pattern(regexp = Constants.ONLY_LETTERS_REGEX)
    private String nacionalidad;

    @Size(min = 7, max = 8)
    @Column(length = 50, unique = true, nullable = false)
    private String dni;

    @NotNull
    private List<NumeroTelefonoDTO> telefono;

    @NotNull
    private List<DomicilioDTO> domicilioDTO;

//    Solo para Persona Juridica
    private String nombreApoderado;

    private String apellidoApoderado;

    private Long cuitApoderado;

    private String email;


    public Long getIdClienteBase() {
        return idClienteBase;
    }

    public void setIdClienteBase(Long idClienteBase) {
        this.idClienteBase = idClienteBase;
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

    public Instant getFechaRegistroClienteBase() {
        return fechaRegistroClienteBase;
    }

    public void setFechaRegistroClienteBase(Instant fechaRegistroClienteBase) {
        this.fechaRegistroClienteBase = fechaRegistroClienteBase;
    }

    public Instant getFechaBajaClienteBase() {
        return fechaBajaClienteBase;
    }

    public void setFechaBajaClienteBase(Instant fechaBajaClienteBase) {
        this.fechaBajaClienteBase = fechaBajaClienteBase;
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

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(String estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public Long getIdClienteCompleto() {
        return idClienteCompleto;
    }

    public void setIdClienteCompleto(Long idClienteCompleto) {
        this.idClienteCompleto = idClienteCompleto;
    }

    public Instant getFechaRegistroClienteCompleto() {
        return fechaRegistroClienteCompleto;
    }

    public void setFechaRegistroClienteCompleto(Instant fechaRegistroClienteCompleto) {
        this.fechaRegistroClienteCompleto = fechaRegistroClienteCompleto;
    }

    public Instant getFechaBajaClienteCompleto() {
        return fechaBajaClienteCompleto;
    }

    public void setFechaBajaClienteCompleto(Instant fechaBajaClienteCompleto) {
        this.fechaBajaClienteCompleto = fechaBajaClienteCompleto;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getPep() {
        return pep;
    }

    public void setPep(Boolean pep) {
        this.pep = pep;
    }

    public Boolean getParticipaFidelizar() {
        return participaFidelizar;
    }

    public void setParticipaFidelizar(Boolean participaFidelizar) {
        this.participaFidelizar = participaFidelizar;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public List<NumeroTelefonoDTO> getTelefono() {
        return telefono;
    }

    public void setTelefono(List<NumeroTelefonoDTO> telefono) {
        this.telefono = telefono;
    }

    public List<DomicilioDTO> getDomicilioDTO() {
        return domicilioDTO;
    }

    public void setDomicilioDTO(List<DomicilioDTO> domicilioDTO) {
        this.domicilioDTO = domicilioDTO;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
