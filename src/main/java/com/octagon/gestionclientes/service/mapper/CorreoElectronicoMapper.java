package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.CorreoElectronico;
import com.octagon.gestionclientes.service.dto.CorreoElectronicoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity CorreoElectronico and its DTO CorreoElectronicoDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteBaseMapper.class})
public interface CorreoElectronicoMapper extends EntityMapper<CorreoElectronicoDTO, CorreoElectronico> {

    @Mapping(source = "clienteBase.id", target = "clienteBaseId")
    CorreoElectronicoDTO toDto(CorreoElectronico correoElectronico);

    @Mapping(source = "clienteBaseId", target = "clienteBase")
    CorreoElectronico toEntity(CorreoElectronicoDTO correoElectronicoDTO);

    default CorreoElectronico fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorreoElectronico correoElectronico = new CorreoElectronico();
        correoElectronico.setId(id);
        return correoElectronico;
    }
}
