package com.de.escuelarural.controller;

import com.de.escuelarural.entity.Estudiante;
import com.de.escuelarural.exception.ResourceNotFoundException;
import com.de.escuelarural.services.EstudianteService;
import com.de.escuelarural.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "estudiantes/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoEstudiante(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("cursos", cursoService.findAll());
        return "estudiantes/form";
    }

    @PostMapping("/guardar")
    public String guardarEstudiante(@Valid @ModelAttribute("estudiante") Estudiante estudiante, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cursos", cursoService.findAll());
            return "estudiantes/form";
        }
        try {
            if (estudiante.getId() != null) {
                Estudiante estudianteExistente = estudianteService.findById(estudiante.getId());
                estudianteExistente.setNombre(estudiante.getNombre());
                estudianteExistente.setCurso(estudiante.getCurso());
                estudianteService.save(estudianteExistente);
            }else{
                estudianteService.save(estudiante);
            }
            return "redirect:/estudiantes";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cursos", cursoService.findAll());
            return "estudiantes/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditarEstudiante(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("estudiante", estudianteService.findById(id));
            model.addAttribute("cursos", cursoService.findAll());
            return "estudiantes/form";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/estudiantes";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id, Model model) {
        try {
            estudianteService.delete(id);
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/estudiantes";
    }
}