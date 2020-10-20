package com.octagon.gestionclientes.web.dto;
import java.io.Serializable;

public class RespuestaDTO implements Serializable {
    private Integer status;

    private String mensaje;

    public void setStatus(Integer status) {
      this.status = status;
    }

    public Integer getStatus() {
      return this.status;
    }

    public void setMensaje(String mensaje) {
      this.mensaje = mensaje;
    }

    public String getMensaje() {
      return this.mensaje;
    }
}
