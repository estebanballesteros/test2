package com.octagon.gestionclientes.web.mapper;

import com.octagon.gestionclientes.domain.CorreoElectronico;
import com.octagon.gestionclientes.service.mapper.ClienteCompletoMapper;
import com.octagon.gestionclientes.service.mapper.EntityMapper;
import com.octagon.gestionclientes.web.dto.UpdateCorreoElectronicoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClienteCompletoMapper.class})
public interface UpdateCorreoElectronicoMapper extends EntityMapper<UpdateCorreoElectronicoDTO, CorreoElectronico> {

    CorreoElectronico toEntity(UpdateCorreoElectronicoDTO updateCorreoElectronicoDTO);

    default CorreoElectronico fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorreoElectronico correo = new CorreoElectronico();
        correo.setId(id);
        return correo;
    }
}
