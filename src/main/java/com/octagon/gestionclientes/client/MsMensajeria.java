package com.octagon.gestionclientes.client;

import com.octagon.gestionclientes.service.dto.MensajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mensajeria")
public interface MsMensajeria {
    @PostMapping("/api/mensajes")
    MensajeDTO createMensaje(MensajeDTO mensajeDTO);
}
