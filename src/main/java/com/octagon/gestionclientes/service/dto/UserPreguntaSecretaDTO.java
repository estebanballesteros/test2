package com.octagon.gestionclientes.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserPreguntaSecreta entity.
 */
public class UserPreguntaSecretaDTO implements Serializable {

    private Long id;

    private String respuesta;

    private Long preguntaSecretaId;

    private String preguntaSecretaStr;

    private Long userId;

    public UserPreguntaSecretaDTO() {
    }

    public UserPreguntaSecretaDTO(String respuesta, Long preguntaSecretaId) {
        this.respuesta = respuesta;
        this.preguntaSecretaId = preguntaSecretaId; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Long getPreguntaSecretaId() {
        return preguntaSecretaId;
    }

    public void setPreguntaSecretaId(Long preguntaSecretaId) {
        this.preguntaSecretaId = preguntaSecretaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPreguntaSecretaStr() {
        return preguntaSecretaStr;
    }

    public void setPreguntaSecretaStr(String preguntaSecretaStr) {
        this.preguntaSecretaStr = preguntaSecretaStr;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPreguntaSecretaDTO userPreguntaSecretaDTO = (UserPreguntaSecretaDTO) o;
        if (userPreguntaSecretaDTO.getId() == null || getId() == null) {
            return false;
        }

        return Objects.equals(getId(), userPreguntaSecretaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPreguntaSecretaDTO{" +
            "id=" + getId() +
            ", respuesta='" + getRespuesta() + "'" +
            ", preguntaSecreta=" + getPreguntaSecretaId() +
            ", user=" + getUserId() +
            "}";
    }


}
