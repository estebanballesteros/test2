package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.Parametro;
import com.octagon.gestionclientes.service.dto.ParametroDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Parametro and its DTO ParametroDTO.
 */
@Mapper(componentModel = "spring")
public interface ParametroMapper extends EntityMapper<ParametroDTO, Parametro> {



    default Parametro fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parametro parametro = new Parametro();
        parametro.setId(id);
        return parametro;
    }
}
