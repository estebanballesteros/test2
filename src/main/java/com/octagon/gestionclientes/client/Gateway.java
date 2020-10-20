package com.octagon.gestionclientes.client;


import com.octagon.gestionclientes.service.dto.AuthorityDTO;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import com.octagon.gestionclientes.service.dto.UserDTO;
import com.octagon.gestionclientes.service.dto.UserPortalPagosDTO;
import com.octagon.gestionclientes.service.dto.UserRespuestasDTO;
import com.octagon.gestionclientes.service.utils.ApiException;
import com.octagon.gestionclientes.service.utils.FeignConfig;
import com.octagon.gestionclientes.web.dto.AuthorityResourceEndpointDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;


@FeignClient(name = "gateway", configuration = FeignConfig.class)
public interface Gateway {

    @RequestMapping("api/users/{login}")
    UserDTO getUser(@PathVariable("login") String login);

    @PutMapping("api/users")
    UserDTO updateUser(@RequestBody UserDTO userDTO);

    @PostMapping("api/users/register")
    ResponseDTO<UserDTO> createUser(@RequestBody @Validated UserRespuestasDTO userRespuestasDTO) throws Exception;

    @GetMapping("api/seguridadPerfil/{authorityName}")
    ResponseDTO<Set<AuthorityResourceEndpointDTO>> getAuthorityResourceEndpointSetByAuth(@PathVariable("authorityName") String authorityName);

    @GetMapping("/api/users/dni/{dni}")
    ResponseDTO<UserDTO> getUserByDni(@PathVariable("dni") String dni) throws ApiException;

    @GetMapping("/api/authority")
    ResponseDTO<List<AuthorityDTO>> getAllAuthorities();

    @PostMapping("/api/users/registerPortalPagos")
    ResponseDTO<UserDTO> createUserPortalPagos(@RequestBody @Validated UserPortalPagosDTO userDTO) throws ApiException;

    @GetMapping("/api/users/id/{id}")
    ResponseDTO<UserDTO> getUserById(@PathVariable("id") Long id) throws ApiException;

    @PostMapping("/api/users/activatePortalPagos")
    ResponseDTO<UserDTO> activateUserPortalPagos(@RequestParam ("dni") String dni, @RequestParam ("activate") Boolean activate) throws ApiException;

}
