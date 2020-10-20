package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ClienteBase and its DTO ClienteBaseDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteCompletoMapper.class, ParametroMapper.class})
public interface ClienteBaseMapper extends EntityMapper<ClienteBaseDTO, ClienteBase> {

    @Mapping(source = "tipoCliente.id", target = "tipoClienteId")
    @Mapping(source = "estadoCliente.id", target = "estadoClienteId")
    @Mapping(source = "clienteCompleto.id", target = "clienteCompletoId")
    @Mapping(source = "estadoCliente.descripcionLarga", target = "estadoClienteDescLarga")
    ClienteBaseDTO toDto(ClienteBase clienteBase);

    @Mapping(target = "agenda", ignore = true)
    @Mapping(target = "estadosHistoricos", ignore = true)
    @Mapping(target = "correosElectronicos", ignore = true)
    @Mapping(target = "numerosTelefonos", ignore = true)
    @Mapping(source = "tipoClienteId", target = "tipoCliente")
    @Mapping(source = "estadoClienteId", target = "estadoCliente")
    ClienteBase toEntity(ClienteBaseDTO clienteBaseDTO);

    default ClienteBase fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClienteBase clienteBase = new ClienteBase();
        clienteBase.setId(id);
        return clienteBase;
    }
}
