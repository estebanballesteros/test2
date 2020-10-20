package com.octagon.gestionclientes.client;

import com.octagon.gestionclientes.service.dto.PrestamoDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.dto.UsuarioFinancieraDTO;
import com.octagon.gestionclientes.service.utils.ApiException;
import com.octagon.gestionclientes.service.utils.FeignConfig;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "prestamos", configuration = FeignConfig.class)
public interface MsPrestamos {

    @GetMapping("api/prestamos/aprobadosOperadorOctagon/dni/{dniCliente}")
    ResponseDTO<List<PrestamoDTO>> getPrestamosAprobadosOpOctagonByDNI(@PathVariable(name = "dniCliente") String dniCliente) throws OctagonBusinessException;

    @PutMapping("api/prestamos/updatePrestamoToLiquidado/{prestamoId}")
    ResponseDTO<PrestamoDTO> liquidarPrestamo(@PathVariable(name = "prestamoId") Long prestamoId) throws OctagonBusinessException;

    @GetMapping("api/prestamos/aprobadosSupervisorOctagon/dni/{dniCliente}")
    ResponseDTO<List<PrestamoDTO>> getPRestamosAprobadosSupOctagonByDNI(@PathVariable(name = "dniCliente") String dniCliente) throws OctagonBusinessException;

    @GetMapping("/api/usuarioFinanciera/{clienteFinancieraId}")
    ResponseDTO<List<UsuarioFinancieraDTO>> getUsuariosFinanciera(@PathVariable(name = "clienteFinancieraId") Long clienteFinancieraId);

    @PostMapping("/api/usuarioFinanciera/{usuarioFinancieraId}/{clienteFinancieraId}/")
    ResponseDTO<UsuarioFinancieraDTO> createUsuarioFinanciera(@PathVariable(name = "usuarioFinancieraId") Long usuarioFinancieraId, @PathVariable(name = "clienteFinancieraId") Long clienteFinancieraId) throws ApiException;

    @GetMapping("/api/usuarioFinanciera/{clienteFinancieraId}/{usuarioId}")
    ResponseDTO<UsuarioFinancieraDTO> getUsuarioFinanciera(@PathVariable(name = "clienteFinancieraId") Long clienteFinancieraId, @PathVariable(name = "usuarioId") Long usuarioId);

}
