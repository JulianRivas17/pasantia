package com.example.api.resource;


import com.example.api.domain.Persona;
import com.example.api.repository.PersonasRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/personas")
public class PersonasCrontroller {
    private List<Persona> personas = new ArrayList<>();
    private Long idPersona = 1L;
    private final PersonasRepository personasRepository;

    public PersonasCrontroller(PersonasRepository personasRepository) {
        this.personasRepository = personasRepository;
    }


    @GetMapping("/")
    public List<Persona> getPersonas() {
        return personasRepository.findAll();
    }

    @PostMapping("/añadir-persona")
    public ResponseEntity<Persona> addPersona(@RequestBody Persona persona) {  //La anotación @PathVariable le dice a Spring que el valor de id debe extraerse de la parte correspondiente de la URL.
        try {
            Persona nuevaPersona = personasRepository.save(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPersona);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonaById(@PathVariable Long id) {  //La anotación @PathVariable le dice a Spring que el valor de id debe extraerse de la parte correspondiente de la URL.
        try {
            personasRepository.deleteById(id);
            return ResponseEntity.ok("Persona eliminada con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró persona con este ID: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        // Verificar si la persona con el ID proporcionado existe
        Persona personaExistente = personasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));

        // Actualizar los datos de la persona existente con los datos nuevos
        personaExistente.setNombre(personaActualizada.getNombre());
        personaExistente.setApellido(personaActualizada.getApellido());
        personaExistente.setEdad(personaActualizada.getEdad());

        // Guardar la persona actualizada
        Persona personaGuardada = personasRepository.save(personaExistente);

        // Retornar la respuesta con la persona actualizada
        return ResponseEntity.ok(personaGuardada);
    }

}
