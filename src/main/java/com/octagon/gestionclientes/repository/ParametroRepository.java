package com.octagon.gestionclientes.repository;

import com.octagon.gestionclientes.domain.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Optional;


/**
 * Spring Data  repository for the Parametro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long> {
    Optional<Parametro> findByDescripcionLarga(String descripcionLarga);
    LinkedList<Parametro> findAll();
    LinkedList<Parametro> findByCodigoTabla(Integer codigoTabla);

    Optional<Parametro> findByCodigoTablaAndCodigoItem(Integer codigoTabla, Integer codigoItem);
    Optional<Parametro> findByCodigoTablaAndDescripcionCorta(int codigoTabla, String descripcionCorta);
    Optional<Parametro> findByCodigoTablaAndDescripcionLarga(int codigoTabla, String descripcionLarga);
}
