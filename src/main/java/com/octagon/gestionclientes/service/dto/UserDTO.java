package com.octagon.gestionclientes.service.dto;

import com.octagon.gestionclientes.config.Constants;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import static com.octagon.gestionclientes.service.utils.Constants.FISICA;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO implements Serializable {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = Constants.LOGIN_REGEX, message = "Patron invalido, solo numeros")
    @Size(min = 7, max = 11, message = "Cantidad de caracteres invalido")
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @Email
    @Size(max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 6)
    private String langKey;

    private Instant createdDate;

    private Instant lastModifiedDate;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = Constants.CELULAR_REGEX, message = "Patron invalido, solo numeros")
    @Size(min = 7, max = 14, message = "Cantidad de caracteres de celular invalido")
    private String celular;

    private Set<String> authorities;

    private String password;

    private String tipoPersona = FISICA;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    @ApiModelProperty(hidden = true)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @ApiModelProperty(hidden = true)
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCelular() { return celular; }

    public void setCelular(String celular) { this.celular = celular; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedDate=" + lastModifiedDate +
            ", celular=" + celular + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
