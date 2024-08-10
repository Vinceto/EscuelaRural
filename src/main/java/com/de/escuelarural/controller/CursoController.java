package com.de.escuelarural.controller;
import com.de.escuelarural.entity.Curso;
import com.de.escuelarural.exception.ResourceNotFoundException;
import com.de.escuelarural.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "cursos/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoCurso(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos/form";
    }

    @PostMapping("/guardar")
    public String guardarCurso(@Valid @ModelAttribute("curso") Curso curso, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cursos/form";
        }
        try {
            if (curso.getId() != null) {
                Curso cursoExistente = cursoService.findById(curso.getId());
                cursoExistente.setNombre(curso.getNombre());
                cursoService.save(cursoExistente);
            } else {
                cursoService.save(curso);
            }
            return "redirect:/cursos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cursos/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditarCurso(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("curso", cursoService.findById(id));
            return "cursos/form";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/cursos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id, Model model) {
        try {
            cursoService.delete(id);
            return "redirect:/cursos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/cursos";
        }
    }
}
