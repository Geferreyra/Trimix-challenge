package com.gefe.rrhh.services;

import com.gefe.rrhh.entity.Persona;

import java.util.List;

public interface IPersonaService {
    List<Persona> listarPersonas();
    Persona buscarPorId(Long id);
    List<Persona> filtrarPorNombre(String nombre);
    List<Persona> filtrarPorTipoDocumento(String tipoDocumento);
    Persona guardarPersona(Persona persona);
    Persona eliminarPersona(Persona persona);
}
