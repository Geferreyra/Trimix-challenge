package com.gefe.rrhh.repository;

import com.gefe.rrhh.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) = LOWER(:nombre)")
    List<Persona> findByNombre(@Param("nombre") String nombre);
    List<Persona> findByTipoDocumento(String tipoDocumento);
}
