package com.octagon.gestionclientes.client;


import com.octagon.gestionclientes.service.dto.AltaCuentaDTO;
import com.octagon.gestionclientes.service.dto.CuentaClienteCompletoDTO;
import com.octagon.gestionclientes.service.dto.CuentaDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.web.dto.CuentaDTOExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "msCuentaTransaccion")
public interface MsCuentaTransaccion {

    @PostMapping("api/cuenta/alta/moneyfi")
    ResponseDTO<CuentaDTOExt> altaCuentaMoneyFi(@RequestBody AltaCuentaDTO altaCuentaDTO);

    @PostMapping("api/cuenta/alta/prestamo")
    ResponseDTO<CuentaDTOExt> altaCuentaPrestamo(@RequestBody AltaCuentaDTO altaCuentaDTO);

    @PutMapping("api/cuenta/activar/{idClienteBase}")
    CuentaDTO activarCuenta(@PathVariable("idClienteBase") Long idClienteBase);

    @GetMapping("api/cuentas/cliente-base/{dni}")
    ResponseDTO<CuentaClienteCompletoDTO> getCuentasByDni(@PathVariable("dni") String dni);

}
