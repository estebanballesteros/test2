package com.octagon.gestionclientes.web.mapper;

import com.octagon.gestionclientes.domain.NumeroTelefono;
import com.octagon.gestionclientes.service.mapper.ClienteCompletoMapper;
import com.octagon.gestionclientes.service.mapper.EntityMapper;
import com.octagon.gestionclientes.service.mapper.ParametroMapper;
import com.octagon.gestionclientes.web.dto.UpdateNumeroTelefonoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClienteCompletoMapper.class, ParametroMapper.class})
public interface UpdateNumeroTelefonoMapper extends EntityMapper<UpdateNumeroTelefonoDTO, NumeroTelefono> {

    @Mapping(source = "tipoTelefono.codigoTabla", target = "codTablaTipoTelefono")
    @Mapping(source = "tipoTelefono.codigoItem", target = "codItemTipoTelefono")
    @Mapping(target = "tipoTelefono", ignore = true)
    NumeroTelefono toEntity(UpdateNumeroTelefonoDTO numeroTelefono);

    default NumeroTelefono fromId(Long id) {
        if (id == null) {
            return null;
        }
        NumeroTelefono telefono = new NumeroTelefono();
        telefono.setId(id);
        return telefono;
    }
}
