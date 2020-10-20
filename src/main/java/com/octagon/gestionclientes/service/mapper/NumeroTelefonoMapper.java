package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.NumeroTelefono;
import com.octagon.gestionclientes.service.dto.NumeroTelefonoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity NumeroTelefono and its DTO NumeroTelefonoDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteBaseMapper.class,ParametroMapper.class})
public interface NumeroTelefonoMapper extends EntityMapper<NumeroTelefonoDTO, NumeroTelefono> {

    @Mapping(source = "clienteBase.id", target = "clienteBaseId")
    @Mapping(source = "tipoTelefono.id", target = "tipoTelefonoId")
    NumeroTelefonoDTO toDto(NumeroTelefono numeroTelefono);

    @Mapping(source = "clienteBaseId", target = "clienteBase")
    @Mapping(source = "tipoTelefonoId", target = "tipoTelefono")
    NumeroTelefono toEntity(NumeroTelefonoDTO numeroTelefonoDTO);

    default NumeroTelefono fromId(Long id) {
        if (id == null) {
            return null;
        }
        NumeroTelefono numeroTelefono = new NumeroTelefono();
        numeroTelefono.setId(id);
        return numeroTelefono;
    }
}
