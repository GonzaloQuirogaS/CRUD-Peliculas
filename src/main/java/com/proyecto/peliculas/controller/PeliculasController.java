package com.proyecto.peliculas.controller;

import com.proyecto.peliculas.entities.Pelicula;
import com.proyecto.peliculas.services.IPeliculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PeliculasController {

    private IPeliculaService peliculaService;

    public PeliculasController(IPeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping("/pelicula")
    public String crear(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("titulo", "Nueva Pelicula");
        return "pelicula";
    }

    @GetMapping("/pelicula/{id}")
    public String editar(@PathVariable(name = "id") Long id, Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("titulo", "Editar Pelicula");
        return "pelicula";
    }

}
