package com.gefe.rrhh.repository;

import com.gefe.rrhh.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {
    List<Persona> findByNombre(String nombre);
    List<Persona> findByTipoDocumento(String tipoDocumento);
}
