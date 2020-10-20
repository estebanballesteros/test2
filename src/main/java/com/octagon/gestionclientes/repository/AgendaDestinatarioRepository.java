package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.AgendaDestinatario;
import com.octagon.gestionclientes.domain.ClienteBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AgendaDestinatario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaDestinatarioRepository extends JpaRepository<AgendaDestinatario, Long> {
    List<AgendaDestinatario> findByDniAndClienteBase(Long dni, ClienteBase clienteBase);
    Page<AgendaDestinatario> findByclienteBaseId(Long clienteBase, Pageable pageable);
}
