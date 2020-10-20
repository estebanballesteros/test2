package com.octagon.gestionclientes.service;

import com.octagon.gestionclientes.service.dto.FiltroClienteDTO;
import com.octagon.gestionclientes.service.utils.OctagonBusinessException;
import com.octagon.gestionclientes.web.dto.GetClienteDTO;
import com.octagon.gestionclientes.web.dto.RespuestaDTO;
import com.octagon.gestionclientes.web.dto.UpdateClienteDTO;
import com.octagon.gestionclientes.web.dto.UserClienteBaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Optional<GetClienteDTO> findOneClienteCompleto(Long clienteCompletoId);

    Page<GetClienteDTO> findClientesByTipoPersona(FiltroClienteDTO filtroClienteDTO, Pageable pageable) throws OctagonBusinessException;

    List<UserClienteBaseDTO> getUsuariosFinanaciera() throws OctagonBusinessException;

    RespuestaDTO updateCliente(UpdateClienteDTO clienteCompletoDTOReq);

    UserClienteBaseDTO createUsuarioFinanaciera(UserClienteBaseDTO userClienteBaseDTO) throws OctagonBusinessException;

    UserClienteBaseDTO getUsuariosFinanacieraByDni(String dni) throws OctagonBusinessException;

    UserClienteBaseDTO activateUsuarioFinancieraByDni(String dni, Boolean activate) throws OctagonBusinessException;

}
