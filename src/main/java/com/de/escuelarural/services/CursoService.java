package com.de.escuelarural.services;
import com.de.escuelarural.entity.Curso;
import com.de.escuelarural.exception.ResourceNotFoundException;
import com.de.escuelarural.repository.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso findById(Long id) throws ResourceNotFoundException {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }
}