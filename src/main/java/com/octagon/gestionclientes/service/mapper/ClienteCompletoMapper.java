package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.dto.ClienteCompletoDTO;
import com.octagon.gestionclientes.web.dto.ClienteBaseCompletoDTOExt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ClienteCompleto and its DTO ClienteCompletoDTO.
 */
@Mapper(componentModel = "spring", uses = {ParametroMapper.class})
public interface ClienteCompletoMapper extends EntityMapper<ClienteCompletoDTO, ClienteCompleto> {

    @Mapping(source = "clienteBaseId", target = "clienteBase.id")
    @Mapping(target = "domicilios", ignore = true)
    @Mapping(source = "tipoPersonaId", target = "tipoPersona")
    @Mapping(source = "tipoGeneroId", target = "genero")
    ClienteCompleto toEntity(ClienteCompletoDTO clienteCompletoDTO);

    @Mapping(source = "clienteBase.id", target = "clienteBaseId")
    @Mapping(source = "tipoPersona.id", target = "tipoPersonaId")
    @Mapping(source = "genero.id", target = "tipoGeneroId")
    ClienteCompletoDTO toDto(ClienteCompleto clienteCompleto);

    default ClienteCompleto fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClienteCompleto clienteCompleto = new ClienteCompleto();
        clienteCompleto.setId(id);
        return clienteCompleto;
    }
    @Mapping(source = "clienteBaseDTO.id", target = "idClienteBase")
    @Mapping(source = "clienteCompletoDTO.id", target = "idClienteCompleto")
    ClienteBaseCompletoDTOExt toDto(ClienteBaseDTO clienteBaseDTO, ClienteCompletoDTO clienteCompletoDTO, String estadoCliente, String tipoCliente);
}

/*@Mapping(source = "clienteBase.nombre", target = "nombre")
@Mapping(source = "clienteBase.apellido", target = "apellido")
@Mapping(source = "clienteBase.alias", target = "alias")*/
