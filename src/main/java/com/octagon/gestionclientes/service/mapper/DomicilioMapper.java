package com.octagon.gestionclientes.service.mapper;

import com.octagon.gestionclientes.domain.Domicilio;
import com.octagon.gestionclientes.service.dto.DomicilioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Domicilio and its DTO DomicilioDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteCompletoMapper.class, ParametroMapper.class})
public interface DomicilioMapper extends EntityMapper<DomicilioDTO, Domicilio> {

    @Mapping(source = "clienteCompleto.id", target = "clienteCompletoId")
    @Mapping(source = "domicilio.id", target = "domicilioId")
    DomicilioDTO toDto(Domicilio domicilio);

    @Mapping(source = "clienteCompletoId", target = "clienteCompleto")
    @Mapping(source = "tipoDomicilioId", target = "tipoDomicilio")
    Domicilio toEntity(DomicilioDTO domicilioDTO);

    default Domicilio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Domicilio domicilio = new Domicilio();
        domicilio.setId(id);
        return domicilio;
    }
}
