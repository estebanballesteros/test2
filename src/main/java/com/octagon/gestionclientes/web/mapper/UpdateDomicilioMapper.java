package com.octagon.gestionclientes.web.mapper;

import com.octagon.gestionclientes.domain.Domicilio;
import com.octagon.gestionclientes.service.mapper.ClienteCompletoMapper;
import com.octagon.gestionclientes.service.mapper.EntityMapper;
import com.octagon.gestionclientes.service.mapper.ParametroMapper;
import com.octagon.gestionclientes.web.dto.UpdateDomicilioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClienteCompletoMapper.class, ParametroMapper.class})
public interface UpdateDomicilioMapper extends EntityMapper<UpdateDomicilioDTO, Domicilio> {

    @Mapping(source = "tipoDomicilio.codigoTabla", target = "codTablaTipoDomicilio")
    @Mapping(source = "tipoDomicilio.codigoItem", target = "codItemTipoDomicilio")
    @Mapping(target = "tipoDomicilio", ignore = true)
    Domicilio toEntity(UpdateDomicilioDTO domicilio);

    default Domicilio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Domicilio domicilio = new Domicilio();
        domicilio.setId(id);
        return domicilio;
    }
}
