package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.AgendaDestinatario;
import com.octagon.gestionclientes.service.dto.AgendaDestinatarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AgendaDestinatario and its DTO AgendaDestinatarioDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteBaseMapper.class})
public interface AgendaDestinatarioMapper extends EntityMapper<AgendaDestinatarioDTO, AgendaDestinatario> {

    @Mapping(source = "clienteBase.id", target = "clienteBaseId")
    AgendaDestinatarioDTO toDto(AgendaDestinatario agendaDestinatario);

    @Mapping(source = "clienteBaseId", target = "clienteBase")
    AgendaDestinatario toEntity(AgendaDestinatarioDTO agendaDestinatarioDTO);

    default AgendaDestinatario fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgendaDestinatario agendaDestinatario = new AgendaDestinatario();
        agendaDestinatario.setId(id);
        return agendaDestinatario;
    }
}
