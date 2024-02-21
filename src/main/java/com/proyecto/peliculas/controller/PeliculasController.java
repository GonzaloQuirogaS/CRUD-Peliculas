package com.proyecto.peliculas.controller;

import com.proyecto.peliculas.entities.Actor;
import com.proyecto.peliculas.entities.Pelicula;
import com.proyecto.peliculas.services.IActorService;
import com.proyecto.peliculas.services.IGeneroService;
import com.proyecto.peliculas.services.IPeliculaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PeliculasController {

    private IPeliculaService peliculaService;

    private IGeneroService generoService;

    private IActorService actorService;

    public PeliculasController(IPeliculaService peliculaService, IGeneroService generoService, IActorService actorService) {
        this.peliculaService = peliculaService;
        this.generoService = generoService;
        this.actorService = actorService;
    }

    @GetMapping("/pelicula")
    public String crear(Model model) {

        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("actores", actorService.findAll());
        model.addAttribute("titulo", "Nueva Pelicula");
        return "pelicula";
    }

    @GetMapping("/pelicula/{id}")
    public String editar(@PathVariable(name = "id") Long id, Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("titulo", "Editar Pelicula");
        return "pelicula";
    }

    @PostMapping("/pelicula")
    public String guardar(@Valid Pelicula pelicula, BindingResult br, @ModelAttribute(name = "ids") String ids, Model model) {

        if(br.hasErrors()){
            model.addAttribute("generos", generoService.findAll());
            model.addAttribute("actores", actorService.findAll());
            return "pelicula";
        }


        List<Long> idsProtagonistas = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
        List<Actor> protagonistas = actorService.findAllById(idsProtagonistas);
        pelicula.setProtagonistas(protagonistas);

        peliculaService.save(pelicula);
        return "redirect:home";
    }

    @GetMapping({"/", "/home", "/index"})
    public String home(Model model) {
        model.addAttribute("msj","Catalogo actualizado");
        model.addAttribute("tipoMsj","success");

        return "home";
    }

}
