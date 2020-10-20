package com.octagon.gestionclientes.web.mapper;

import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.service.mapper.EntityMapper;
import com.octagon.gestionclientes.web.dto.GetClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<GetClienteDTO, ClienteCompleto> {

    //@Mapping(source = "clienteBaseId", target = "clienteBase.id")
    //@Mapping(target = "domicilios", ignore = true)
    //ClienteCompleto toEntity(UpdateClienteDTO clienteDTO);

    @Mapping(source = "clienteBase.alias", target = "alias")
    @Mapping(source = "clienteBase.nombre", target = "nombre")
    @Mapping(source = "clienteBase.apellido", target = "apellido")
    @Mapping(source = "clienteBase.correosElectronicos", target = "correos")
    @Mapping(source = "domicilios", target = "domicilios")
    @Mapping(source = "clienteBase.numerosTelefonos", target = "telefonos")
    @Mapping(source = "clienteBase.tipoCliente", target = "tipoCliente")
    @Mapping(source = "clienteBase.estadoCliente", target = "estado")
    @Mapping(source = "clienteBase.dni", target = "dni")
    @Mapping(source = "tipoPersona", target = "tipoPersona")
    @Mapping(source = "clienteBase.id", target = "clienteBaseId")
    GetClienteDTO toDto(ClienteCompleto clienteCompleto);

    default ClienteCompleto fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClienteCompleto clienteCompleto = new ClienteCompleto();
        clienteCompleto.setId(id);
        return clienteCompleto;
    }
}
