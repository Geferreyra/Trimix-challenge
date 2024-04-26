package com.gefe.rrhh.controller;


import com.gefe.rrhh.entity.Persona;
import com.gefe.rrhh.exception.RecursoNoEncontradoException;
import com.gefe.rrhh.services.IPersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("trimix-api")
@CrossOrigin(value= "http://localhost:3000" )
public class PersonaRestController {
    private static final Logger logger= LoggerFactory.getLogger(PersonaRestController.class);
    @Autowired
    IPersonaService personaService;

    @GetMapping("/timezone")
    public String getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        return "ID de la zona horaria: " + timeZone.getID();
    }

    @GetMapping("/personas")
    public List<Persona>obtenerPersonas(){
        var personas = personaService.listarPersonas();
        personas.forEach(empleado -> logger.info(empleado.toString()));
        return personas;
    }

    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> obtenerEmpleadoById(@PathVariable Long id){
        var persona = personaService.buscarPorId(id);

        if(persona == null){
            logger.error("No se encontro el usuario con el id: "+ id);
            throw new RecursoNoEncontradoException("No se encontro el usuario con el id: "+ id);
        }
        return ResponseEntity.ok(persona);
    }

    @GetMapping("personas/filtrar/nombre/{nombre}")
    public ResponseEntity<List<Persona>> filtrarPorNombre(@PathVariable String nombre) {
        List<Persona>personas = personaService.filtrarPorNombre(nombre);

        if (personas.isEmpty()) {
            logger.error("No se encontro la persona con el nombre: "+ nombre);
            throw new RecursoNoEncontradoException("No se encontraron personas con el nombre: " + nombre);
        }
        return ResponseEntity.ok(personas);
    }
    @GetMapping("personas/filtrar/tipo-de-documento/{tipoDocumento}")
    public ResponseEntity<List<Persona>> buscarPorTipoDocumento(@PathVariable String tipoDocumento) {
        List<Persona> personas = personaService.filtrarPorTipoDocumento(tipoDocumento);

        if (personas.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron personas con el tipo de documento: " + tipoDocumento);
        }

        return ResponseEntity.ok(personas);
    }

    @PostMapping("/personas")
    public Persona agregarPersona( @RequestBody Persona persona){
        logger.info("Persona a agregar: "+ persona);
        return personaService.guardarPersona(persona);
    }
    @PutMapping("/personas/{id}")
    public ResponseEntity<Persona>actualizarPersona(@PathVariable Long id, @RequestBody Persona objtoRecibido){
        var persona = personaService.buscarPorId(id);
        if(persona == null){
            throw new RecursoNoEncontradoException("No se encontro la persona con el id: "+ id);
        }
        if (objtoRecibido.getNombre() != null) {
            persona.setNombre(objtoRecibido.getNombre());
        }
        if (objtoRecibido.getApellido() != null) {
            persona.setApellido(objtoRecibido.getApellido());
        }
        if (objtoRecibido.getFecha_nacimiento() != null) {
            persona.setFecha_nacimiento(objtoRecibido.getFecha_nacimiento());
        }

        if (objtoRecibido.getDni() != null) {
            persona.setDni(objtoRecibido.getDni());
        }

        if (objtoRecibido.getTipoDocumento() != null) {
            persona.setTipoDocumento(objtoRecibido.getTipoDocumento());
        }


        personaService.guardarPersona(persona);
        return ResponseEntity.ok(persona);
    }

    @DeleteMapping("/personas/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarPersonas(@PathVariable Long id){
        var empleado = personaService.buscarPorId(id);
        if(empleado == null){
            logger.error("No se encontro la persona con el id: "+ id);
            throw new RecursoNoEncontradoException("No se encontro la persona con el id: "+ id);
        }

        personaService.eliminarPersona(empleado);
        Map<String,Boolean> respuesta = new HashMap<>();

        respuesta.put("eliminado",Boolean.TRUE);

        return ResponseEntity.ok(respuesta);
    }


}
