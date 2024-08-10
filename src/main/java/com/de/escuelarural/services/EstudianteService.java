package com.de.escuelarural.services;

import com.de.escuelarural.entity.Estudiante;
import com.de.escuelarural.exception.ResourceNotFoundException;
import com.de.escuelarural.repository.EstudianteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante findById(Long id) throws ResourceNotFoundException {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
    }

    public void delete(Long id) {
        estudianteRepository.deleteById(id);
    }
}