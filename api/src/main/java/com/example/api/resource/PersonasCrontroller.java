package com.example.api.resource;


import com.example.api.domain.Persona;
import org.springframework.web.bind.annotation.*;

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
}
