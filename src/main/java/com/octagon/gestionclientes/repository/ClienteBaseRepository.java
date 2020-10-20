package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.ClienteBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ClienteBase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteBaseRepository extends JpaRepository<ClienteBase, Long> {
    Optional<ClienteBase> findByDni(String dni);
    Optional<ClienteBase> findByIdUsuario(Long idUsuario);
    Optional<ClienteBase>findByIdUsuarioAndDni(Long idUsuario, String dni);

}
