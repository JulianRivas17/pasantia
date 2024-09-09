package com.example.api.resource;


import com.example.api.domain.Persona;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/personas")
public class PersonasCrontroller {
    private List<Persona> personas = new ArrayList<>();
    private Long idPersona = 1L;


    @GetMapping("/")
    public List<Persona> getPersonas() {
        return personas;
    }
    @PostMapping("/añadir-persona")
    public Persona agregarPersona(@RequestBody Persona persona) {
        persona.setId(idPersona++);
        personas.add(persona);
        return persona;
    }

    @GetMapping("/{id}") //se establecee que id será tratado como una variable segun se ingrese en la url
    public Persona getPersonaById(@PathVariable Long id) {  //La anotación @PathVariable le dice a Spring que el valor de id debe extraerse de la parte correspondiente de la URL.
        for (Persona persona : personas) { //Este es un bucle for-each que itera sobre la lista personas. En cada iteración, la variable persona representa un objeto Persona de la lista.
            if (persona.getId().equals(id)) { //if id del objeto persona de Persona coincide con el id ingresado en la url
                return persona; //retornará todos los datos del objeto persona de Persona
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"); //excepcion por si no se encuentra el id
    }
}
