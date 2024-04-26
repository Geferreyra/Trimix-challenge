package com.gefe.rrhh.services;

import com.gefe.rrhh.entity.Persona;
import com.gefe.rrhh.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServicio implements IPersonaService{

    @Autowired
    PersonaRepository personaRepository;

    @Override
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona buscarPorId(Long id) {
        Persona persona = personaRepository.findById(id).orElse(null);
        return persona;
    }

    @Override
    public List <Persona> filtrarPorNombre(String nombre) {
        var personas = personaRepository.findByNombre(nombre);
        return personas;
    }

    @Override
    public List<Persona> filtrarPorTipoDocumento(String tipoDocumento) {
        var personas = personaRepository.findByTipoDocumento(tipoDocumento);
        return personas;
    }

    @Override
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public Persona eliminarPersona(Persona persona) {
        personaRepository.delete(persona);
        return null;
    }

}
