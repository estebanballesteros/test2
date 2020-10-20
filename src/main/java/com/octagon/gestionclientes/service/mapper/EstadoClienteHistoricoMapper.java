package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.EstadoClienteHistorico;
import com.octagon.gestionclientes.service.dto.EstadoClienteHistoricoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity EstadoClienteHistorico and its DTO EstadoClienteHistoricoDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteBaseMapper.class, ParametroMapper.class})
public interface EstadoClienteHistoricoMapper extends EntityMapper<EstadoClienteHistoricoDTO, EstadoClienteHistorico> {

    @Mapping(source = "clienteBase.id", target = "clienteBaseId")
    @Mapping(source = "estadoCliente.id", target = "estadoClienteId")
    EstadoClienteHistoricoDTO toDto(EstadoClienteHistorico estadoClienteHistorico);

    @Mapping(source = "clienteBaseId", target = "clienteBase")
    @Mapping(source = "estadoClienteId", target = "estadoCliente")
    EstadoClienteHistorico toEntity(EstadoClienteHistoricoDTO estadoClienteHistoricoDTO);

    default EstadoClienteHistorico fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstadoClienteHistorico estadoClienteHistorico = new EstadoClienteHistorico();
        estadoClienteHistorico.setId(id);
        return estadoClienteHistorico;
    }
}
