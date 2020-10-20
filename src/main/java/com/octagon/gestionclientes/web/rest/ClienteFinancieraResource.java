package com.octagon.gestionclientes.web.rest;


import com.octagon.gestionclientes.service.ClienteService;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.dto.UserClienteBaseDTO;
import com.octagon.gestionclientes.web.rest.util.HeaderUtil;
import com.octagon.gestionclientes.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientesFinanciera")
public class ClienteFinancieraResource {


    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);
    private static final String ENTITY_NAME = ClienteFinancieraResource.class.getSimpleName();

    private final ClienteService clienteService;


    public ClienteFinancieraResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    /**
     * Obtengo todos los cliente de la financiera
     *
     * @return ResponseDTO<Page <UserClienteBaseDTO>>
     */
    @GetMapping("/")
    @ApiOperation(value = "Obtengo todos los usuarios de la financiera", consumes = "PortalPagos")
    @PreAuthorize("hasPermission('ClienteFinancieraResource','getUsuariosFinanaciera')")
    public ResponseEntity<ResponseDTO<List<UserClienteBaseDTO>>> getUsuariosFinanaciera() {
        log.debug("REST request to get a page of Usuarios Fianciera");
        List<UserClienteBaseDTO> userClienteBaseDTOList;
        try {
            userClienteBaseDTOList = clienteService.getUsuariosFinanaciera();
        } catch (OctagonBusinessException e) {
            return ResponseEntity.ok(e.getResponseDTO());
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(false, ex));
        }

        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "/api/clientesFinanciera");
        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(true, ResponseStatus.OK, userClienteBaseDTOList));
    }


    /**
     * Creo un usuario de la financiera: usuario(gateway), clienteBase(gestionCliente) y usuarioFinanciera(prestamos)
     * @param usuarioFinancieraDTO
     * @return ResponseDTO<UserClienteBaseDTO>
     */
    @PostMapping("/")
    @ApiOperation(value = "Creo los usuarios de la financiera. Solo el administrador del portal de pagos, es decir la financiera, puede consumir este servicio.",notes = "Consumido desde el Portal Pagos.")
    @PreAuthorize("hasPermission('ClienteFinancieraResource','createUsuarioFinanaciera')")
    public ResponseEntity<ResponseDTO<UserClienteBaseDTO>> createUsuarioFinanaciera(@RequestBody  @Validated UserClienteBaseDTO usuarioFinancieraDTO) {
        log.debug("REST request to create usuario,clienteBase,usuarioFinanciera {}", usuarioFinancieraDTO);
        UserClienteBaseDTO userClienteBaseDTO;
        try {
            userClienteBaseDTO = clienteService.createUsuarioFinanaciera(usuarioFinancieraDTO);
        } catch (OctagonBusinessException e) {
            return ResponseEntity.ok(e.getResponseDTO());
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(false, ex));
        }


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userClienteBaseDTO.getClienteBaseDTO().toString()))
            .body(new ResponseDTO(true, ResponseStatus.OK, "El usuario de la financiera se creo correctamente", userClienteBaseDTO));
    }

    /**
     * Obtengo el cliente buscado por dni
     *
     * @return ResponseDTO<Page < UserClienteBaseDTO>>
     */
    @GetMapping("/dni/{dni}")
    @ApiOperation(value = "Obtengo el usuarios de la financiera buscado por dni", consumes = "PortalPagos")
    @PreAuthorize("hasPermission('ClienteFinancieraResource','getUsuarioFinanacieraByDni')")
    public ResponseEntity<ResponseDTO<UserClienteBaseDTO>> getUsuarioFinanacieraByDni(@PathVariable String dni) {
        log.debug("REST request to get Usuarios Fianciera by dni {}", dni);
        UserClienteBaseDTO userClienteBaseDTO;
        try {
            userClienteBaseDTO = clienteService.getUsuariosFinanacieraByDni(dni);
        } catch (OctagonBusinessException e) {
            return ResponseEntity.ok(e.getResponseDTO());
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(false, ex));
        }

        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "/api/clientesFinanciera/dni/");
        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(true, ResponseStatus.OK, userClienteBaseDTO));
    }


    /**
     * Activar o Desactivar el usario de la financiera
     *
     * @param dni
     * @param activate(true or false)
     * @return ResponseDTO<UserClienteBaseDTO>
     */
    @PutMapping("/activate")
    @ApiOperation(value = "Activo o Desactivo el usuarios de la financiera por dni", consumes = "PortalPagos")
    @PreAuthorize("hasPermission('ClienteFinancieraResource','activateUsuarioFinancieraByDni')")
    public ResponseEntity<ResponseDTO<UserClienteBaseDTO>> activateUsuarioFinancieraByDni(@RequestParam String dni, @RequestParam Boolean activate) {
        log.debug("REST request to activate Usuarios Fianciera by dni {}", dni);
        UserClienteBaseDTO userClienteBaseDTO;
        try {
            userClienteBaseDTO = clienteService.activateUsuarioFinancieraByDni(dni, activate);
        } catch (OctagonBusinessException e) {
            return ResponseEntity.ok(e.getResponseDTO());
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(new ResponseDTO(false, ex));
        }

        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "/dni/{dni}/activate/{activate}");
        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(true, ResponseStatus.OK, userClienteBaseDTO));
    }


}
