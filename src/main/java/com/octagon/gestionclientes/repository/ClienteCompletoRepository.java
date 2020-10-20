package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.domain.Parametro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ClienteCompleto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteCompletoRepository extends JpaRepository<ClienteCompleto, Long> {

    Page<ClienteCompleto> findAllByTipoPersona(List<Parametro>tipoPersona, Pageable pageable);

    Page<ClienteCompleto> findAllByTipoPersonaAndClienteBase(Parametro tipoPersona, ClienteBase clienteBase, Pageable pageable);

    Page<ClienteCompleto> findAllByTipoPersonaInAndClienteBaseIn(List<Parametro>tipoPersona, List<ClienteBase> clientesBase, Pageable pageable);
}
