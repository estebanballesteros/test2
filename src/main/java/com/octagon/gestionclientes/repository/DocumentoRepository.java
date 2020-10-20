package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.Documento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findAllByClienteCompletoId(Long id);
}
