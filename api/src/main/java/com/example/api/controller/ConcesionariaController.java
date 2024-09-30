package com.example.api.controller;

import com.example.api.model.Concesionaria;
import com.example.api.service.ConcesionariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/concesionaria")
public class ConcesionariaController {
    private final ConcesionariaService concesionariaService;

    public ConcesionariaController(ConcesionariaService concesionariaService) {
        this.concesionariaService = concesionariaService;
    }

    @PostMapping("/agregar-concesionaria") // o "/add-concesionaria"
    public ResponseEntity<?> addConcesionaria(@RequestBody Concesionaria concesionaria) {
        try {
            Concesionaria nuevaConcesionaria = concesionariaService.save(concesionaria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaConcesionaria);
        } catch (RuntimeException e) {
            // Aquí podrías registrar el error para depuración
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al agregar la concesionaria: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Concesionaria>> getConcesionaria() {
        try {
            List<Concesionaria> concesionarias = concesionariaService.findAllConcesionaria();
            return ResponseEntity.ok(concesionarias); // Si la lista es encontrada, devolver 200 OK con los datos
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // En caso de error, devolver un código 500 con cuerpo null
        }
    }

    @GetMapping("/{id}")
    public Optional<Concesionaria> getConcesionariaById(@PathVariable Long id) {
        return  concesionariaService.findConcesionariaById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concesionaria> updateConcesionaria(@PathVariable Long id, @RequestBody Concesionaria concesionariaActualizada) {
        // Verificar si la concesionaria con el ID proporcionado existe
        Optional<Concesionaria> dataOptional = concesionariaService.findConcesionariaById(id); //el optional valida que el elemento exista o no, ventaja en excepcion
        if (dataOptional.isPresent()){
            Concesionaria data = concesionariaService.editConcesionaria(dataOptional, concesionariaActualizada);
            return ResponseEntity.ok(data);
        } else {
            // Si la persona no fue encontrada, retornar un 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConcesionaria(@PathVariable Long id) {
        try {
            concesionariaService.deleteById(id);
            return ResponseEntity.ok("Persona eliminada con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró concesionaria con este ID: " + id);
        }
    }


}
