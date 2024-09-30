package com.example.api.service;

import com.example.api.model.Concesionaria;
import com.example.api.model.Persona;
import com.example.api.repository.ConcesionariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcesionariaService {
    private final ConcesionariaRepository concesionariaRepository;

    public ConcesionariaService(ConcesionariaRepository concesionariaRepository) {
        this.concesionariaRepository = concesionariaRepository;
    }


    public Concesionaria save(Concesionaria concesionaria) {
        return concesionariaRepository.save(concesionaria);
    }

    public List<Concesionaria> findAllConcesionaria() {
        return concesionariaRepository.findAll();
    }

    public Optional<Concesionaria> findConcesionariaById(Long id) {
        return concesionariaRepository.findById(id);
    }

    public Concesionaria editConcesionaria(Optional<Concesionaria> dataOptional, Concesionaria concesionariaActualizada) {
        // Verificamos que el Optional contenga un valor
        if (dataOptional.isPresent()) {
            Concesionaria concesionaria = dataOptional.get();
            concesionaria.setNombre(concesionariaActualizada.getNombre());
            concesionaria.setDireccion(concesionariaActualizada.getDireccion());
            concesionaria.setTelefono(concesionariaActualizada.getTelefono());
            return concesionariaRepository.save(concesionaria);  // Guardamos la entidad actualizada
        } else {
            throw new RuntimeException("Concesionaria no encontrada");
        }
    }

    public void deleteById(Long id) {
        concesionariaRepository.deleteById(id);
    }
}
