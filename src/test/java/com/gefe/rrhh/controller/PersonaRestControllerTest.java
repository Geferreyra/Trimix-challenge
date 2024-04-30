package com.gefe.rrhh.controller;

import com.gefe.rrhh.entity.Persona;
import com.gefe.rrhh.exception.RecursoNoEncontradoException;
import com.gefe.rrhh.services.IPersonaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonaRestControllerTest {
    @Mock
    private IPersonaService personaService;

    @InjectMocks
    private PersonaRestController personaController;

    @Test
    public void testObtenerPersonaExistente() throws ParseException {
        long id = 1L;
        Persona personaMock = new Persona();
        personaMock.setId(id);
        personaMock.setNombre("Gustavo");
        personaMock.setApellido("Ferreyra");
        personaMock.setTipoDocumento("Pasaporte");
        personaMock.setNroDocumento("40879654");
        personaMock.setFecha_nacimiento(new SimpleDateFormat("dd-MM-yyyy").parse("12-12-2012"));

        when(personaService.buscarPorId(id)).thenReturn(personaMock);

        ResponseEntity<Persona> response = personaController.obtenerEmpleadoById(id);

        System.out.println("Se encontro la persona: "+ personaMock.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personaMock, response.getBody());
    }

    @Test
    public void testObtenerPersonaNoExistente() {

        long id = 1L;
        when(personaService.buscarPorId(id)).thenReturn(null);

        try {
            personaController.obtenerEmpleadoById(id);
            fail("Se esperaba que se lanzara una RecursoNoEncontradoException");
        } catch (RecursoNoEncontradoException e) {

            assertEquals("No se encontro el usuario con el id: " + id, e.getMessage());
        }
    }

    @Test
    public void testFiltrarPorNombre_Existente() {
        // Arrange
        String nombre = "Johnybgod";
        Persona personaMock = new Persona();
        personaMock.setNombre(nombre);
        when(personaService.filtrarPorNombre(nombre)).thenReturn(Collections.singletonList(personaMock));

        ResponseEntity<List<Persona>> response = personaController.filtrarPorNombre(nombre);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(personaMock), response.getBody());
        System.out.println("Se encontro la persona con el nombre: "+  nombre);
    }

    @Test
    public void testFiltrarPorNombre_NoExistente() {
        String nombre = "Juan";
        when(personaService.filtrarPorNombre(nombre)).thenReturn(Collections.emptyList());

        try {
            personaController.filtrarPorNombre(nombre);
            fail("Se esperaba que se lanzara una RecursoNoEncontradoException");
        } catch (RecursoNoEncontradoException e) {

            assertEquals("No se encontraron personas con el nombre: " + nombre, e.getMessage());
        }
    }

    @Test
    public void testBuscarPorTipoDocumento_Existente() throws ParseException {

        String tipoDocumento = "DNI";
        Persona personaMock = new Persona();
        personaMock.setId(1L);
        personaMock.setNombre("Gustavo");
        personaMock.setApellido("Ferreyra");
        personaMock.setTipoDocumento(tipoDocumento);
        personaMock.setNroDocumento("40879654");
        personaMock.setFecha_nacimiento(new SimpleDateFormat("dd-MM-yyyy").parse("12-12-2012"));


        Persona personaMock2 = new Persona();
        personaMock2.setId(2L);
        personaMock2.setNombre("Ezequiel");
        personaMock2.setApellido("Ferreyra");
        personaMock2.setTipoDocumento("Pasaporte");
        personaMock2.setNroDocumento("40879654");
        personaMock2.setFecha_nacimiento(new SimpleDateFormat("dd-MM-yyyy").parse("12-12-2012"));

        List<Persona>personas = new ArrayList<>();
        personas.add(personaMock);
        personas.add(personaMock2);


        int contadorDNI = 0;
        int contadorPasaporte = 0;

        for(Persona persona: personas){
        if(persona.getTipoDocumento() == "DNI"){
            System.out.println("Tipo de documento de la persona: " + persona.getTipoDocumento());
            contadorDNI++;
        }else if (persona.getTipoDocumento()== "Pasaporte"){
            System.out.println("Tipo de documento de la persona: " + persona.getTipoDocumento());
            contadorPasaporte++;
        }

        }

        System.out.println("Se encontraron " + contadorDNI + " personas con tipo de documento DNI");
        System.out.println("Se encontraron " + contadorPasaporte + " personas con tipo de documento Pasaporte");


        when(personaService.filtrarPorTipoDocumento(tipoDocumento)).thenReturn(personas);

        ResponseEntity<List<Persona>> response = personaController.buscarPorTipoDocumento(tipoDocumento);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personas, response.getBody());
    }

    @Test
    public void testActualizarPersona_Existente() {
        // Arrange
        Long id = 1L;
        Persona personaExistente = new Persona();
        personaExistente.setId(id);
        personaExistente.setNombre("Joan");
        personaExistente.setApellido("Pirez");
        personaExistente.setFecha_nacimiento(new Date(2012, 11, 12)); // Corregir la creación de la fecha de nacimiento
        personaExistente.setNroDocumento("40879654");
        personaExistente.setTipoDocumento("DNI");

        Persona personaActualizada = new Persona();
        personaActualizada.setId(id);
        personaActualizada.setNombre("Juan Pablo");
        personaActualizada.setApellido("Pirez");
        personaActualizada.setFecha_nacimiento(new Date(2012, 11, 12)); // Corregir la creación de la fecha de nacimiento
        personaActualizada.setNroDocumento("40879654");
        personaActualizada.setTipoDocumento("DNI");


        when(personaService.buscarPorId(id)).thenReturn(personaExistente);
        when(personaService.guardarPersona(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Devolver el argumento recibido sin cambios

        ResponseEntity<Persona> response = personaController.actualizarPersona(id, personaActualizada);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personaActualizada, response.getBody());

        // Verificar que solo se ha actualizado el nombre y los demás atributos se mantienen igual
        assertEquals("Juan Pablo", personaExistente.getNombre());
        assertEquals("Pirez", personaExistente.getApellido()); // El apellido debe mantenerse igual
        assertEquals(new Date(2012, 11, 12), personaExistente.getFecha_nacimiento()); // Corregir la fecha de nacimiento
        assertEquals("40879654", personaExistente.getNroDocumento());
        assertEquals("DNI", personaExistente.getTipoDocumento());
    }

    @Test
    public void testAgregarPersona() {
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Pereira");
        persona.setTipoDocumento("DNI");
        persona.setNroDocumento("40879654");
        persona.setFecha_nacimiento(new Date(2012, 11, 12));

        when(personaService.guardarPersona(persona)).thenReturn(persona);

        Persona personaAgregada = personaController.agregarPersona(persona);

        assertEquals(persona, personaAgregada);
        System.out.println("Se agrego correctamente la persona: "+ persona);
    }

    @Test
    public void testEliminarPersona_Existente() {

        Long id = 1L;


        when(personaService.buscarPorId(id)).thenReturn(new Persona());


        ResponseEntity<Map<String, Boolean>> response = personaController.eliminarPersonas(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Boolean> respuesta = response.getBody();
        assertEquals(1, respuesta.size());
        assertTrue(respuesta.containsKey("eliminado"));
        assertTrue(respuesta.get("eliminado"));
        verify(personaService, times(1)).eliminarPersona(any());
    }

    @Test
    public void testEliminarPersona_NoExistente() {

        Long id = 1L;
        when(personaService.buscarPorId(id)).thenReturn(null);

        assertThrows(RecursoNoEncontradoException.class, () -> {
            personaController.eliminarPersonas(id);
        });
        verify(personaService, never()).eliminarPersona(any());
    }


}
